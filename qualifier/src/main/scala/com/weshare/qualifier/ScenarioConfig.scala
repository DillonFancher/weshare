package com.weshare.qualifier

import java.net.URL

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Charsets
import com.twitter.finagle.Service
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{RequestBuilder, Http}
import com.twitter.util.{Base64StringEncoder, Future}
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.buffer.ChannelBuffers.wrappedBuffer

object ScenarioConfig {
  val contentType = "application/json"
  val userNameForAuth = "user"
  val hostName = "localhost"
  val portNumber = "8090"
  val type_invitation = "invitation"
  val type_picture_url = "picture_url"
  val field_request_type = "request_type"
  val field_picture_url = "picture_url"
  val field_friend_id = "friend_id"

  def buildRoutifierRequest(requestType: String, friendId: String, pictureUrl: String, adventureToken: String): HttpRequest = {
    RequestBuilder()
      .url(new URL("http", hostName, portNumber))
      .setHeader(HttpHeaders.Names.AUTHORIZATION, "Basic " + Base64StringEncoder.encode(userNameForAuth.getBytes))
      .setHeader("Content-Type", contentType)
      .buildPost(wrappedBuffer(ScenarioConfig.constructRequestJson(requestType, friendId, pictureUrl, adventureToken).getBytes))
  }

  def constructRequestJson(requestType: String, userId: String, pictureUrl: String, adventureToken: String) = {
    "{\"type\": \"" + requestType + "\", \"user_id\": \"" + userId + "\", \"picture_url\": " + pictureUrl + ", \"adventure_token\": \"" + adventureToken + "\"}"
  }

  val qualifierClient: Service[HttpRequest, HttpResponse] = {
    ClientBuilder()
      .codec(Http())
      .hosts(s"$hostName:$portNumber") // If >1 host, client does simple load-balancing
      .hostConnectionLimit(1)
      .build
  }

  val mapper = new ObjectMapper()

  def parseRequest(request: HttpRequest) = {
    mapper.readTree(request.getContent.toString(Charsets.UTF_8))
  }

}
