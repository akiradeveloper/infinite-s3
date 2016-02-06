name := "akashic-storage"

version := "0.1.0"

scalaVersion := "2.11.6"

test in assembly := {}

// Each test suite can be run in parallel
parallelExecution in Test := false
    
resolvers += Resolver.sonatypeRepo("snapshots")

val akkaV = "2.0.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-stream-experimental_2.11" % akkaV,
  "com.typesafe.akka" % "akka-http-core-experimental_2.11" % akkaV,
  "com.typesafe.akka" % "akka-http-experimental_2.11" % akkaV,
  "com.typesafe.akka" %% "akka-http-xml-experimental" % akkaV,
  "org.scala-lang.modules" %% "scala-pickling" % "0.10.1",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.4",
  "com.typesafe" % "config" % "1.3.0",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "commons-io" % "commons-io" % "2.4",
  "commons-codec" % "commons-codec" % "1.10",
  "commons-daemon" % "commons-daemon" % "1.0.15",
  // "org.apache.httpcomponents" % "httpclient" % "4.5",
  // "org.apache.httpcomponents" % "httpmime" % "4.5.1",
  "org.apache.tika" % "tika-core" % "1.10",
  "com.google.guava" % "guava" % "18.0",
  "com.amazonaws" % "aws-java-sdk" % "1.10.49",
  "com.typesafe.slick" %% "slick" % "3.0.2",
  "org.xerial" % "sqlite-jdbc" % "3.7.2",
  "org.scalaj" %% "scalaj-http" % "2.2.0"
  // "com.github.scopt" %% "scopt" % "3.3.0",
  // "ch.qos.logback" % "logback-classic" % "1.1.2"
)
