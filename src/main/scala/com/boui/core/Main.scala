package com.boui.core

import akka.actor.{ActorSystem, Props}

/**
 * User: boui
 * Date: 1/13/14
 */
object Main {
  def main(args: Array[String]) {
    val downloadActor  = ActorSystem().actorOf(Props[DownloadActor], "download-actor")
    downloadActor ! DownloadUrl("http://doc.akka.io/docs/akka/snapshot/AkkaScala.pdf")
  }
}
