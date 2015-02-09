package com.weshare.qualifier

import com.twitter.util.Future
import com.weshare.qualifier.ScenarioConfig._
import org.jboss.netty.handler.codec.http._
import org.slf4j.{Logger, LoggerFactory}

class ScenarioProcessor extends DataContainers {
  private val logger: Logger = LoggerFactory.getLogger(classOf[ScenarioProcessor])

  //--------------------------------------------------------------------------------------------------------------------
  //Below is scenario where user is not in an adventure
  //--------------------------------------------------------------------------------------------------------------------

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
      friend <- lookupFriendsOfUser(requestData.userId)
      friendInBoundary <- boundary.withinBoundary(friend.userId, friend.latLon)
    } yield areFriendsInAdventureAlready(friendInBoundary, requestData.pictureUrl)

    logger.info(s"Sending an invitation to adventure: ${requestData.adventureToken} to users: $friendsInBoundary, with initial picture: ${requestData.pictureUrl}")
    sendInvitationsToJoinAdventure(friendsInBoundary, requestData.adventureToken, requestData.pictureUrl)
  }

  /**
   * This method will need to check if any of the user's friends are already in an adventure and make a ***decision*** ;)
   * returns userId, userLocation, adventureStatus
   */
  def areFriendsInAdventureAlready(userId: String, pictureUrl: String): List[Friend] = {
    lookupFriendsOfUser(userId).flatMap { friend =>
      friend.adventureStatus match {
        case 0 => Some(friend)
        case 1 =>
          sendInvitationToJoinAdventure(friend.userId, lookupCurrentAdventureToken(friend.userId), pictureUrl)
          None
      }
    }
  }

  /**
   * 3 table lookups happen here, one look up in the friends table,
   * then a lookup in the users table for the friends adventure status,
   * then a look up in the user location table for the friend's current location.
   */
  def lookupFriendsOfUser(userId: String): List[Friend] = {
    List(Friend("2", LatLon(1.0, 2.0), 0), Friend("3", LatLon(3.0, 4.0), 0))
  }

  /**
   * This will be a mysql lookup in the adventure table for the adventure the user is currently in.
   */
  def lookupCurrentAdventureToken(userId: String): String = {
    "1234"
  }

  def sendInvitationsToJoinAdventure(friendsToInvite: List[String], adventureToken: String, pictureUrl: String) = {
    friendsToInvite.map { friendToInvite =>
      sendInvitationToJoinAdventure(friendToInvite, adventureToken, pictureUrl)
    }
  }

  def sendInvitationToJoinAdventure(friendToInvite: String, adventureToken: String, pictureUrl: String) = {
    val routifierRequest = constructRequestJson(type_invitation, friendToInvite, pictureUrl, adventureToken)
    logger.info(s"$routifierRequest")
    handleInvitationResponse(qualifierClient(buildRoutifierRequest(type_invitation, friendToInvite, pictureUrl, adventureToken)), adventureToken, friendToInvite, pictureUrl)
  }

  def handleInvitationResponse(invitationResponse: Future[HttpResponse], adventureToken: String, friendInBoundary: String, pictureUrl: String): Future[HttpResponse] = {
    invitationResponse.onSuccess { s =>
      s.getStatus.getCode match {
        case 200 =>
          logger.info(s"Invitation successfully received by routifier for user: $friendInBoundary, in adventure: $adventureToken")
        case _ =>
          logger.error(s"Invitation failed: $invitationResponse \n for picture: $pictureUrl, to friend: $friendInBoundary")
      }
    }.onFailure { f =>
      logger.error(s"Failed to communicate with routifier, no ack on picture url.")
    }
  }

  //--------------------------------------------------------------------------------------------------------------------
  //Below is scenario where user is already in an adventure
  //--------------------------------------------------------------------------------------------------------------------

  /**
   * This is the easy part, if the user is already in a session
   */
  def sendToAllInAdventure(adventureToken: String, pictureUrl: String) = {
    getUsersInAdventure(adventureToken).map { adventurer =>
      handleInvitationResponse(qualifierClient(buildRoutifierRequest(type_picture_url, adventureToken, adventurer, pictureUrl)), adventureToken, adventurer, pictureUrl)
    }
  }

  /**
   * This needs to be a mysql lookup for the current geo location of every phone in the session
   * logic: if geo location is being recorded then the phone is in the session otherwise, if it is not updating
   * geo information we flag the phone table for the corresponding phone and say they are no longer in the session.
   */
  def getUsersInAdventure(adventureToken: String): List[String] = {
    List("123", "456")
  }

  def handlePictureResponse(pictureResponse: Future[HttpResponse], userId: String, pictureUrl: String, adventureToken: String) = {
    pictureResponse.onSuccess { s =>
      s.getStatus.getCode match {
        case 200 =>
          logger.info(s"Picture url sent to routifier for user: $userId")
        case _ =>
          logger.error(s"Sending picture url to routifier failed: $pictureResponse \n For picture: $pictureUrl, to friend: $userId, in adventure:$adventureToken")
      }
    }.onFailure { f =>
      logger.error(s"Failed to communicate with routifier, no ack on picture url.")
    }
  }
}
