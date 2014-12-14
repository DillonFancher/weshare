package com.weshare.commoner.storeer

sealed abstract class BucketKey(bucket: String, key: String)

case class LocalDiskStorerBucketKey(bucket:String, key: String) extends BucketKey(bucket, key)

case class S3BucketKey(bucket: String, key: String) extends BucketKey(bucket, key)
