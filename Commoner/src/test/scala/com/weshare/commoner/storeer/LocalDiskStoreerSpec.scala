package com.weshare.commoner.storeer

import java.io.{File, InputStream, ByteArrayOutputStream}
import java.nio.ByteBuffer
import java.nio.file.{Paths, Files}
import org.apache.commons.io.FileUtils
import org.specs2.execute.Failure
import org.specs2.mutable._
import org.specs2.specification.Scope

class LocalDiskStoreerSpec extends Specification {

  "The LocalStorerImpl" should {

    "Be able to write out a file to disk" in new scope {
      storer.put(byteBuffer) match {
        case None => Failure("Failed to write data")
        case Some(LocalDiskStorerBucketKey(bucket, key)) => {
          val path = Paths.get(bucket + "/" + key)
          val readBytes = Files.readAllBytes(path)
          readBytes must_== byteBuffer.array
        }
      }
    }

    "Be able to read data based on a bucket and key" in new scope {
      val Some(LocalDiskStorerBucketKey(bucket, path)) = storer.put(byteBuffer)
      storer.get(bucket, path) match {
        case None => Failure("Couldn't read the data")
        case Some(f) => f must_== byteBuffer.array
      }
    }
  }

  class scope extends Scope with After {
    val storer = new LocalDiskStoreer
    val file = this.getClass.getResourceAsStream("/giphy.gif")
    val baos = new ByteArrayOutputStream
    val buffer = new Array[Byte](1024)
    val byteArrStream = inputStream2ByteArrayOutputStream(file, buffer, baos).toByteArray
    val byteBuffer = ByteBuffer.wrap(byteArrStream)

    def after = {
      val file = new File(storer.dataDir)
      t

      ry {
        FileUtils.cleanDirectory(file)
      }
    }

    def inputStream2ByteArrayOutputStream(f: InputStream, buffer: Array[Byte], baos: ByteArrayOutputStream): ByteArrayOutputStream = {
      lazy val answer = {
        baos.flush()
        baos
      }

      Option(f).fold(answer) { inputStream =>
        val read = inputStream.read(buffer, 0, buffer.length)
        if (-1 != read) {
          baos.write(buffer, 0, read)
          inputStream2ByteArrayOutputStream(f, buffer, baos)
        } else answer
      }
    }

  }
}
