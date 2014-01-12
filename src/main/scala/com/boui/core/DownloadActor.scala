package com.boui.core

import akka.actor.{ActorRef, Actor}
import com.ning.http.client._
import com.ning.http.client.AsyncHandler.STATE
import java.io.{FileOutputStream, BufferedOutputStream}

case class DownloadUrl(url:String)
case class DownloadComplete()
case class DownloadException(e:Throwable)

class DownloadActor extends Actor with Serializable {

  val asyncHttpClient: AsyncHttpClient = new AsyncHttpClient

  def receive = initial

  def initial : Receive = {
    case DownloadUrl(url) => {
      val me = self
      asyncHttpClient.prepareGet(url).execute(
        new DownloadCompletionHandler(url, me)
      )
      context.become(downloading(url))
    }
  }

  def downloading (url:String) : Receive  = {
    case DownloadComplete() => {
      context.unbecome()
    }

    case DownloadException(e) => {
      context.unbecome()
    }
  }
}

class DownloadCompletionHandler(url:String, tracker: ActorRef) extends AsyncCompletionHandler[Unit]{
  val filename = url.replaceAll("http:","").replaceAll("/","_")
  lazy val bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filename))
  
  def onCompleted(response: Response) = {
    bufferedOutputStream.close()
    println("done")
    tracker ! DownloadComplete()
  }

  override def onThrowable(t:Throwable) = {
    println("error:"+t)
    tracker ! DownloadException(t)
  }

  override def onBodyPartReceived(content: HttpResponseBodyPart): STATE = {
    println("in progress")
    println(content.getBodyByteBuffer.array().length)
    content.writeTo(bufferedOutputStream)
    STATE.CONTINUE
  }
}