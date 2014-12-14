package com.weshare.commoner.storeer

trait BucketKey {
  def bucket: String
  def key: String
}

case class LocalDiskStorerBucketKey(bucket:String, key: String) extends BucketKey

case class S3BucketKey(bucket: String, key: String) extends BucketKey
