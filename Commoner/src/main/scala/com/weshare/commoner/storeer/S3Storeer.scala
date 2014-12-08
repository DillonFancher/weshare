package com.weshare.commoner.storeer

import java.net.URL
import java.nio.ByteBuffer

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{ObjectMetadata, PutObjectRequest}
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream

import scala.util.{Success, Try}

class S3Storeer(bucket: String, key: String) extends Storeer {

  val Client = new AmazonS3Client()

  override def put(data: ByteBuffer): Option[URL] = {
    val inputStream = new ByteBufferBackedInputStream(data)
    val objMetadata= new ObjectMetadata()
    val putRequest = new PutObjectRequest(bucket, key, inputStream, objMetadata)
    putRequest.withInputStream(inputStream)
    val putObjectResult = Try {
      Client.putObject(putRequest)
    }

    putObjectResult match {
      case Success(result) =>

        val url = new URL()
        Some()
      case _ =>
        None
    }
  }

  override def get(url: URL): Unit = ???

}
