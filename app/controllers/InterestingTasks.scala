package controllers

import scala.collection.mutable

/**
 * User: boui
 * Date: 3/16/14
 */
object InterestingTasks {
  def go() {

    val str = "IaehvakitRocksy"
    val dict = List("I", "have", "a","kit", "Rocksy")
    val dictMap = dict.foldLeft(Map[String, String]())((acc, x) => acc + (x.sorted -> x))
    println(dictMap.mkString("[", ",", "]"))

    def sliding(wsize: Int) = {
      (for(start <- 0 to str.length-wsize) yield str.substring(start, start+wsize)).toList
    }

    def alg(res: mutable.Buffer[String], wsize: Int): List[String] = {
      if (wsize < str.length) {
         sliding(wsize).map {
          window => {
             dictMap.get(window.sorted).map(x=> res+=x)
          }
        }
        println("wsize="+wsize+";list="+res.mkString("[",",","]"))
        alg(res, wsize + 1)
      } else res.toList
    }

    println(alg(mutable.Buffer[String](), 1).mkString("[", ",", "]"))
  }
}
