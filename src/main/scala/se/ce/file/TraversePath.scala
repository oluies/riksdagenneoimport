package se.ce.file

import java.nio.file.{Files, FileVisitResult, SimpleFileVisitor, Path}
import java.nio.file.attribute.BasicFileAttributes

import scala.collection.Traversable

// Make it extend Traversable
class TraversePath(path: Path) extends Traversable[(Path, BasicFileAttributes)] {
  // Make foreach receive a function from Tuple2 to Unit
  def foreach[U](f: ((Path, BasicFileAttributes)) => U) {
    class Visitor extends SimpleFileVisitor[Path] {
      override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = try {
        // Pass a tuple to f
        f(file -> attrs)
        FileVisitResult.CONTINUE
      } catch {
        case e: Throwable => println("error " + e.getMessage);FileVisitResult.TERMINATE
      }
    }
    Files.walkFileTree(path, new Visitor)
  }
}