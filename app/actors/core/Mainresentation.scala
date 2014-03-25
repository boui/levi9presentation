package com.boui.core

import java.util.UUID
import scala.Option

/**
 * User: boui
 * Date: 1/29/14
 */

//case class Basket()
//case class Environment(mood:String, place:String)
//case class User(name: String, age: Int)

//  def tellAboutEnvironment(implicit env:Environment)={
//    println(env)
//  }
//}


//
//case class Person(name: String, surname: String = "Lee")

//
//case class P(name:String, surname:String="Lee"){
//  def unapply(p:Person):Option[Pair[String, String]] = Some(p.name, p.surname)
//}

//trait Bird {
//  def makeSound: String = {
//    "default"
//  }
//}

//
//trait SwimmingBird {
//  def swim: String
//}
//
//case class Penguin() extends Bird with SwimmingBird {
//  def swim = {
//    "tap-tap"
//  }
//}
//
//
//trait FlyingBird {
//  def fly: String
//}

//trait GoodFlyer extends FlyingBird {
//  override def fly = {
//    "I fly good"
//  }
//}
//
//trait BadFlyer extends FlyingBird {
//  override def fly = {
//    "I fly bad"
//  }
//}
//
//case class NotInMoodFlyer(mood: Boolean) extends Bird with GoodFlyer with BadFlyer {
//  override def fly = {
//    if (mood) {
//      super[GoodFlyer].fly
//    } else {
//      super[BadFlyer].fly
//    }
//  }
//}


//won't compile
//case class Jaguar() extends FlyingBird

object Mainresentation {

  //    println(NotInMoodFlyer(true).fly)
  //    println(NotInMoodFlyer(false).fly)
  //  println(Penguin().makeSound)
  //  println(Penguin().swim)


  //  val  p= Person("Bruce")
  //  println(p.name)
  //  println(p.surname)
  //  println(p.toString)
  //  println(p.hashCode())
  //  val p2 = p.copy()
  //  println(p == p2)
  //  println(p.eq(p2))
  //  println(p.productArity)
  //
  //  print(Person("Ui"))
  //  print(Person("Jonh","Doe"))
  //
  //  P("Me") match {
  //    case P("Me","Lee") => println("yey")
  //  }

  //  implicit class JsonConverter(x: User) {
  //    def toJson(): String = {
  //      "{name:"+x.name+";age:"+x.age+"}"
  //    }
  //  }
  //
  //
  //object validation{
  //  case class Success[+A](value:A) extends Validation[A]
  //  case class Failure(error:String) extends Validation[Nothing]
  //  sealed trait Validation[+A] {
  //    //also known in haskell as >>= or bind in theory
  //    def flatMap[B](f: (A) => Validation[B]): Validation[B] = this match {
  //      case Success(a:A) => f(a)
  //      case fail@Failure(error:String) => fail
  //    }
  //    def map[B](f: A=>B): Validation[B] = this match {
  //      case Success(a:A) => Success(f(a))
  //      case fail@Failure(error:String) => fail
  //    }
  //    //works only if you use no yield in for
  //    def foreach(f: A => Unit): Unit = this match {
  //      case Success(a:A) => f(a)
  //      case _ =>
  //    }
  //  }
  //
  //  implicit class ValidationPimp[A](obj: Option[A]){
  //    def ~(errorMessage:String):Validation[A] = obj match {
  //      case Some(value) => Success(value)
  //      case None => Failure(errorMessage)
  //    }
  //  }
  //}
  //
//  def main(args: Array[String]) {

//
//    object DB{
//      val list = Map(1->"Potters", 2->"Everdine")
//      def getIds:List[Int] = list.keys.toList
//      def getValue(id:Int) = list.get(id)
//
//    }
//    def sendResponse(x:String) = println(x)
//
//    import scala.concurrent._
//
//    val a:Future[List[Int]] = future {
//      DB.getIds
//    }

//    val a = future (DB.getIds)
//    val b = future (DB.getValue)
//    def findFriendsOfFriends(id:Int) = future {
//      DB.get
//    }
//
//    // a.value
//    val result = a.map {
//      friends => friends.map{
//            case (id, _) => {
//              findFriendsOfFriends(id)
//            }
//      }}
//
//

