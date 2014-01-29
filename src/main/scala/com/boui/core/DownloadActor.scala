package com.boui.core

import akka.actor.{Props, ActorRef, Actor}
import com.ning.http.client._
import com.ning.http.client.AsyncHandler.STATE
import java.io.BufferedOutputStream
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataOutputStream, Path, FileSystem}
import java.net.URI
import java.nio.charset.Charset
import org.jsoup.Jsoup
import java.security.MessageDigest
import java.math.BigInteger

case class DownloadUrl(id: String, url: String, level: Int, coordinator: ActorRef)

case class DownloadComplete(html: String)

case class DownloadException(e: Throwable)

object lol {
  val asyncHttpClient: AsyncHttpClient = new AsyncHttpClient
  val hdfsURI = "hdfs://127.0.0.1:9000"
  lazy val hdfs = {
    val configuration = new Configuration()
    FileSystem.get(new URI(hdfsURI), configuration)
  }

  def md5(s: String) = {
    //http://viralpatel.net/blogs/java-md5-hashing-salting-password/
    val digest = MessageDigest.getInstance("MD5")
    digest.update(s.getBytes(), 0, s.length())
    new BigInteger(1, digest.digest()).toString(16)
  }
}

class DownloadActor extends Actor with Serializable {

  import lol._

  def receive = initial

  def initial: Receive = {
    case DownloadUrl(id, url, level, coordinator) => {
      println("started" + counter.i.getAndIncrement)
      val me = self
      asyncHttpClient.prepareGet(url).execute(
        new DownloadCompletionHandler(me, id)
      )
      context.become(downloading(url, id, level, coordinator))
    }
  }

  def downloading(url: String, id: String, level: Int, coordinator: ActorRef): Receive = {
    case DownloadComplete(html) => {
      val extractorActor = context.actorOf(Props[ExtractorActor])
      if (level < 1) extractorActor ! ExtractUrls(html, level + 1, coordinator)
      println("Download complete:" + url + "of level:" + level + "of count:" + counter.i.get)
      coordinator ! Done
      context.unbecome()
    }

    case DownloadException(e) => {
      e.printStackTrace()
      context.unbecome()
    }
  }
}

class DownloadCompletionHandler(tracker: ActorRef, id: String) extends AsyncCompletionHandler[Unit] {

  import lol._

  def parse(html: String): String = {
    import scala.collection.JavaConversions._
    val newHtml = Jsoup.parse(html).select("div#mw-content-text a[href]").map {
      a => {
        a.attr("href", md5(a.attr("href")))
      }
    }
    newHtml.toString()
  }

  lazy val (bos, file:FSDataOutputStream) = {
    val path = new Path(hdfsURI + "/files/" + id)
    if (hdfs.exists(path)) {
      hdfs.delete(path, true)
    }
    val os = hdfs.create(path)
    (new BufferedOutputStream(os), os)
  }

  var isHtmlPage = false
  val inMemoryHtml = new StringBuffer()

  def onCompleted(response: Response) = {
    if (isHtmlPage) {
      file.writeChars(parse(inMemoryHtml.toString))
    }
    file.close()
    bos.close()
    println("closed all resources" + counter.i.getAndDecrement)
    tracker ! DownloadComplete(inMemoryHtml.toString)
  }

  override def onThrowable(t: Throwable) = {
    file.close()
    bos.close()
    println("throwable closed" + id)
    tracker ! DownloadException(t)
  }

  override def onHeadersReceived(headers: HttpResponseHeaders): STATE = {
    import scala.collection.JavaConversions._
    val headersMap = headers.getHeaders
    val contentTypeOpt = headersMap.entrySet().find {
      x => x.getKey == "Content-Type"
    }
    for (contentType <- contentTypeOpt) {
      contentType.getValue.find(x => {
        x.contains("text/html")
      }) match {
        case Some(_) => {
          isHtmlPage = true
        }
        case None => {
          isHtmlPage = false
        }
      }
    }
    STATE.CONTINUE
  }

  override def onBodyPartReceived(content: HttpResponseBodyPart): STATE = {
    if (isHtmlPage) {
      inMemoryHtml append Charset.forName("UTF8").decode(content.getBodyByteBuffer)
    } else {
      content.writeTo(bos)
    }
    STATE.CONTINUE
  }
}
