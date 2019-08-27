package jp.co.who.httpclient.sttp

import java.io.FileInputStream

import com.softwaremill.sttp._
import jp.co.who.httpclient.config.Config

import scala.concurrent.duration._

object CustomSttp {

  implicit val backend = HttpURLConnectionBackend()
  val timeout = 5.seconds

  @Override
  def main(args: Array[String]): Unit = {
    // read config.
    val p = new java.util.Properties()
    p.load(new FileInputStream(Config.configFilePath))

    val url = p.getProperty("url")
    val query = Map(
      "param1" -> "fuga",
      "param2" -> "fuga1"
    )

    val response = request(url, query, if (args.isEmpty) "" else args.head)
    println(response.code, response.body.right.get)

  }

  private def request(
                       url: String
                       , query: Map[String, String] = Map.empty[String, String]
                       , rType: String = ""
                     ) = {
    rType match {
      case "post" =>
        post(url, query)
      case _ =>
        // default get.
        get(url, query)
    }
  }

  private def get(
                   url: String
                   , query: Map[String, String] = Map.empty[String, String]
                 ) = {
    val queryParam = query.map {
      case (k, v) => s"${k}=${v}"
    }.mkString("&")
    val uri = if (queryParam.isEmpty) uri"${url}" else uri"${url}?${queryParam}"
    val request = sttp.method(Method.GET, uri)
      .headers(Map.empty[String, String])
      .readTimeout(timeout)

    request.send()
  }

  private def post(
                    url:String
                    , query: Map[String, String] = Map.empty[String, String]
                  ) = {
    val queryParam = query.map {
      case (k, v) => s"${k}=${v}"
    }.mkString("&")
    var request = sttp.method(Method.POST, uri"$url")
      .headers(Map("Content-Type" -> "application/x-www-form-urlencoded"))
      .readTimeout(timeout)

    if (queryParam != "") {
      request = request.body(queryParam)
    }

    request.send()
  }

}
