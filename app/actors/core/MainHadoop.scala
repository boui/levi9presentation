package com.boui.core

import akka.actor.{ActorSystem, Props}

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import java.net.URI
import java.io.{OutputStreamWriter, BufferedWriter}
import org.apache.hadoop.util.Progressable

/**
 * User: boui
 * Date: 1/13/14
 */
//object MainHadoop {
//  def main(args: Array[String]) {
//    println("HEY")
//    val configuration = new Configuration()
//    val hdfs = FileSystem.get( new URI( "hdfs://127.0.0:9000" ), configuration )
//    val file = new Path("hdfs://localhost:9000/table.html")
//    if (hdfs.exists(file)) { hdfs.delete( file, true ) }
//    val os = hdfs.create(file)
//    val br = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) )
//    br.write("Hello World")
//    br.close()
//    hdfs.close()
//  }
//}
