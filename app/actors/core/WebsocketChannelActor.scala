package actors.core

import akka.actor.{Actor, Props}
import play.api.libs.iteratee.Concurrent
import play.api.libs.json.{Json, JsValue}

case class Info(fileInfo:String, _type:String = "info")
case class FileLoadStarted(id:String, url:String, _type:String = "start_file")
case class FileLoadReceivedPart(id:String, size:Int, _type:String = "upd_file")
case class FileLoadEnded(id:String, _type:String = "end_file")

object WebsocketChannelActor {
  def props(channel: Concurrent.Channel[JsValue]):Props = Props(new WebsocketChannelActor(channel))
}

class WebsocketChannelActor(channel: Concurrent.Channel[JsValue]) extends Actor {
  implicit val InfoWrite = Json.writes[Info]
  implicit val FileLoadStartedWrite = Json.writes[FileLoadStarted]
  implicit val FileLoadReceivedPartWrite = Json.writes[FileLoadReceivedPart]
  implicit val FileLoadEndedWrite = Json.writes[FileLoadEnded]

  def receive = {
    case info @ Info(data, _type) => channel.push(Json.toJson(info))
    case m1 @ FileLoadStarted(id, url, _t1) => channel.push(Json.toJson(m1))
    case m2 @ FileLoadReceivedPart(id, size, _t2) => channel.push(Json.toJson(m2))
    case m3 @ FileLoadEnded(id, _t3) => channel.push(Json.toJson(m3))
  }
}