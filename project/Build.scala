import sbt._
import Keys._

object CommonSettings {

  lazy val build = Seq(
    //scalaVersion := "2.11.7",
    scalacOptions ++= Seq("-deprecation", "-feature")
  )

  lazy val macroBuild = Seq(
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  )

}
