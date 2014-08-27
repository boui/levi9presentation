package controllers

import play.mvc.Controller
import play.api.mvc._
import play.api.mvc.Results._
import play.api.libs.iteratee.{Iteratee, Concurrent}
import akka.actor.{Props, ActorSystem}
import com.boui.core.{CoordinatorActor, Enqueue}
import play.api.mvc.WebSocket
import actors.core.WebsocketChannelActor
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.JsValue

/**
 * User: boui
 * Date: 8/27/14
 */
object AppController extends Controller{
  def index = Action { request =>
     Ok(views.html.index())
  }

  def start = WebSocket.using[JsValue] {
    request =>
      val (outEnumerator, channel) = Concurrent.broadcast[JsValue]
      val inIteratee: Iteratee[JsValue, Unit] = Iteratee.ignore[JsValue]

      boot(channel)

      (inIteratee, outEnumerator)
  }

  def boot(out:Concurrent.Channel[JsValue]) = {
    implicit val system = ActorSystem("system-1")
    val websocketChannel = system.actorOf(WebsocketChannelActor.props(out))
    val coordinator = system.actorOf(Props(new CoordinatorActor(websocketChannel)), "coordinator-one")
    coordinator ! Enqueue("http://en.wikipedia.org/wiki/Titan_triggerfish", 0)
  }
}
