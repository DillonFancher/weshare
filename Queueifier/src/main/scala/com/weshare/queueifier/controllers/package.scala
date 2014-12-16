package com.weshare.queueifier

import com.twitter.finatra.{ResponseBuilder, Controller}

package object controllers extends Controller {

  def weshareNotFound: ResponseBuilder = {
    render.status(404).plain("not found yo")
  }

}
