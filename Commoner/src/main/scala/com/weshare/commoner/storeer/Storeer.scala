package com.weshare.commoner.storeer

import java.net.URL
import java.nio.ByteBuffer

trait Storeer {

  abstract def put(data: ByteBuffer): Option[Any]

  abstract def get(url: URL): Option[Any]

}
