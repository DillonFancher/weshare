package com.weshare.routifier

import com.fasterxml.jackson.databind.JsonNode

trait RoutifierDataContainers {
  case class QualifierRequestData(requestJson: JsonNode) {
    val requestType = requestJson.get("type").toString
    val userId = requestJson.get("user_id").toString
    val adventureToken = requestJson.get("adventure_token").toString
    val pictureUrl = requestJson.get("picture_url").toString
  }
}
