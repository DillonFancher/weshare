package com.weshare.qualifier

import com.fasterxml.jackson.databind.JsonNode

trait DataContainers {
  /**
   * Put all types below
   */
  type Geo = (Double, Double)

  /**
   * Container for picture data coming from queueifier
   */
  case class RequestData(requestJson: JsonNode) {
    val adventureToken = requestJson.get("adventure_token").toString
    val userId = requestJson.get("user_id").toString
    val pictureUrl = requestJson.get("image_url_to_S3").toString
    val geoTag: Geo = (requestJson.get("geo").get("lat").asDouble(), requestJson.get("geo").get("lon").asDouble())
    val boundary: Boundary = Boundary(geoTag)
  }

  /**
   * Sample String JSON Body for picture data:
   *'{"adventure_token":123456,"user_id":234567890,"image_url_to_S3":"https://dillon.is.awesome.com","geo":{"lat":90.01,"lon":40.2}}'
   */
  case class Boundary(geoCoord: (Double, Double)) {
    val lat1 = math.toRadians(geoCoord._1.toDouble)
    val lon1 = math.toRadians(geoCoord._2.toDouble)

    def withinBoundary(userIdAndGeoCoord: (String, (Double, Double))): Option[String] = {
      val lat2 = math.toRadians(geoCoord._1)
      val lon2 = math.toRadians(geoCoord._2)
      val dlon = math.abs(lat1 - lat2)
      val dlat = math.abs(lon1 - lon2)

      val a = math.pow(math.sin(dlat / 2), 2) +
        (math.cos(lat1) * math.cos(lat2) * math.pow(math.sin(dlon / 2), 2))
      val c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
      val distance = 3961 * c

      if (distance < 20) {
        Some(userIdAndGeoCoord._1)
      } else {
        None

      }
    }
  }

}
