package com.weshare.queueifier.controllers

import com.twitter.finatra.Controller
import com.weshare.queueifier.actions.photos.PhotoActions
import com.weshare.queueifier.{controllers, ControllerAuthenticationService}

class PhotosController extends Controller with ControllerAuthenticationService {

  put("/photos") { implicit request =>
    controllers.weshareNotFound.toFuture
  }

  delete("/photos") { request =>
    controllers.weshareNotFound.toFuture
  }

  post("/photos"){ implicit request =>
    withTokenAuthentication(request){
        PhotoActions.create
    }
  }
}
