package jp.co.who.json.jackson

import jp.co.who.json.jackson.JacksonImplicits._

object SampleParser {

  def main(args: Array[String]): Unit = {

    val bar = Person("Bar Smith")
    val foo = Person("Foo Smith", Set(bar))

    // JSON 文字列にシリアライズ
    val fooJson = foo.toJsonString
    println(fooJson)
    // 出力: {"name":"Foo Smith","children":[{"name":"Bar Smith","children":[]}]}

    // JSON 文字列からデシリアライズ
    val parsed = """{"name":"Hoge","children":[]}""".asJsonStringOf[Person]
    println(parsed)

  }

  case class Person(name: String, children: Set[Person] = Set())
}
