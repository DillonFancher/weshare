package com.weshare.routifier

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Charsets
import com.twitter.finagle.{Http, Service}
import com.twitter.util.{Await, Future}
import org.jboss.netty.handler.codec.http._
import org.slf4j.{Logger, LoggerFactory}

object Main extends RoutifierDataContainers {

  private val logger: Logger = LoggerFactory.getLogger(classOf[RoutifierDataContainers])

  def main(args: Array[String]) {
    val service = new Service[HttpRequest, HttpResponse] {
      def apply(request: HttpRequest): Future[HttpResponse] = {
        val requestData: QualifierRequestData = QualifierRequestData(parseRequest(request))
        logger.info(parseRequest(request).toString)
        logger.info(s"Got request of type: ${requestData.requestType} to user: ${requestData.userId}, in adventure: ${requestData.adventureToken}")

        Future.value(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK))

      }
    }

    val server = Http.serve(":8090", service)
    Await.ready(server)
  }
  val mapper = new ObjectMapper()

  def parseRequest(request: HttpRequest) = {
    mapper.readTree(request.getContent.toString(Charsets.UTF_8))
  }
}
