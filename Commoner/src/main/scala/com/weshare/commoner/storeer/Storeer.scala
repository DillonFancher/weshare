package com.weshare.commoner.storeer

import java.nio.ByteBuffer

trait Storeer {

  def put(data: ByteBuffer): Option[BucketKey]

  def get(bucket: String, key: String): Option[Any]

}
