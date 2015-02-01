name := "migratifier"

Common.settings

libraryDependencies ++= Seq(
    "com.h2database" % "h2" % "1.3.174",
    "org.flywaydb"   % "flyway-core" % "3.1"
)

seq(flywaySettings: _*)

flywayUrl := "jdbc:h2:file:target/foobar"

flywayUser := "SA"
