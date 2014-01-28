package com.boui.core

import akka.actor.{Props, Actor}
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import scala.collection.mutable
import java.util.concurrent.atomic.AtomicLong

/**
 * User: boui
 * Date: 1/21/14
 */

case class Enqueue(url: String, level: Int)
case class AllDone()
case class Done()

object counter {
  val i = new AtomicLong(0l)
}

class CoordinatorActor extends Actor with Serializable {
  val state: ConcurrentHashMap[UUID, String] = new ConcurrentHashMap[UUID, String]()
  val loadQueue = new mutable.Queue[DownloadUrl]()
  val sentMessagesCount = new AtomicLong(0l)
  val MAX_CONNECTIONS_ALLOWED = 10

  def receive = serve

  def serve: Receive = {

    case Enqueue(url, level) => {
      val coordinator = self
      if (sentMessagesCount.get > MAX_CONNECTIONS_ALLOWED) {
        loadQueue.enqueue(DownloadUrl(UUID.randomUUID(), url, level, coordinator))
        context.become(accumulate)
      } else {
        sentMessagesCount.incrementAndGet()
        println("serve>> New" + sentMessagesCount.get)

        val msg = if (loadQueue.size > 0) loadQueue.dequeue() else DownloadUrl(UUID.randomUUID(), url, level, coordinator)
        val downloadActor = context.actorOf(Props[DownloadActor], "download-actor-" + msg.id)
        downloadActor ! msg
      }
    }

    case Done => {
      sentMessagesCount.decrementAndGet()
      println("serve>> Done")
      if (loadQueue.size > 0){
        sentMessagesCount.incrementAndGet()
        val msg = loadQueue.dequeue()
        val downloadActor = context.actorOf(Props[DownloadActor], "download-actor-" + msg.id)
        downloadActor ! msg
      }  else self ! AllDone
    }

    case AllDone =>{
      println("All done, cool")
    }

  }

  def accumulate: Receive = {
    case Enqueue(url, level) => {
      val coordinator = self
      println("accum>> Enqueue:" + url)
      loadQueue.enqueue(DownloadUrl(UUID.randomUUID(), url, level, coordinator))
    }

    case Done => {
      if (sentMessagesCount.decrementAndGet() < MAX_CONNECTIONS_ALLOWED) {
        context.unbecome()
        if (loadQueue.size > 0){
          println("accum>> Done")
          sentMessagesCount.incrementAndGet()
          val msg = loadQueue.dequeue()
          val downloadActor = context.actorOf(Props[DownloadActor], "download-actor-" + msg.id)
          downloadActor ! msg
        }  else self ! AllDone
      }
    }
  }
}
