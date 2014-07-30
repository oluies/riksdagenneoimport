name := "neo4j"

version := "1.0"

scalaVersion := "2.11.0"

scalacOptions  ++= Seq("-unchecked", "-deprecation")

resolvers ++= Seq(
  "anormcypher" at "http://repo.anormcypher.org/",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "spray" at "http://repo.spray.io/",
  "neo4j-rel" at "http://m2.neo4j.org/content/repositories/releases/",
  "neo4j-snap" at "http://m2.neo4j.org/snapshots",
  "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "anormcypher" at "http://repo.anormcypher.org/"
)

libraryDependencies ++= Seq(
  "org.anormcypher" %% "anormcypher" % "0.5.1",
  "com.typesafe.play" %% "play-json" % "2.3.1"
  //"org.neo4j" % "neo4j-kernel" % "2.1.2"
  //"org.neo4j" % "neo4j-shell" % "2.1.2"
  //"org.neo4j" % "neo4j-rest-graphdb" % "1.9"
  //"org.neo4j" % "neo4j-cypher" % "2.1.2"
)
