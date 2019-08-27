package jp.co.who.httpclient.util

sealed trait QueryType
case object UrlEncode extends QueryType
case object Json extends QueryType
case object Xml extends QueryType