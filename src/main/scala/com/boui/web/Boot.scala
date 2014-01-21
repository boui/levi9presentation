package com.boui.web

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import com.boui.core.{Download, CoordinatorActor, DownloadUrl, DownloadActor}
import java.util.UUID

object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val sprayActor = system.actorOf(Props[MyServiceActor], "spray-service")


  val coordinator  = system.actorOf(Props[CoordinatorActor], "coordinator-one")
  coordinator ! Download("http://stackoverflow.com/questions/9710295/eofexception-in-connecting-to-hdfs-in-hadoop", -1)

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(sprayActor, interface = "localhost", port = 8080)
}