    //    def genUser():User={
    //      if(Random.nextBoolean()) User("Bruce", 35) else null
    //    }
    //
    //    val userOption = Option(genUser())
    //    userOption match {
    //        case Some(u) => print(u)
    //        case None => println("No one found")
    //    }
    //
    //    userOption.map(user => {
    //      println(user)
    //    })
    //
    //    val opt = for(user <- userOption) yield user
    //
    //    opt getOrElse {
    //      println("No one found")
    //      User("",25) // default user
    //    }


//    case class Account(active: Boolean, balance: Option[BigDecimal])
//    case class Accounts(accounts: List[Account]) {
//      def getActive: Option[Account] = {
//        accounts.find(_.active)
//      }
//    }
//    case class Customer(id: UUID, accounts: Option[Accounts])
//    val id = UUID.randomUUID()
//    val customers = Option(List(
//
//      Customer(id,
//        Option(Accounts(
//          List(Account(true, Option(BigDecimal("20"))),
//            Account(false, Option(BigDecimal("25")))))))))
//
//
//    def getActiveAccountBalance(idOption: Option[UUID]):BigDecimal = {
//
//      val c = idOption
//        .map{id => customers
//        .map{db => db.find(_.id == id)
//        .map(c => c.accounts.map(a => a.getActive.map( active=> active.balance)))}}
//        //))))
//      val b = idOption.flatMap(id => customers.flatMap(db => db.find(_.id == id).flatMap(c => c.accounts).flatMap(a => a.getActive.flatMap( active=> active.balance)))))
//      b getOrElse BigDecimal(0)

//      val bOpt = for {
//        id <-idOption
//        fromDb <- customers
//        customer <- fromDb.find(_.id == id)
//        accounts <- customer.accounts
//        active <- accounts.getActive
//        balance <- active.balance
//      } yield balance
//
//      bOpt getOrElse BigDecimal(0)
//    }

//    println(getActiveAccountBalance(Option(id)))

    //    import validation._
    //    case class Cat(adressOnLeash:Option[String])
    //    def foundCat(cat:Cat) {
    //      val result = for{
    //       adress <- cat.adressOnLeash ~ "take him home"
    //      } yield adress
    //      println(result)
    //    }
    //
    //    foundCat(Cat(Some("Home, app.1")))
    //    foundCat(Cat(None))
    //    val p = Person("Bruce")
    //    println(p.copy(name="Brandon")) //Person(Brandon,Lee)


    //  def applyFunction(predicate:Int=>Boolean) = {
    //    def sum(v1:Int, v2:Int) = {
    //      (if(predicate(v2)) v2 else 0 + v2).toString
    //    }
    //    sum 25 _
    //  }
    //  def predicate(value:Int):Boolean = value > 10
    //  println(applyFunction(predicate)(25, 40)) // 65
    //  println(applyFunction(predicate)(5, 40))  // 40

    //  val divide: PartialFunction[(Int,Int),Double] = {
    //    case (x,y) if y!=0 => x/y
    //  }
    //
    //  println(divide.isDefinedAt(100,0))//false
    //
    //


    //    implicit val env = Environment("sad", "grey woods")
    //    User("Nancy",10).tellAboutEnvironment
  }

  //  case class Path(path:String) {
  //      def /(d:String):String = {
  //        path+"/"+d
  //      }
  //  }
  //
  //  println(Path("/home") / "user1")
  //
  //
  //  class Lol (reason:String) {
  //   private def getReason = reason
  //  }
  //  object Lol{
  //    def apply(s:String):Lol = {
  //      new Lol(s)
  //    }
  //
  //    def analyzeState(lol:Lol) {
  //      if(lol.getReason == "irony"){
  //        println("cool")
  //      } else {
  //        println("not cool")
  //      }
  //    }
  //  }
  //
  //  val myLol = Lol("irony")
  //  println(myLol)
  //  Lol.analyzeState(myLol)

  //  object Book{
  //    def fromString(s:String):Book = {
  //      val values = s.split(',')
  //      require(values.length>1)
  //      new Book(values(0), UUID.fromString(values(1)))
  //    }
  //  }
  //
  //  class Book(author: String, id: UUID) {
  //    require(!author.isEmpty)
  //    require(id !=null && id.toString.length>0)
  //
  //    def this(author: String) = {
  //      this(author, UUID.randomUUID())
  //    }
  //    override def toString(): String = {
  //      "Book(" + author + "," + id.toString.take(2) + ")"
  //    }
  //  }
  //  println(new Book("Lermontov"))
  //  println(new Book("Lomonosov", UUID.randomUUID()))
  //  println(Book.fromString("Greboedov,1fa6615f-ec61-4255-9307-d628c8e4e4c8"))
  //  //  new Book("")
  //  //  Book(Lermontov,3c)
  //  //  Book(Lomonosov,c9)
  //  //  Book(Greboedov,1f)
  //


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


//}
