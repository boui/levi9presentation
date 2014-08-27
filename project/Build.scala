import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "Levi9AsynchronousPresentation"
  val organization = "com.boui"
  val appVersion = "1.0"
  val akkaV = "2.1.4"
  val scalaVersion = "2.10.3"

  val appDependencies = Seq(
    "org.specs2" %% "specs2" % "1.14" % "test",
    "commons-codec" % "commons-codec" % "1.7",
    "com.typesafe.akka" %% "akka-testkit" % "2.1.0",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV,
    "org.specs2"          %%  "specs2"        % "2.2.3" % "test",
    "com.ning" % "async-http-client" % "1.7.23",
    "org.apache.hadoop" % "hadoop-client" % "1.2.1",
    "org.jsoup" % "jsoup" % "1.7.2"
  ) 

  val main = play.Project(appName, appVersion, appDependencies).settings(
    playDefaultPort := 8081
  )

}
