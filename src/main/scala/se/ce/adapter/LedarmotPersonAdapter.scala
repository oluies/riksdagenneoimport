package se.ce.adapter

import play.api.libs.json.JsValue
import se.ce.dto.{IntressentID, HangarGuid, LedarmotPerson}

/**
 * Created by orjan on 14-07-19(29).
 */
object LedarmotPersonAdapter {

  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  implicit val LedarmotPersonReads:Reads[LedarmotPerson] = (
      (__ \ "person" \ "hangar_guid").read[String].map { guid => HangarGuid(guid) } and
      (__ \ "person" \ "intressent_id").read[String].map { id => IntressentID(id.toLong) }  and
      (__ \ "person" \ "fodd_ar").read[Int] and
      (__ \ "person" \ "kon").read[String] and
      (__ \ "person" \ "efternamn").read[String] and
      (__ \ "person" \ "tilltalsnamn").read[String] and
      (__ \ "person" \ "sorteringsnamn").read[String] and
      (__ \ "person" \ "parti").readNullable[String] and
      (__ \ "person" \ "valkrets").readNullable[String] and
      (__ \ "person" \ "status").read[String] and
      (__ \ "person" \ "person_url_xml").read[String] and
      (__ \ "person" \ "bild_url_80").read[String] and
      (__ \ "person" \ "bild_url_192").read[String] and
      (__ \ "person" \ "bild_url_max").read[String]
      )(LedarmotPerson)

  def translate(json: JsValue): LedarmotPerson = {
    LedarmotPerson(
      HangarGuid((json \ "person" \ "hangar_guid").asOpt[String].getOrElse("")),
      IntressentID((json \ "person" \ "intressent_id").asOpt[String].getOrElse("-1").toLong),
      (json \ "person" \ "fodd_ar").asOpt[Int].getOrElse(0),
      (json \ "person" \ "kon").asOpt[String].getOrElse(""),
      (json \ "person" \ "efternamn").asOpt[String].getOrElse(""),
      (json \ "person" \ "tilltalsnamn").asOpt[String].getOrElse(""),
      (json \ "person" \ "sorteringsnamn").asOpt[String].getOrElse(""),
      (json \ "person" \ "parti").asOpt[String],
      (json \ "person" \ "valkrets").asOpt[String],
      (json \ "person" \ "status").asOpt[String].getOrElse(""),
      (json \ "person" \ "person_url_xml").asOpt[String].getOrElse(""),
      (json \ "person" \ "bild_url_80").asOpt[String].getOrElse(""),
      (json \ "person" \ "bild_url_192").asOpt[String].getOrElse(""),
      (json \ "person" \ "bild_url_max").asOpt[String].getOrElse("")
    )
  }

}
