organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val akkaV = "2.1.4"
  val sprayV = "1.1.0"
  Seq(
    "io.spray"            %   "spray-can"     % sprayV,
    "io.spray"            %   "spray-routing" % sprayV,
    "io.spray"            %   "spray-testkit" % sprayV,
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV,
    "org.specs2"          %%  "specs2"        % "2.2.3" % "test",
    "com.ning" % "async-http-client" % "1.7.23",
    "org.slf4j" % "slf4j-simple" % "1.7.5",
    "org.apache.hadoop" % "hadoop-client" % "1.2.1",
    "org.jsoup" % "jsoup" % "1.7.2"
  )
}

seq(Revolver.settings: _*)
