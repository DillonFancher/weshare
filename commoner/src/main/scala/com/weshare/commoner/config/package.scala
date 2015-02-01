package com.weshare.commoner

import com.typesafe.config.ConfigFactory

package object config {

  def config  = ConfigFactory.load.withFallback(ConfigFactory.defaultReference)

}
