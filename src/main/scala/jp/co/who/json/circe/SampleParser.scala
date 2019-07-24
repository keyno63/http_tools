package jp.co.who.json.circe

import io.circe.Json
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object SampleParser {

  val jsonString = """{
                    |   "foo" : "foo value",
                    |   "bar" : {
                    |     "bar_child" : "bar child value"
                    |   },
                    |   "array":[
                    |     { "content" : 1 },
                    |     { "content" : 2 },
                    |     { "content" : 3 }
                    |   ]
                    | }""".stripMargin

  def main(args: Array[String]): Unit = {
    val doc: Json = parse(jsonString).getOrElse(Json.Null)
    println(doc)

    sample
  }

  def sample(): Unit = {
    val list = List(1, 2, 3)
    val json = list.asJson
    println(json)
    val json1 = List("an", "do", "tr").asJson
    val a = json1.as[List[String]]
    println(a)

    val sample = Sample(Id(1), Name("sample_name"))
    val encodeData: String = sample.asJson.spaces2
    val decodedData: Either[io.circe.Error, Sample] = decode[Sample](encodeData)
    val json2 = sample.asJson
    println(json2)

    val sampleL = SampleL(list, Name("sample_name_l"))
    val json3 = sampleL.asJson
    println(json3)
  }

}

case class SampleL(lists: List[Int], name: Name)
case class Sample(id: Id, name: Name)
case class Id(value: Long) extends AnyVal
object Id {
  import io.circe._
  implicit val encode: Encoder[Id] = Encoder[Long].contramap(_.value)
  implicit val decode: Decoder[Id] = Decoder[Long].map(Id(_))
}
case class Name(value: String) extends AnyVal
object Name {
  import io.circe._
  implicit val encode: Encoder[Name] = Encoder[String].contramap(_.value)
  implicit val decode: Decoder[Name] = Decoder[String].map(Name(_))
}