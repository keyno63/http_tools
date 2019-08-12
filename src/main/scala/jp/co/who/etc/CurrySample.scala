package jp.co.who.etc

object CurrySample extends App {

  def add(a: Int)(b: Int) = {
    val hoge = add1(a)
    addChain(hoge, b)
  }
  def add1(a: Int) = a + 1
  def addChain(a: Int, b: Int) = a + b + 2
  println(add(1)(2))

}
