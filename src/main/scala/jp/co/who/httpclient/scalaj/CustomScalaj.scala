package jp.co.who.httpclient.scalaj

import scalaj.http.Http

object CustomScalaj {

  val connectTimeoutMillis = 5000
  val readTimeoutMillis = 5000

  @Override
  def main(args: Array[String]): Unit = {
    //read config
    val p = new java.util.Properties()
    p.load(new java.io.FileInputStream("src/resources/http/conf.properties"))
    val url1 = p.getProperty("url")
    get(url1)
  }

  private def get(url: String): Unit = {
    var query = Map(
      "param1" -> "fuga",
      "param2" -> "fuga1"
    )

    val queryParam = query.map(item => s"${item._1}=${item._2}").mkString("&")
    var request = Http(s"${url}?${queryParam}")
      .timeout(connTimeoutMs = connectTimeoutMillis, readTimeoutMs = readTimeoutMillis)

    request = request.method("GET")
    val ret = request.execute()
    println(ret.code, ret.body)
  }

}
