name := "qualifier"

version := "1.0"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
    "com.amazonaws" % "aws-java-sdk" % "1.9.9",
    "com.twitter" %% "finagle-http" % "6.22.0",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)
