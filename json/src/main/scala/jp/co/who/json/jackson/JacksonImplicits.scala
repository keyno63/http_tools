package jp.co.who.json.jackson

import java.lang.reflect.{ParameterizedType, Type}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

object JacksonImplicits {
  implicit class JsonString(json: String) {
     def asJsonStringOf[T](implicit m: Manifest[T], om: ObjectMapper): T =
       om.readValue(json, typeRef[T])
  }
  implicit class JsonSerializable(any: AnyRef)
                                   (implicit om: ObjectMapper) {
    def toJsonString: String = om.writeValueAsString(any)
  }

  private def typeRef[T](implicit m: Manifest[T]) =
    new TypeReference[T] {
      override def getType: Type = typeFromClassManifest(m)
    }

  def typeFromClassManifest(m: Manifest[_]): Type = {
    if (m.typeArguments.isEmpty) m.runtimeClass
    else new ParameterizedType {
      def getRawType: Type = m.runtimeClass
      def getActualTypeArguments: Array[Type] =
        m.typeArguments.map(typeFromClassManifest).toArray
      def getOwnerType: Type = null
    }
  }
}
