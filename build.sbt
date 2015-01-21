organization := Common.org

// Library projects are '*ers'
lazy val commoner = project in file("Commoner")

// Apps are '*ifiers'
lazy val queueifer = (project in file("Queueifier")).dependsOn(commoner)

lazy val root = (project in file(".")).
aggregate(commoner, queueifer)

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra" % "1.5.2"
)

resolvers +=
  "Twitter" at "http://maven.twttr.com"
