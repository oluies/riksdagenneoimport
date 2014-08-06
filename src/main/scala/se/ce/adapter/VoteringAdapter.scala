package se.ce.adapter

import java.util.Date

import se.ce.dto.Rost._
import se.ce.dto.VoteringTyp._
import se.ce.dto._

/**
 * Created by orjan on 14-07-29(31).
 */
object VoteringAdapter {

  import play.api.libs.json._
  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads._
  import play.api.data.validation.ValidationError


  implicit val voteringReads:Reads[Votering] = (
      (__ \ "rm").read[String] ~
      (__ \ "beteckning").read[String] ~
      (__ \ "hangar_id").read[String].map { guid => HangarGuid(guid) } ~
      (__ \ "votering_id").read[String].map { guid => VoteringID(guid) } ~
      (__ \ "punkt").read[String] ~
      (__ \ "intressent_id").read[String].map { id => IntressentID(id.toLong) } ~
      (__ \ "rost").read[String].map { rost => Rost.withName(rost) } ~
      (__ \ "avser").read[String] ~
      (__ \ "votering").read[String].map { votering => VoteringTyp.withName(votering) } ~
      (__ \ "banknummer").read[String].map( bänk => bänk.toInt) ~
      (__ \ "datum").read[Date]
    )(Votering.apply _)

  implicit val dokVoteringReads: Reads[DokVotering] =
    (JsPath \ "dokvotering" \ "votering").read[List[Votering]] map (DokVotering.apply _)
}



/*
 "Votering": [
      {
        "rm": "2013/14",
        "beteckning": "AU2",
        "hangar_id": "2875527",
        "votering_id": "0ED3F706-B84D-4551-8782-A5753A73B99B",
        "punkt": "2",
        "namn": "Susanne  Eberstein",
        "intressent_id": "0235974887200",
        "parti": "S",
        "valkrets": "Västernorrl~s län",
        "valkretsnummer": "26",
        "iort": null,
        "rost": "Ja",
        "avser": "sakfrågan",
        "votering": "huvud",
        "banknummer": "1",
        "fornamn": "Susanne",
        "efternamn": "Eberstein",
        "kon": "kvinna",
        "fodd": "1948",
        "datum": "2013-12-18"
      },
 */
