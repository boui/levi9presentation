package com.boui.core

import scala.concurrent.Future
import scala.util.parsing.json.JSON

/**
 * User: boui
 * Date: 1/29/14
 */

case class Basket()
case class Environment(mood:String, place:String)
case class User(name:String, age:Int){
  def tellAboutEnvironment(implicit env:Environment)={
    println(env)
  }
}



object Mainresentation {
  implicit class JsonConverter(x: User) {
    def toJson(): String = {
      "{name:"+x.name+";age:"+x.age+"}"
    }
  }

  def main(args: Array[String]) {
    implicit val env = Environment("sad", "grey woods")
    User("Nancy",10).tellAboutEnvironment
  }


  class Lol (reason:String) {
    def getReason = reason
  }
  object Lol{
    def apply(s:String):Lol ={
      new Lol(s)
    }
  }

  val myLol = Lol("irony")
  println(myLol.getReason)




//
//  def currentUser: Future[User] = ???
//
//  def getBasket(user: User): Future[Basket] = ???
//
//  def addSteamBox(basket: Basket): Future[Int] = ???
//
//  def sendResponse(msg:String) {}
//
//  val userFuture = currentUser
//
//  userFuture.onSuccess{
//    case user => {
//      val basketFuture = getBasket(user)
//
//      basketFuture.onSuccess{
//        case basket => {
//          val itemsCountFuture = addSteamBox(basket)
//
//          itemsCountFuture.onSuccess{
//            case itemsCount => {
//              sendResponse("Items: "+itemsCount)
//            }
//          }
//
//          itemsCountFuture.onFailure{
//            case e => sendResponse(e.toString)
//          }
//        }
//
//      }
//      basketFuture.onFailure{
//        case e => sendResponse(e.toString)
//      }
//    }
//  }
//  userFuture.onFailure{
//    case e => sendResponse(e.toString)
//  }
//
//  // -------
//
//  val result = for {
//    user <- currentUser
//    basket <- getBasket(user)
//    itemsCount <- addSteamBox(basket)
//  } yield {
//    sendResponse("Items: "+itemsCount)
//  }
//
//  for (e <- result.failed){
//    sendResponse(e.toString)
//  }
}
