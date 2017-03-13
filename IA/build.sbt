
// General

organization := "org.example"

name := """IA"""

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

scalacOptions += "-deprecation"


// Code Formatting

scalariformSettings


// Testing

libraryDependencies +=  "org.scalatest" %% "scalatest" % "3.0.1"


// Publishing

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomExtra :=
  <url>http://org.example/hello-sbt</url>
    <licenses>
      <license>
        <name>CC0 1.0 Universal</name>
        <url>https://github.com/exampleorg/hello-sbt/blob/master/LICENSE</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:exampleorg/hello-sbt.git</url>
      <connection>scm:git:git@github.com:exampleorg/hello-sbt.git</connection>
    </scm>
    <developers>
      <developer>
        <id>mr-activator</id>
        <name>Mr. Activator</name>
        <url>http://org.example/mr-activator</url>
      </developer>
    </developers>

