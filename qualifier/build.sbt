name := "qualifier"

organization := Common.org

version := "1.0"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
   "com.twitter" %% "finatra" % "1.5.2",
    "com.amazonaws" % "aws-java-sdk" % "1.9.9",
    "com.twitter" %% "finagle-http" % "6.22.0",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)

resolvers += "Twitter" at "http://maven.twttr.com"