import java.nio.charset.Charset
import java.nio.file._
import java.nio.file.attribute.BasicFileAttributes

import play.api.libs.json.{JsValue, Json}
import se.ce.dto.LedarmotPerson
import se.ce.file.TraversePath
import se.ce.adapter.LedarmotPersonAdapter


val projectHome = new TraversePath(Paths.get( """/Users/orjan/Documents/riksdagen/person.json/"""))


projectHome foreach {
  case (file: Path, attr: BasicFileAttributes) => {
    if (attr.isRegularFile) {
      val content = new String(Files.readAllBytes(file), Charset.forName("UTF-8"))
      println(content.length)
      val json = Json.parse(content)
      val p:LedarmotPerson = LedarmotPersonAdapter.translate(json)
      println(p.intressent_id)
      println(p.tilltalsnamn)
      println(p.parti)
    }
  }
}