name := "commoner"

organization := Common.org

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "com.amazonaws"      %  "aws-java-sdk" % "1.9.9",
  "org.specs2"         %% "specs2-core"  % "2.4.14" % "test",
  "org.apache.commons" % "commons-io"    % "1.3.2",
  "org.apache.kafka"   % "kafka_2.8.2"   % "0.8.1",
  "ly.stealth"         % "scala-kafka"   % "0.1.0.0",
  "com.typesafe"       % "config"        % "1.2.1"
)