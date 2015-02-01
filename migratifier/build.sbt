name := "migratifier"

organization := Common.org

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.5"

organization := Common.org

libraryDependencies ++= Seq(
    "com.h2database" % "h2" % "1.3.174"
)

seq(flywaySettings: _*)

flywayUrl := "jdbc:h2:file:target/foobar"

flywayUser := "SA"
