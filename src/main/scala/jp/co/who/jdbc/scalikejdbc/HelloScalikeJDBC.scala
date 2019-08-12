package jp.co.who.jdbc.scalikejdbc

import scalikejdbc._

object HelloScalikeJDBC {

  def main(args: Array[String]): Unit = {

    val p = new java.util.Properties()
    p.load(new java.io.FileInputStream("src/resources/sql/conf.properties"))
    val db = p.getProperty("db")
    val host = p.getProperty("host")
    val port = p.getProperty("port")
    val dbname = p.getProperty("dbname")
    val user = p.getProperty("user")
    val password = p.getProperty("pass")

    // データベースに接続
    val url = s"jdbc:${db}://${host}:${port}/${dbname}"
    ConnectionPool.singleton(url, user, password)

    insertInitData()
    // クエリの実行と結果表示
    val names = DB readOnly {implicit session =>
      sql"select name from company".map(_.string("name")).list.apply()
    }
    names.foreach(println)
  }

  def insertInitData(): Unit = {
    var id = DB readOnly {implicit session =>
      sql"select max(id) as max from company"
        .map(_.int("max"))
        .single()
        .apply()
    }
    implicit val session = AutoSession
    Seq("Alice", "Bob", "Chris") foreach { name =>
      id = id.map(_ + 1)
      sql"insert into company (id, name, age) values (${id.get}, ${name}, ${id.get})".update.apply()
    }
  }

}
