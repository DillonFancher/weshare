lazy val root = (project in file(".")).
  settings(
    name := "qualifier"
  )

version := "1.0"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
   "com.twitter" %% "finatra" % "1.5.2",
    "com.amazonaws" % "aws-java-sdk" % "1.9.9",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.4",
    "org.json4s"  %% "json4s-jackson" % "3.2.4",
    "org.slf4j" % "slf4j-api" % "1.7.5",
    "org.slf4j" % "slf4j-simple" % "1.7.5"
)

resolvers += "Twitter" at "http://maven.twttr.com"