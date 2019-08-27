package jp.co.who.httpclient.skinny

import skinny.http.{HTTP, Request, Response}

class CustomSkinny {

  def get(url: String, query: Map[String, String]): Response = {
    val request = Request(url)
    query.foreach{ kv
    => val (k,v) = kv
      request.queryParams(k -> v)
    }
    HTTP.get(request)
  }

  def post(url: String, query: Map[String, Any]): Response  = {

    val request = Request(url)
    val b = query.map(item => s"${item._1}=${item._2}").mkString("&")
    val response = HTTP.post(request.body(b.getBytes()))
    response
  }

  def sample(query: Map[String, String]) {
    val request = Request("http://example.com")

    query.foreach { item =>
      val (k, v) = item
      request.queryParams(k -> v)
    }
  }
}

object CustomSkinny {

  @Override
  def main(args: Array[String]): Unit = {

    // read config
    val p = new java.util.Properties()
    p.load(new java.io.FileInputStream("src/resources/http/conf.properties"))
    val url1 = p.getProperty("url")
    val rType = if (args.isEmpty) "" else args.head
    request(url1, rType)
  }

  private def request(url: String, rType: String = ""): Unit = {
    var query = Map(
      "param1" -> "fuga",
      "param2" -> "fuga1"
    )

    rType match {
      case "post" => post(url, query)
      case _ => get(url, query)
    }
  }

  private def get(
                   url: String,
                   query: Map[String, String] = Map.empty[String, String]
                 ): Unit = {

    val cs = new CustomSkinny
    val res = cs.get(url, query)
    println("status=[%s], body=[%s], headers=[%s]."
      .format(res.status, res.textBody, extraHeaders(res.headers)))
  }

  private def post(
                    url: String,
                    query: Map[String, String] = Map.empty[String, String]
                  ): Unit = {
    val cs = new CustomSkinny
    val res = cs.post(url, query)
    println("status=[%s], body=[%s], headers=[%s]."
      .format(res.status, res.textBody, extraHeaders(res.headers)))

  }

  private def perf(url: String): Unit = {
    val request = Request(url)
    var map = Map(
      "param1" -> "fuga",
      "param2" -> "fuga1"
    )
    val b = map.map(item => s"${item._1}=${item._2}").mkString("&")
    (0 to 100).foreach{ i =>
      val response = HTTP.post(request.body(b.getBytes()))
      Thread.sleep(500)
      println(i, response.status, response.textBody)
    }

  }

  private def extraHeaders(headers: Map[String, String]): String = {
    headers.map { kv =>
      val (k, v) = kv
      s"${k}: ${v}"
    }.mkString(", ")
  }

}
