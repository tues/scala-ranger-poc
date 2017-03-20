// Tested only Scala 2.11 so far
scalaVersion in ThisBuild := "2.11.8"

lazy val core = project
  .in(file("core"))
  .settings(buildSettings: _*)
  .settings(macroBuildSettings: _*)
  .settings(moduleName := "ranger")

lazy val example = project
  .in(file("example"))
  .settings(moduleName := "example")
  .settings(buildSettings: _*)
  .dependsOn(exampleTypes)

lazy val exampleTypes = project
  .in(file("example-types"))
  .settings(moduleName := "example-types")
  .settings(buildSettings: _*)
  .settings(macroBuildSettings: _*)
  .settings(testBuildSettings: _*)
  .dependsOn(core)

run := (run in Compile in example).evaluated

lazy val buildSettings = Seq(
  scalacOptions ++= Seq("-deprecation", "-feature")
)

lazy val macroBuildSettings = Seq(
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)

lazy val testBuildSettings = Seq(
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
