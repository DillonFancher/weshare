organization := Common.org

// Library projects are '*ers'
lazy val commoner = project in file("commoner")

// Apps are '*ifiers'
lazy val queueifer = (project in file("queueifier")).dependsOn(commoner)
lazy val qualifier = (project in file("qualifier")).dependsOn(commoner)
lazy val routifier = (project in file("routifier")).dependsOn(commoner)
lazy val root = (project in file(".")).
aggregate(commoner, queueifer, qualifier, routifier)

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra" % "1.5.2",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5"
)

resolvers +=
  "Twitter" at "http://maven.twttr.com"
