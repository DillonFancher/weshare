package com.weshare.qualifier

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Charsets
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http._

class   ScenarioProcessor extends DataContainers {

  //Below is scenario where user is not in an adventure
  /**
   * General workflow:
   * 1. Create an adventure in the adventure table with the leader being the user id
   * 2. Find the user's friends in the friends table and send then the invitation to join the adventure
   * 3. Register a handler for each friend's response:
   * - Acceptance + Do get previous photos    => Add user_id to adventure AND Send link to all previously taken photos in adventure
   * - Acceptance + Don't get previous photos => Add user_id to adventure and send link to picture that triggered invite
   * - Decline                                => Do Nothing
   */
  def createAdventureAndInviteFriends(requestData: RequestData) {
    val boundary: Boundary = Boundary(requestData.geoTag)
    val friendsInBoundary = for {
      friend <- findFriendsOfUser(requestData.userId)
      friendInBoundary <- boundary.withinBoundary(friend)
    } yield friendInBoundary

    println(s"Sending invitations to users: $friendsInBoundary")
    sendInvitationToJoinAdventure(friendsInBoundary, requestData.pictureUrl)
  }

  def findFriendsOfUser(userId: String): List[(String, Geo)] = {
    List(("2", (1.0, 2.0)), ("3", (3.0, 4.0)))
  }

  def sendInvitationToJoinAdventure(friendsToInvite: List[String], pictureUrl: String): Future[HttpResponse] = {
    //send the invitation logic goes here
    val invitationResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)
    val invitationResults: List[String] = for {
      friendToInvite <- friendsToInvite
      result <- handleInvitationResponse(invitationResponse, friendToInvite, pictureUrl)
    } yield result

    println(s"The results of the invitations: $invitationResults")
    Future.value(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK))
  }

  def handleInvitationResponse(invitationResponses: HttpResponse, friendInBoundary: String, pictureUrl: String): Option[String] = {
      invitationResponses.getStatus.getCode match {
        case 200 =>
          //sendPictureUrl(friendInBoundary, pictureUrl)
          Some(s"Invitation accepted by user: $friendInBoundary")
        case _ =>
          Some(s"Failed to communicate with user: $friendInBoundary")
         // Future.value(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SERVICE_UNAVAILABLE))
      }
  }

  //Below is scenario where user is already in an adventure
  //--------------------------------------------------------------------------------------------------------------------
  /**
   * This is the easy part, if the user is already in a session
   */
  def sendToAllInAdventure(adventureToken: String, pictureUrl: String): Future[HttpResponse] = {
    getUsersInAdventure(adventureToken).map(userId =>
      sendPictureUrl(userId, pictureUrl)
    )
    Future.value(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK))
  }

  /**
   * This needs to be a mysql lookup for the current geo location of every phone in the session
   * logic: if geo location is being recorded then the phone is in the session otherwise, if it is not updating
   * geo information we flag the phone table for the corresponding phone and say they are no longer in the session.
   */
  def getUsersInAdventure(adventureToken: String): List[String] = {
    List("123", "456")
  }

  //--------------------------------------------------------------------------------------------------------------------

  //These are the common methods between the processors
  //--------------------------------------------------------------------------------------------------------------------
  def sendPictureUrl(userId: String, pictureUrl: String): Future[HttpResponse] = {
    println(s"Successfully delivered picture: $pictureUrl, to user: $userId")
    Future.value(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK))
  }

  def handleResponse(response: Future[HttpResponse]): Future[HttpResponse] = {
    response.onSuccess(s =>
      println("fuck ya")
    ).onFailure(f =>
      println("fuck no")
    )
  }

  val mapper = new ObjectMapper()

  def parseRequest(request: HttpRequest) = {
    mapper.readTree(request.getContent.toString(Charsets.UTF_8))
  }
}
