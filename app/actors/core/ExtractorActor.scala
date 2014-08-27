package com.boui.core

import akka.actor.{ActorRef, Actor}
import org.jsoup.nodes.Document
import org.jsoup.Jsoup

/**
 * User: boui
 * Date: 1/21/14
 */
case class ExtractUrls(html: String, level: Int, coordinator: ActorRef)

class ExtractorActor extends Actor with Serializable {
  def receive = {
    case ExtractUrls(html, level, coordinator) => {
      val doc: Document = Jsoup.parse(html)
      if (!html.isEmpty) {
        import scala.collection.JavaConversions._
        val size = doc.select("div#mw-content-text a[href]").map {
          a => {
            val url = a.attr("href")
            if (!url.contains("#")
              && !a.attr("class").contains("new")
              && !url.contains("web.archive.org")
              && !url.contains("Special:")
            ) {
              if (url.contains("http") || url.contains("https")) {
                coordinator ! Enqueue(url, level)
              } else {
                if (url.startsWith("/wiki/")) {
                  coordinator ! Enqueue("http://en.wikipedia.org" + url, level)
                }
              }
            }
          }
        }.size
        println("size again:" + size)
      }
    }
  }
}