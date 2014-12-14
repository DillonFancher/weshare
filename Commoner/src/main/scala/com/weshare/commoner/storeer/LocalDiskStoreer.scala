package com.weshare.commoner.storeer

import java.io.{IOException, FileOutputStream, File}
import java.nio.ByteBuffer
import java.nio.file.{Path, Paths, Files}

import scala.util.{Success, Try}

/**
 * Class that writes out to the
 */
class LocalDiskStoreer extends Storeer {

  val dir = "/tmp/weshare/commoner/storeer/local_disk_storeer"
  val DirHandle = new File(dir)

  // if the directory does not exist, create it
  // blow up if we dont have access to create directory
  if (!DirHandle.exists()) assert( DirHandle.mkdir() )

  override def put(data: ByteBuffer): Option[LocalDiskStorerBucketKey] = {
    val filename: String = listFiles.size.toString
    writeSmallBinaryFile(data.array, filename).toOption.map( a => LocalDiskStorerBucketKey(dir, filename))
  }

  /**
   *
   * Bucket being folder, key being the file name
   *
   * @param bucket
   * @param key
   * @return
   *
   */
  override def get(bucket: String, key: String): Option[Array[Byte]] = readSmallBinaryFile(joinFilePath(bucket, key))

  def dataDir = dir

  private def listFiles: Seq[String] = Option(DirHandle.listFiles()).fold(Seq.empty[String]){ fList => fList.map(_.getAbsolutePath)}

  private def readSmallBinaryFile(aFileName: String): Try[Array[Byte]] = {
    val path = Paths.get(aFileName)
    Try {
      Files.readAllBytes(path)
    }
  }

  private def writeSmallBinaryFile(aBytes: Array[Byte], aFileName: String): Try[Path] = {
    val path = Paths.get(mkPathForFile(aFileName))
    Try {
      Files.write(path, aBytes)
    }
  }

  private def joinFilePath(path: String, filename: String): String = {
    val split: List[String] = path.split("/").toList :+ filename
    split.foldLeft(""){ (acc, item) =>
      if ("" == item) acc else  acc + "/" + item
    }
  }

  def mkPathForFile(file: String): String = joinFilePath(dir, file)

  implicit def try2Option[T](t: Try[T]): Option[T] = t.toOption
}
