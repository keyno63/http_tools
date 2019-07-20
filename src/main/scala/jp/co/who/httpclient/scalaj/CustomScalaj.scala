package jp.co.who.httpclient.scalaj

import scalaj.http.{Http, HttpResponse}

object CustomScalaj {

  val connectTimeoutMillis = 5000
  val readTimeoutMillis = 5000
  //val rType: String

  @Override
  def main(args: Array[String]): Unit = {
    //read config
    val p = new java.util.Properties()
    p.load(new java.io.FileInputStream("src/resources/http/conf.properties"))

    val url = p.getProperty("url")
    val query = Map(
      "param1" -> "fuga",
      "param2" -> "fuga1"
    )

    val response = request(url, query, if (args.isEmpty) "" else args.head)
    println(response.code, response.body)
  }

  private def request(url: String, query: Map[String, String], rType: String): HttpResponse[String] = {
    rType match {
      case "post" =>
        post(url, query)
      case _ =>
        // default get.
        get(url, query)
    }
  }

  private def get(url: String, query: Map[String, String]): HttpResponse[String] = {

    val queryParam = createQueryParam(query, UrlEncode)
    var request = Http(s"${url}?${queryParam}")
      .timeout(connTimeoutMs = connectTimeoutMillis, readTimeoutMs = readTimeoutMillis)

    request = request.method("GET")
    request.execute()
  }

  private def post(url: String , query: Map[String, String]): HttpResponse[String] = {
    val queryParam = createQueryParam(query, UrlEncode)
    var request = Http(url)
      .timeout(connTimeoutMs = connectTimeoutMillis, readTimeoutMs = readTimeoutMillis)

    if (queryParam != "") {
      request = request.postData(queryParam)
    }

    request = request.method("POST")
    request.execute()
  }

  private def createQueryParam(query: Map[String, String], qType: QueryType): String = {
    qType match {
      case UrlEncode =>
        query.map{ kv =>
          val (k,v) = kv
          s"${k}=${v}"
        }.mkString("&")
      case Json => "" // not implemented
      case Xml => "" // not implemented
    }
  }

  sealed trait QueryType
  case object UrlEncode extends QueryType
  case object Json extends QueryType
  case object Xml extends QueryType
}
