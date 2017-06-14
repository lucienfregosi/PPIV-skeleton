name := "sbt-scala-sample"

version := "1.0"

scalaVersion := "2.11.8"

val sparkVersion = "2.1.0"
resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "com.typesafe" % "config" % "1.2.1",
  "org.joda" % "joda-convert" % "1.8",
  "log4j" % "log4j" % "1.2.14",
  "com.github.nscala-time" % "nscala-time_2.11" % "2.16.0",
  "org.elasticsearch" % "elasticsearch-spark-20_2.11" % "5.4.0" % "compile",

"org.scalaz" %% "scalaz-core" % "7.0.6" withSources() withJavadoc(),
"org.rogach" %% "scallop" % "0.9.5" withSources() withJavadoc(),
"org.scala-lang" % "scalap" % "2.10.4" withSources() withJavadoc(),
"org.scala-lang" % "scala-compiler" % "2.10.4" withSources() withJavadoc(),
"org.specs2" %% "specs2-core" % "2.4.9-scalaz-7.0.6" % "test" withSources() withJavadoc(),
"org.specs2" %% "specs2-scalacheck" % "2.4.9-scalaz-7.0.6" % "test" withSources() withJavadoc(),
"com.novocode" % "junit-interface" % "0.8" % "test->default"
)

test in assembly := {}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) => MergeStrategy.discard
      case _ => MergeStrategy.discard
    }
  case "application.conf"                            => MergeStrategy.concat
  case _                                => MergeStrategy.first
}