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
      friendInBoundary <- boundary.withinBoundary(friend)
    } yield friendInBoundary

    val splitAdventurerList = whoIsInAdventureAndWhoIsNot(friendsInBoundary)
    splitAdventurerList._1 match {
      case true =>
        logger.info(s"Some friends of user: ${requestData.userId}, are already in an adventure, sending photo and invitations.")
        sendPicturesAndInvitations(splitAdventurerList._2, splitAdventurerList._3.get, requestData.pictureUrl)
      case false =>
        logger.info(s"Sending an invitation to adventure: ${requestData.adventureToken} to users: $friendsInBoundary, with initial picture: ${requestData.pictureUrl}")
        sendInvitationsToJoinAdventure(friendsInBoundary, requestData.adventureToken, requestData.pictureUrl)
    }
  }

  def sendPicturesAndInvitations(decisionList: (List[(Friend, Boolean)]), adventureToken: String , pictureUrl: String) = {
    logger.info(s"$decisionList")
    decisionList.map { decision =>
      decision._2 match {
        case true  =>
          logger.info("sending picture to user who was already in adventure")
          sendToUserInAdventure(adventureToken, decision._1.userId, pictureUrl)
        case false =>
          logger.info("sending invitation to user who was not in an adventure")
          sendInvitationToJoinAdventure(decision._1, adventureToken, pictureUrl)
      }
    }
  }

  def whoIsInAdventureAndWhoIsNot(friendsInBoundary: List[Friend]): (Boolean, List[(Friend, Boolean)], Option[String]) = {
    val decisionList = friendsInBoundary.flatMap { friendInBoundary =>
      friendInBoundary.adventureStatus match {
        case 0 =>
          logger.info("user's adventure status was 0")
          Some(friendInBoundary, false)
        case 1 =>
          logger.info("user's adventure status was 1")
          Some(friendInBoundary, true)
      }
    }

    val adventureToken = decisionList.map { entry =>
      entry._2 match {
        case false =>
          logger.info("user was not in adventure")
          None
        case true =>
          logger.info("user IS IN AN ADVENTURE")
          lookupCurrentAdventureToken(entry._1.userId)
      }
    }.toSet

    adventureToken.size match {
      case 1 =>
        logger.info("adventure token size was 0")
        (false, decisionList, None)
      case 2 =>
        logger.info("adventure token size was 1")
        (true, decisionList, adventureToken.head)
    }
  }

  /**
   * 3 table lookups happen here, one look up in the friends table,
   * then a lookup in the users table for the friends adventure status,
   * then a look up in the user location table for the friend's current location.
   */
  def lookupFriendsOfUser(userId: String): List[Friend] = {
    val a = List(Friend("2", LatLon(1.0, 2.0), 1), Friend("3", LatLon(3.0, 4.0), 0))
    logger.info(s"looking up friends of user: $a")
    a
  }

  /**
   * This will be a mysql lookup in the adventure table for the adventure the user is currently in.
   */
  def lookupCurrentAdventureToken(userId: String): Option[String] = {
    Some("1234")
  }

  def sendInvitationsToJoinAdventure(friendsToInvite: List[Friend], adventureToken: String, pictureUrl: String) = {
    friendsToInvite.map { friendToInvite =>
      sendInvitationToJoinAdventure(friendToInvite, adventureToken, pictureUrl)
    }
  }

  def sendInvitationToJoinAdventure(friendToInvite: Friend, adventureToken: String, pictureUrl: String) = {
    val routifierRequest = constructRequestJson(type_invitation, friendToInvite.userId, pictureUrl, adventureToken)
    logger.info(s"$routifierRequest")
    handleInvitationResponse(qualifierClient(buildRoutifierRequest(type_invitation, friendToInvite.userId, pictureUrl, adventureToken)), adventureToken, friendToInvite.userId, pictureUrl)
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
      sendToUserInAdventure(adventureToken, adventurer, pictureUrl)
    }
  }

  def sendToUserInAdventure(adventureToken: String, adventurer: String, pictureUrl: String) = {
    val pictureRequest =  constructRequestJson(type_picture_url, adventurer, pictureUrl, adventureToken)
    logger.info(s"$pictureRequest")
    handlePictureResponse(qualifierClient(buildRoutifierRequest(type_picture_url, adventurer, pictureUrl, adventureToken)), adventureToken, adventurer, pictureUrl)
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
