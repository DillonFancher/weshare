lazy val root = (project in file(".")).
  aggregate(commoner, queueifier, qualifier)

// Library projects are '*ers'
lazy val commoner = project


// Apps are '*ifiers'
lazy val queueifier = project.dependsOn(commoner)
lazy val qualifier = project.dependsOn(commoner)
