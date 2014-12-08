package com.weshare.queueifier

import com.twitter.finagle.http.Request
import com.twitter.finatra.{ResponseBuilder, Controller}
import com.twitter.util.Future

class Unauthorized extends Exception

trait ControllerAuthenticationService extends Controller {

  val UserTokenParam = "userParam"
  val SessionTokenParam = "sessionToken"

  def withTokenAuthentication(request: Request)(callback: => Future[ResponseBuilder]): Future[ResponseBuilder] = {
    val userToken = request.getParam(UserTokenParam)
    val sessionToken = request.getParam(SessionTokenParam)
    if (isAuthenticated(userToken, sessionToken)) {
      callback
    } else {
      throw new Unauthorized
    }
  }

  def isAuthenticated(userToken: String, sessionToken: String): Boolean = {
    true
  }
}
