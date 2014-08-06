/**
 * Created by orjan on 14-07-18(29).
 */

import java.nio.charset.StandardCharsets
import java.nio.file.attribute.BasicFileAttributes

import play.api.libs.json._
import se.ce.dto.{DokVotering, Votering, LedarmotPerson}
import se.ce.file.TraversePath
import se.ce.adapter.LedarmotPersonAdapter

import scala.collection.JavaConverters._
import java.nio.file.{Files, Path, Paths}

import org.anormcypher._

object riksdagen extends App {


  val ledarmotFiles = new TraversePath(Paths.get( """/Users/orjan/Documents/riksdagen/person.json/"""))
  val voteringFiles = new TraversePath(Paths.get( """/Users/orjan/src/play/neo4j/data/votering-201314.json"""))

  Neo4jREST.setServer("localhost", 7474, "/db/data/")

  val rConstraint = Cypher("""CREATE CONSTRAINT ON (n:LedarmotPerson) ASSERT n.intressent_id IS UNIQUE;""").execute()
  println(s"rConstraint $rConstraint")


  /*ledarmotFiles foreach {
    case (file: Path, attr: BasicFileAttributes) => try {
      import LedarmotPersonAdapter.LedarmotPersonReads

      if (attr.isRegularFile) {
        val fileContent: String = new String(Files.readAllBytes(file), StandardCharsets.UTF_8)

        val json: JsValue = try {
          Json.parse(fileContent.replaceFirst("^\uFFFE", ""))
        } catch {
          // some files miss the ending }
          case e: Throwable => Json.parse(fileContent.replaceFirst("^\uFFFE", "") + "}")
        }

        json.validate[LedarmotPerson] match {
          case s: JsSuccess[LedarmotPerson] =>  println("LedarmotPerson: " + s.get.intressent_id)//; insertLedarmot(s.get)
          case e: JsError => println("Errors: " + JsError.toFlatJson(e).toString())
        }
      }
    } catch {
       case e:
         Throwable => println(s"$file error " + e.getMessage);
    }
  }
  */

  voteringFiles foreach {
    case (file: Path, attr: BasicFileAttributes) => try {
      if (attr.isRegularFile) {
        import se.ce.adapter.VoteringAdapter._

        val fileContent: String = new String(Files.readAllBytes(file),StandardCharsets.UTF_8)
        val json: JsValue = Json.parse(fileContent.replaceFirst("^\uFEFF", ""))

        val votes:JsResult[DokVotering] = json.validate[DokVotering]

        votes match {
          case s: JsSuccess[DokVotering] => println("DokVotering  : " + s.get.votering.size); assert(s.get.votering.size % 349 == 0)
          case e: JsError => println("Errors: " + JsError.toFlatJson(e).toString())
        }
      }
    } catch {
      case e:
        Throwable => println(s"$file error " + e.getMessage);
    }
  }




  def insertLedarmot (p: LedarmotPerson): Unit = {
      if (p.status.substring(0, 8) != "Tidigare") {
        val result = Cypher(
          """
                MERGE (p:LedarmotPerson {
                                          intressent_id:{id},
                                          tilltalsnamn:{name},
                                          efternamn:{ename},
                                          parti:{parti},
                                          fodd_ar:{fodd_ar},
                                          kon:{kon},
                                          hangar_guid:{hangar_guid},
                                          person_url_xml:{person_url_xml},
                                          status:{status},
                                          valkrets:{valkrets}
                                        })
                ON CREATE SET p.created = timestamp()
                ON MATCH SET p.lastupdated = timestamp()
                MERGE (parti:Parti {parti:{parti},namn:{parti}})
                MERGE (p)-[:MEMBER_OF]->(parti);
          """).on("id" -> p.intressent_id.id)
          .on("name" -> p.tilltalsnamn)
          .on("ename" -> p.efternamn)
          .on("parti" -> p.parti.getOrElse("??"))
          .on("fodd_ar" -> p.fodd_ar)
          .on("kon" -> p.kon)
          .on("hangar_guid" -> p.hangar_guid.guid).on("person_url_xml" -> p.person_url_xml)
          .on("status" -> p.status)
          .on("valkrets" -> p.valkrets.getOrElse(""))
          .execute()

        println(s"neo4j Result $result $p")
      } else {
        println(p.intressent_id + " " + p.status)
      }
  }
}

