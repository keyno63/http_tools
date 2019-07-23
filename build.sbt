name := "http_tools"

version := "0.1"

scalaVersion := "2.12.7"
val circeVersion = "0.9.3"

libraryDependencies ++= Seq(
  // skinny
  "org.skinny-framework" %% "skinny-http-client" % "3.0.1",
  "log4j" % "log4j" % "1.2.17",
  "org.slf4j" % "slf4j-log4j12" % "1.7.26" % Test,

  //scalaj
  "org.scalaj" %% "scalaj-http" % "2.4.2",

  //sttp
  "com.softwaremill.sttp" %% "core" % "1.6.3",

  //akka
  "com.typesafe.akka" %% "akka-http" % "10.1.9",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.9" % Test,
  "com.typesafe.akka" %% "akka-actor" % "2.5.23",
  "com.typesafe.akka" %% "akka-stream" % "2.5.23",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.23" % Test,
  
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)