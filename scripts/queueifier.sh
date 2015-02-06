PORT='8000'
BASE='../Queifier'
LOGGER='conf/logger.xml'
OPTIONS -Dlogback.configurationFile=$LOGGER -Dcom.twitter.finatra.config.env=production -Dcom.twitter.finatra.config.adminPort='' -Dcom.twitter.finatra.config.port=:$PORT -cp $BASE/target/classes:$BASE/target/dependency/* com.weshare.Queueifier.App
sbt 'project Queueifier' start-script
