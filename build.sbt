name := "WeShare"

Common.settings

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra" % "1.5.2",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5"
)

// Library projects are '*ers'
lazy val commoner = project in file("commoner")
lazy val servicer = (project in file("servicer")).dependsOn(commoner)


// Apps are '*ifiers'
lazy val queueifer   = (project in file("queueifier")).dependsOn(commoner)
lazy val qualifier   = (project in file("qualifier")).dependsOn(commoner)
lazy val migratifier = (project in file("migratifier")).dependsOn(commoner, servicer)
lazy val routifier = (project in file("routifier")).dependsOn(commoner)

lazy val weshare = (project in file(".")).aggregate(
  commoner,
  servicer,
  queueifer,
  migratifier, 
  qualifier,
  routifier
)
