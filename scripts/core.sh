PORT='7000'
java -Dcom.twitter.finatra.config.env=production -Dcom.twitter.finatra.config.adminPort='' -Dcom.twitter.finatra.config.port=:$PORT -cp target/classes:target/dependency/* com.weshare.core.App
