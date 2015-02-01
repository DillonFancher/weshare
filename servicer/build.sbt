name := "services"

Common.settings

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.18",
  "com.sandinh" % "play-jdbc-standalone_2.10" % "2.1.2"
)