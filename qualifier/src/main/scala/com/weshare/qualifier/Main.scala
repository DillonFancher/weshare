package com.weshare.qualifier

import com.twitter.finagle.{Http, Service}
import com.twitter.util.{Await, Future}
import org.jboss.netty.handler.codec.http._

object Main extends ScenarioProcessor {

  val isUserInAdventure = false

  def main(args: Array[String]) {
    val service = new Service[HttpRequest, HttpResponse] {
      def apply(request: HttpRequest): Future[HttpResponse] = {
        val requestData: RequestData = RequestData(parseRequest(request))

        //This needs to be the mysql query that checks the adventure status
        if (isUserInAdventure) {
          sendToAllInAdventure(requestData.adventureToken, requestData.pictureUrl)
        } else {
          createAdventureAndInviteFriends(requestData)
        }

        Future.value(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK))

      }
    }

    val server = Http.serve(":8080", service)
    Await.ready(server)
  }
}
