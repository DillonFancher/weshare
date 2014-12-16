package com.weshare.commoner.storeer

import java.nio.ByteBuffer

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{GetObjectRequest, ObjectMetadata, PutObjectRequest}
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream

import scala.util.{Success, Try}

class S3Storeer(bucket: String, key: String) extends Storeer {

  val Client = new AmazonS3Client()

  override def put(data: ByteBuffer): Option[S3BucketKey] = {
    val inputStream = new ByteBufferBackedInputStream(data)
    val objMetadata= new ObjectMetadata()
    val putRequest = new PutObjectRequest(bucket, key, inputStream, objMetadata)
    putRequest.withInputStream(inputStream)
    val putObjectResult = Try {
      Client.putObject(putRequest)
    }
    putObjectResult.toOption.map(a => S3BucketKey(bucket,key))
  }

  override def get(bucket: String, key: String): Option[Any] = {
    val objRequest = new GetObjectRequest(bucket, key)
    Try {
      Client.getObject(objRequest)
    } match {
      case Success(i) => Some(i)
      case _ => None
    }
  }
}
