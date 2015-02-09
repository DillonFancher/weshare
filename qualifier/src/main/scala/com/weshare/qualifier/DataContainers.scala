package com.weshare.qualifier

import com.fasterxml.jackson.databind.JsonNode

trait DataContainers {
  /**
   * Put all types below
   */
  case class Friend(userId: String, latLon: LatLon, adventureStatus: Int) {
    val this.userId = userId
    val this.latLon = latLon
    val lat = latLon.lat
    val lon = latLon.lon
    val this.adventureStatus = adventureStatus

  }

  case class LatLon(lattitude: Double, longitude: Double) {
    val lat = lattitude
    val lon = longitude
  }
  /**
   * Container for picture data coming from queueifier
   */
  case class RequestData(requestJson: JsonNode) {
    val adventureToken = requestJson.get("adventure_token").toString
    val userId = requestJson.get("user_id").toString
    val pictureUrl = requestJson.get("image_url_to_S3").toString
    val geoTag: LatLon = LatLon(requestJson.get("geo").get("lat").asDouble(), requestJson.get("geo").get("lon").asDouble())
    val boundary: Boundary = Boundary(LatLon)
  }

  case class Boundary(geoCoord: LatLon) {
    val lat1 = math.toRadians(geoCoord.lat)
    val lon1 = math.toRadians(geoCoord.lon)

    def withinBoundary(friend: Friend): Option[Friend] = {
      val lat2 = math.toRadians(friend.lat)
      val lon2 = math.toRadians(friend.lon)
      val dlon = math.abs(lat1 - lat2)
      val dlat = math.abs(lon1 - lon2)

      val a = math.pow(math.sin(dlat / 2), 2) +
        (math.cos(lat1) * math.cos(lat2) * math.pow(math.sin(dlon / 2), 2))
      val c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
      val distance = 3961 * c

      if (distance < 20) {
        Some(Friend)
      } else {
        None
      }
    }
  }

}
