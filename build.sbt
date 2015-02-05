name := "root"

Common.settings

// Library projects are '*ers'
lazy val commoner = project in file("commoner")
lazy val servicer = (project in file("servicer")).dependsOn(commoner)


// Apps are '*ifiers'
lazy val queueifer   = (project in file("queueifier")).dependsOn(commoner)
lazy val qualifier   = (project in file("qualifier")).dependsOn(commoner)
lazy val migratifier = (project in file("migratifier")).dependsOn(commoner, servicer)

lazy val root = (project in file(".")).aggregate(
  commoner,
  servicer,
  queueifer,
  migratifier, 
  qualifier
)