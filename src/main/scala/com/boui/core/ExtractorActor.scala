package com.boui.core

import akka.actor.Actor
import org.jsoup.nodes.Document
import org.jsoup.Jsoup

/**
 * User: boui
 * Date: 1/21/14
 */
case class ExtractUrls(html:String, level:Int)

class ExtractorActor extends Actor with Serializable{
      def receive = {
         case ExtractUrls(html, level) => {
           val doc:Document = Jsoup.parse(html)
           import scala.collection.JavaConversions._
           doc.select("a[href]").map{ url =>
              println("found new url "+ url)
              sender ! Download(url.attr("href"), level)
           }
         }
      }
}