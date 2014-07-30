package se.ce.dto


/**
 * Created by orjan on 14-07-18(29).
 */


case class LedarmotPerson(hangar_guid: HangarGuid,
                          intressent_id: IntressentID,
                          fodd_ar: Int,
                          kon: String,
                          efternamn: String,
                          tilltalsnamn: String,
                          sorteringsnamn: String,
                          parti: Option[String],
                          valkrets: Option[String],
                          status: String,
                          person_url_xml: String,
                          bild_url_80: String,
                          bild_url_192: String,
                          bild_url_max: String)


