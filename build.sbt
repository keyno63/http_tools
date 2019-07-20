name := "http_tools"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  // skinny
  "org.skinny-framework" %% "skinny-http-client" % "3.0.1",
  "log4j" % "log4j" % "1.2.17",
  "org.slf4j" % "slf4j-log4j12" % "1.7.26" % Test,

  //scalaj
  "org.scalaj" %% "scalaj-http" % "2.4.2"
)