package com.weshare.qualifier

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Charsets
import com.twitter.finagle.{Http, Service}
import com.twitter.util.{Future, Await}
import org.jboss.netty.handler.codec.http._

object Main {

  val mapper = new ObjectMapper()

  def main(args: Array[String]) {
    val service = new Service[HttpRequest, HttpResponse] {
      def apply(request: HttpRequest): Future[HttpResponse] = {


        val requestJson: JsonNode = mapper.readTree(request.getContent.toString(Charsets.UTF_8))

        val sessionAndGeo: (String, (String, String)) = parseSessionAndGeo(requestJson)
        val newBoundary: Boundary = Boundary(sessionAndGeo._1, sessionAndGeo._2)

        getPhonesInSession(sessionAndGeo._1).map( m =>
          if(newBoundary.withinBoundary(m._2)) {
            println("This phone, " + m._1 + " is within the boundary, send it the picture.")
            //handleResponse(sendPicture(newBoundary.pictureId, m._1))
          }
        )
        println("The Request Body Contained: " + request.getContent.toString(Charsets.UTF_8))
        Future.value(new DefaultHttpResponse(request.getProtocolVersion, HttpResponseStatus.OK))
      }
    }

    val server = Http.serve(":8080", service)
    Await.ready(server)

  }


  /**
   * Keep this in a Tuple 3 for now
   */
  def parseSessionAndGeo(picJson: JsonNode): (String, (String, String)) = {
    (picJson.get("sessionToken").toString, (picJson.get("lat").toString, picJson.get("lon").toString))
  }

  /**
   * This needs to be a mysql lookup for the current geo location of every phone in the session
   * logic: if geo location is being recorded then the phone is in the session otherwise, if it is not updating
   * geo information we flag the phone table for the corresponding phone and say they are no longer in the session.
   */
  def getPhonesInSession(sessionToken: String): List[(String, (Double, Double))] = {
    List(("1", (1.1, 1.1)), ("2",(1.1, 1.1)), ("3", (1.1, 1.1)), ("4", (1.1, 1.1)))
  }

  def sendPicture(pictureId: String, userId: String): Future[HttpResponse] = {
    ???
  }

  def handleResponse(response: Future[HttpResponse]): Future[HttpResponse] = {
    response.onSuccess( s =>
      println("fuck ya")
    ).onFailure( f =>
      println("fuck no")
    )
  }

/**
 * Sample String JSON Body for picture data:
 * '{"sessionToken":"abcd1234", "lat":"-90.09", "lon":"90.09"}'
 * Each picture will need two identifiers to be unique, the sessionToken and the pictureId which will start at 0 and go up
 * from there, i.e.
 *    pictureIds = <sessionToken>-0, <sessionToken>-1, <sessionToken>-2, ...
 */

  case class Boundary(sessionToken: String, initialGeoCoord: (String, String)) {
    val lat1 = math.toRadians(initialGeoCoord._1.toDouble)
    val lon1 = math.toRadians(initialGeoCoord._2.toDouble )
    val pictureId = sessionToken + "1"

    def withinBoundary(geoCoord: (Double, Double)): (Boolean) = {
        val lat2 = math.toRadians(geoCoord._1)
        val lon2 = math.toRadians(geoCoord._2)
        val dlon = math.abs(lat1-lat2)
        val dlat = math.abs(lon1-lon2)

        val a = math.pow(math.sin(dlat / 2), 2) +
                (math.cos(lat1)*math.cos(lat2)*math.pow(math.sin(dlon/2),2))
        val c = 2*math.atan2(math.sqrt(a), math.sqrt(1-a))
        val distance = 3961*c

        val inB: Boolean = distance < 20
        inB
      }
  }
}