package com.boui.core

import akka.actor.{Props, ActorSystem, Actor}
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * User: boui
 * Date: 1/21/14
 */
case class Download(url: String, level: Int)

case class CheckTimes(level: Int, html: String)

class CoordinatorActor extends Actor with Serializable {
  val system = ActorSystem("download-system")
  val state: ConcurrentHashMap[UUID, String] = new ConcurrentHashMap[UUID, String]()


  def receive = {
    case Download(url, level) => {
      val id = UUID.randomUUID()
      val downloadActor = system.actorOf(Props[DownloadActor], "download-actor")
      state.put(id, url)
      println(url + " is loading")
      downloadActor ! DownloadUrl(id, url, level)
    }

    case CheckTimes(parentLevel, html) => {
      println("current level " + parentLevel)
      val extractorActor = system.actorOf(Props[ExtractorActor], "extractor-actor")
      if (parentLevel < 2) extractorActor ! ExtractUrls(html, parentLevel + 1)
    }
  }
}
