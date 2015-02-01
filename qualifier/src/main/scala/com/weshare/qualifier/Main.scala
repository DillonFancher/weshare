package com.weshare.qualifier

import com.fasterxml.jackson.databind.{ObjectMapper, JsonNode}
import com.google.common.base.Charsets
import com.twitter.finagle.{Http, Service}
import com.twitter.util.{Future, Await}
import org.jboss.netty.handler.codec.http._

object Main extends ScenarioProcessor{

  val isUserInAdventure = true

  def main(args: Array[String]) {
    val service = new Service[HttpRequest, HttpResponse] {
      def apply(request: HttpRequest): Future[HttpResponse] = {
        val pictureData: RequestData = parseRequest(request)

        //This needs to be the mysql query that checks the adventure status
        if (isUserInAdventure) {
          sendToAllInAdventure(pictureData.adventureToken, pictureData.pictureUrl)
        } else {
          createAdventureAndInviteFriends(pictureData)
        }
      }
    }

    val server = Http.serve(":8080", service)
    Await.ready(server)
  }

  val mapper = new ObjectMapper()
  def parseRequest(request: HttpRequest): RequestData = {
    val requestJson: JsonNode = mapper.readTree(request.getContent.toString(Charsets.UTF_8))
    RequestData(requestJson)
  }
  
}