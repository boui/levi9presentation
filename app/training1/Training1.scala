package training1

import scala.Array
import scala.collection.mutable.ArrayBuffer


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

object Training1 {

  def simple_nums_tr2also(n: Int, m: Int) = {
    val sqrtMap = scala.collection.mutable.Map[Int, Double]()
    val simplesSet = scala.collection.mutable.ArrayBuffer[Int]()
    val simplesList = scala.collection.mutable.ArrayBuffer[Int]()

    def getSqrt(i: Int): Double = {
      sqrtMap.get(i) match {
        case Some(number) => number
        case None => {
          val sqrt = Math.sqrt(i)
          sqrtMap += (i -> sqrt)
          sqrt
        }
      }
    }

    def genPrimesUntilN() {
      for (num <- 2 to n) {
        var i = 2
        var isPrime = true
        while (isPrime && i < getSqrt(num)) {
          if (num % i == 0) {
            isPrime = false
          }
          i += 1
        }
        if (isPrime) simplesList += num
      }
    }


    def isPrime(num: Int): Boolean = {
      var i = 0
      var isPrime = true

      while (isPrime && i < simplesList.length && simplesList(i) <= getSqrt(num)) {
        if (num % simplesList(i) == 0) {
          isPrime = false
        }
        i += 1
      }
      isPrime
    }

    genPrimesUntilN()
    for (i <- n to m) {
      if (isPrime(i)) {
        simplesList += i
        simplesSet += i
      }
    }
    simplesSet.toList
  }

  def expression_signs(nums: List[Int], targetSum: Int) = {
    val n = nums.length
    val signs = scala.collection.mutable.ArrayBuffer.fill(n - 1)(false)
    def p(k: Int, sum: Int) {
      if (k >= 0) {
        signs(k) = true
        p(k - 1, sum + nums(k + 1))
        signs(k) = false
        p(k - 1, sum - nums(k + 1))
      } else {
        val sum2 = if (signs(0)) {
          sum + nums(0)
        } else {
          sum - nums(0)
        }

        if (sum2 == targetSum) {
          println("Found solution" + signs.mkString("[", ",", "]"))
        }
      }
    }
    p(n - 2, 0)
  }

  def max_sub_sequence(a: List[Int]) {
    val n = a.length
    val d = ArrayBuffer.fill(n)(1)
    val p = ArrayBuffer.fill(n)(-1)
    for (index <- 0 to n - 1) {
      for (j <- 0 to index) {
        if (a(j) < a(index)) {
          if (d(index) < d(j) + 1) {
            p(index) = j
            d(index) = d(j) + 1
          }
        }
      }
    }

    val longestIndex = d.zipWithIndex.maxBy(_._1)._2
    val result = ArrayBuffer[Int]()
    var i = longestIndex
    for (_ <- 1 to longestIndex if i != -1) {
      result += a(i)
      i = p(i)
    }

    println(result.reverse)
  }

  def point_int_triangle(A: (Int, Int), B: (Int, Int), C: (Int, Int), point: (Int, Int)) {
    def variant1() = {
      def square(A: (Int, Int), B: (Int, Int), C: (Int, Int)) = {
        Math.abs((B._1 - A._1) * (C._2 - A._2) - (C._1 - A._1) * (B._2 - A._2)) / 2
      }
      if (square(A, B, point) + square(B, C, point) + square(A, C, point) <= square(A, B, C)) {
        println("In")
      } else {
        println("Out")
      }
    }

    def variant2() {
      def cos(A: (Int, Int), B: (Int, Int), C: (Int, Int)) = {
        val (dx1, dy1) = (B._1 - A._1, B._2 - A._2)
        val (dx2, dy2) = (C._1 - A._1, C._2 - A._2)
        import Math._
        val (len1, len2) = (sqrt(pow(dx1, 2) + pow(dy1, 2)), sqrt(pow(dx2, 2) + pow(dy2, 2)))
        val res = (dx1 * dx2 + dy1 * dy2) / (len1 * len2)
        res
      }

      if (A == point || B == point || C == point) println("In(vertex)")
      else if (cos(A, B, point) >= cos(A, B, C) && cos(B, C, point) >= cos(B, A, C)) println("In")
      else println("Out")
    }

    variant1()
    variant2()
  }

  def pow_big_numbers(n: Int, m: Int) {
    def div(num: Int, div: Int) = {
      var number = num
      var i = 0
      while (number - div >= 0) {
        number -= div
        i += 1
      }
      i
    }

    def alg(n:Int, m:Int) = {
      val digit = ArrayBuffer.fill(m + 1)(-1)
      digit(0) = 1
      var carry = 0
      for (p <- 1 to m) {
        var i = 0
        while (digit(i) != -1) {
          carry += digit(i) * n
          digit(i) = carry % 10
          carry = div(carry, 10)
          i += 1
        }

        if (digit(i) == -1 && carry > 0) digit(i) = carry
        carry = 0
      }
      digit
    }

    println(alg(n, m).reverse.mkString)
  }


  def poker(combination: List[Int]) = {
    def similarpairs = {
      combination.foldLeft(scala.collection.mutable.HashMap[Int, Int]()) {
        (acc, e) => {
          if (acc.contains(e)) acc(e) += 1
          else acc += (e -> 1)
          acc
        }
      }.values.toList.sorted
    }

    def is_street(combination: List[Int]) = {
      val list = for (i <- 1 to combination.size - 1) yield {
        Math.abs(combination(i - 1) - combination(i)) == 1
      }
      !list.contains(false)
    }
    val sim = similarpairs
    sim match {
      case List(5) => println("imposible")
      case List(1, 4) => println("four of a kind")
      case List(2, 3) => println("full house")
      case List(1, 1, 3) => println("three of a kind")
      case List(1, 2, 2) => println("two pairs")
      case List(1, 1, 1, 2) => println("pair")
      case a => {
        if (is_street(combination.sorted)) println("street")
        else println("nothing")
      }
    }
  }

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
  def main(args: Array[String]) {
    def timeCheck(f: () => Unit, name: String = "") {
      val t = System.currentTimeMillis()
      f()
      println("Spent time on " + name + ":" + (System.currentTimeMillis() - t))
    }

    /** Training1 */
        val f1 = ()=>println(simple_nums_tr2also(1000, 30000))
        timeCheck(f1, "Task1, T1" )
        val f2 = ()=>expression_signs(List(1, 4, 5, 3, 9, 11, 55, 12, 83, 1, 77, 29, 74, 7, 28, 99, 10, 89, 82, 11, 27, 64, 83, 82), 332)
        timeCheck(f2, "Task2 v1, T1" )
        timeCheck(() => max_sub_sequence(List(1, 2, 3, 4, 8, 3, 9, 1, 54, 3, 5, 4, 3, 2, 10, 3, 4)), "maximal subsequence of symbols")
        timeCheck(() => point_int_triangle((-10000, 10000), (1230, -20934), (1212, 73873), (0, 0)), "is point in triangle")
        timeCheck(() => pow_big_numbers(3, 2500), "pow really big numbers")
        timeCheck(() => poker(List(12, 2, 3, 4, 5)))
        timeCheck(() => poker(List(1, 2, 3, 4, 5)))
        timeCheck(() => poker(List(2, 2, 5, 5, 5)))
        timeCheck(() => poker(List(4, 4, 4, 4, 5)))
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


}




