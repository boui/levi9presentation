package com.boui.core

import akka.actor.{ActorRef, Actor}
import com.ning.http.client._
import com.ning.http.client.AsyncHandler.STATE
import java.io.BufferedOutputStream
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{Path, FileSystem}
import java.net.URI
import java.util.UUID
import java.nio.charset.Charset

case class DownloadUrl(id: UUID, url: String, level:Int)
case class DownloadComplete(html: String)
case class DownloadException(e: Throwable)

class DownloadActor extends Actor with Serializable {

  val asyncHttpClient: AsyncHttpClient = new AsyncHttpClient

  def receive = initial

  def initial: Receive = {
    case DownloadUrl(id, url, level) => {
      val me = self
      asyncHttpClient.prepareGet(url).execute(
        new DownloadCompletionHandler(me, id)
      )
      context.become(downloading(url, id, level))
    }

    case _ => println("WTF1")
  }

  def downloading(url: String, id: UUID, level:Int): Receive = {
    case DownloadComplete(html) => {
      println("download completed")
      sender ! CheckTimes(level, html)
      context.unbecome()
    }

    case DownloadException(e) => {
      context.unbecome()
    }

    case _ => println("WTF2")
  }
}

class DownloadCompletionHandler(tracker: ActorRef, id: UUID) extends AsyncCompletionHandler[Unit] {

  val hdfsURI = "hdfs://127.0.0.1:9000"
  lazy val hdfs = {
    val configuration = new Configuration()
    FileSystem.get(new URI(hdfsURI), configuration)
  }

  lazy val bos = {
    val file = new Path(hdfsURI + "/files/" + id)
    if (hdfs.exists(file)) {
      hdfs.delete(file, true)
    }
    val os = hdfs.create(file)
    new BufferedOutputStream(os)
  }

  var isHtmlPage = false
  val inMemoryHtml = new StringBuffer()

  def onCompleted(response: Response) = {
    bos.close()
    hdfs.close()
    tracker ! DownloadComplete(inMemoryHtml.toString)
  }

  override def onThrowable(t: Throwable) = {
    bos.close()
    hdfs.close()
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
          isHtmlPage = true; println("YES")
        }
        case None => {
          isHtmlPage = false; println("NO")
        }
      }
    }
    STATE.CONTINUE
  }

  override def onBodyPartReceived(content: HttpResponseBodyPart): STATE = {
    if (isHtmlPage) {
      inMemoryHtml append Charset.forName("UTF8").decode(content.getBodyByteBuffer)
    }
    content.writeTo(bos)
    STATE.CONTINUE
  }
}