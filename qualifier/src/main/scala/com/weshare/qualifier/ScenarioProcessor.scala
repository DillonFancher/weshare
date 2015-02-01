package com.weshare.qualifier

import com.twitter.util.Future
import org.jboss.netty.handler.codec.http._

class ScenarioProcessor extends DataContainers {
  /**
   * General workflow:
   * 1. Create an adventure in the adventure table with the leader being the user id
   * 2. Find the user's friends in the friends table and send then the invitation to join the adventure
   * 3. Register a handler for each friend's response:
   * - Acceptance + Do get previous photos    => Add user_id to adventure AND Send link to all previously taken photos in adventure
   * - Acceptance + Don't get previous photos => Add user_id to adventure and send link to picture that triggered invite
   * - Decline                                => Do Nothing
   */
  def createAdventureAndInviteFriends(requestData: RequestData): Future[HttpResponse] = {
    val boundary: Boundary = Boundary(requestData.geoTag)
    val friends: List[(String, Geo)] = findFriendsOfUser(requestData.userId)
    val friendsInBoundary: List[String] = findFriendsInBoundary(friends, boundary)
    sendInvitationToJoinAdventure(friendsInBoundary, requestData.pictureUrl)
  }


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

  def findFriendsOfUser(userId: String): List[(String, Geo)] = {
    List(("2", (1.0, 2.0)), ("3", (3.0, 4.0)))
  }

  def findFriendsInBoundary(friends: List[(String, Geo)], boundary: Boundary): List[String] = {
    friends.flatMap(friend => boundary.withinBoundary(friend))
  }

  def sendInvitationToJoinAdventure(friendsToInvite: List[String], pictureUrl: String): Future[HttpResponse] = {
    friendsToInvite.map(friend =>
      println(s"Sent picture: $pictureUrl, to user: $friend")
    )
    Future.value(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK))
  }

  def sendPicture(friend: String, pictureUrl: String): Future[HttpResponse] = {
    ???
  }

  def handleResponse(response: Future[HttpResponse]): Future[HttpResponse] = {
    response.onSuccess(s =>
      println("fuck ya")
    ).onFailure(f =>
      println("fuck no")
      )
  }

  def sendPictureUrl(userId: String, pictureUrl: String): Future[HttpResponse] = {
    println(s"Successfully delivered picture: $pictureUrl, to user: $userId")
    Future.value(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK))
  }
}
