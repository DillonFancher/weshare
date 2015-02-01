import sbt._
import sbt.Keys._

object Common {
  val settings: Seq[Setting[_]] = Seq(
    organization := "com.weshare",
    version := "1.2.3-SNAPSHOT", 
    scalaVersion := "2.10.4"
  )
}