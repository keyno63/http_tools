package jp.co.who.httpclient.akka

import java.io.FileInputStream

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model.{HttpEntity, HttpRequest, HttpResponse, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.util.Timeout
import jp.co.who.httpclient.config.Config

import scala.concurrent.Await
import scala.concurrent.duration._

object CustomAkka {

  implicit val timeout = Timeout(5 seconds)
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  @Override
  def main(args: Array[String]): Unit = {
    val p = new java.util.Properties()
    p.load(new FileInputStream(Config.configFilePath))

    val url = p.getProperty("url")
    val query = Map(
      "param1" -> "fuga",
      "param2" -> "fuga1"
    )


    val response = get(url, query)
    println(response.status.intValue(),
      Await.result(Unmarshal(response.entity).to[String], timeout.duration))
  }

  private def request(
                       url: String,
                       query: Map[String, String],
                       rType: String = ""
                     ): HttpResponse = {
    rType match {
      case "post" => post(url, query)
      case _ => get (url, query)
    }

  }

  def get(url: String, query: Map[String, String]): HttpResponse = {
    val rawHeaders = List.empty

    val httpEntity = HttpEntity.Empty

    val req = HttpRequest(GET, uri = Uri(url), entity = httpEntity)
      .withHeaders(
        rawHeaders: _*,
      )

    val responseFuture = Http().singleRequest(req)
    Await.result(responseFuture, timeout.duration)
  }

  def post(url: String, query: Map[String, String]) = {
    // header
    val rawHeaders = List.empty

    val queryParam = query.map {
      case (k, v) => s"${k}=${v}"
    }.mkString("&")
    val httpEntity = queryParam match {
      case "" => HttpEntity.Empty
      case _ => HttpEntity(queryParam)
    }

    val request = HttpRequest(POST, uri = Uri(url), entity = httpEntity)
      .withHeaders(
        rawHeaders: _*,
      )

    val responseFuture = Http().singleRequest(request)
    Await.result(responseFuture, timeout.duration)
  }



}
