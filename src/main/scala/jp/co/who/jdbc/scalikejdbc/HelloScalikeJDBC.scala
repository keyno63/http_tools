package jp.co.who.jdbc.scalikejdbc

import scalikejdbc._

object HelloScalikeJDBC {

  def main(args: Array[String]): Unit = {
    // データベースに接続
    val url = "jdbc:postgresql://akane:5432/postgres"
    val user = "postgres"
    val password = ""
    ConnectionPool.singleton(url, user, password)

    // クエリの実行と結果表示
    val names = DB readOnly {implicit session =>
      sql"select name from company".map(_.string("name")).list.apply()
    }
    names.foreach(println)
  }

}
