name := "qualifier"

Common.settings

libraryDependencies ++= Seq(
   "com.twitter" %% "finatra" % "1.5.2",
    "com.amazonaws" % "aws-java-sdk" % "1.9.9",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.4",
    "org.json4s"  %% "json4s-jackson" % "3.2.4"
)

resolvers += "Twitter" at "http://maven.twttr.com"