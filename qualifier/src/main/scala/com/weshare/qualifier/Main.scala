package com.weshare.qualifier

import com.twitter.finagle.{Http, Service}
import com.twitter.util.{Await, Future}
import org.jboss.netty.handler.codec.http._

object Main extends ScenarioProcessor {

  val isUserInAdventure = false

  /**
   * Sample String JSON Body for picture data:
   *'{"adventure_token":123456,"user_id":234567890,"image_url_to_S3":"https://dillon.is.awesome.com","geo":{"lat":90.01,"lon":40.2}}'
   */

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
