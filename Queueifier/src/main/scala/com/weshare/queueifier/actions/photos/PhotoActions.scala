package com.weshare.queueifier.actions.photos

import java.nio.ByteBuffer

import com.twitter.finatra.{Controller, ResponseBuilder, Request}
import com.twitter.util.Future
import com.weshare.commoner.storeer.{LocalDiskStoreer, S3BucketKey, LocalDiskStorerBucketKey, BucketKey}
import com.weshare.queueifier.controllers._

object PhotoActions extends Controller {

  def create(implicit request: Request): Future[ResponseBuilder] = {
    Future {
      val storer = new LocalDiskStoreer
      val picData = request.multiParams.get("picture").fold(Array.empty[Byte])(_.data)
      val buffer = ByteBuffer.wrap(picData)

      storer.put(buffer)
        .map(bucketKeyMap)
        .map(render.json)
        .getOrElse(weshareNotFound)
    }
  }

  def read(implicit request: Request) = {

  }

  def update(implicit request: Request) = {

  }

  def delete(implicit request: Request) = {

  }

  // Helpers
  def bucketKeyMap(bucketKey: BucketKey)(implicit request: Request): Map[String, String] = {

    val storageTypeMap = bucketKey match {
      case LocalDiskStorerBucketKey(b, k) => Map(
        "type" -> "local",
        "url" -> (request.host.getOrElse("http://localhost:7070") + s"/pictures/$b/$k")
      )

      case S3BucketKey(b, k) => Map(
        "type" -> "s3",
        "url" -> s"https://s3.amazonaws.com/$b/$k"
      )
    }
    Map(
      "bucket" -> bucketKey.bucket,
      "key" -> bucketKey.key
    ) ++ storageTypeMap
  }
}
