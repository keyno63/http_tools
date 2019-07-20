package jp.co.who.httpclient.sttp

import java.io.FileInputStream

import com.softwaremill.sttp._
import scala.concurrent.duration._
import jp.co.who.httpclient.config.Config

object CustomSttp {

  implicit val backend = HttpURLConnectionBackend()

  @Override
  def main(array: Array[String]): Unit = {
    // read config.
    val p = new java.util.Properties()
    p.load(new FileInputStream(Config.configFilePath))
    val url = p.getProperty("url")
    get(url)

  }

  private def get(url: String) = {
    val request = sttp.method(Method.GET, uri"$url")
      .headers(Map.empty[String, String])
      .readTimeout(5.seconds)
    val response = request.send()
    println(response.code, response.body.right.get)
  }

}
