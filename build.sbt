
name := "http_tools"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "com.typesafe.akka" %% "akka-stream" % "2.5.8",
  "com.typesafe.akka" %% "akka-actor" % "2.5.8",

  // swing
  "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.1",
  "net.ruippeixotog" %% "scala-scraper" % "2.1.0",

  // skinny
  "org.skinny-framework" %% "skinny-http-client" % "3.0.1",
  "log4j" % "log4j" % "1.2.17",
  "org.slf4j" % "slf4j-log4j12" % "1.7.26" % Test
)