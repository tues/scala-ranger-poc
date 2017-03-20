// Tested only Scala 2.11 so far
scalaVersion in ThisBuild := "2.11.8"

lazy val core = project
  .in(file("core"))
  .settings(CommonSettings.build: _*)
  .settings(CommonSettings.macroBuild: _*)
  .settings(moduleName := "ranger")

lazy val example = project
  .in(file("example"))
  .settings(moduleName := "example")
  .settings(CommonSettings.build: _*)
  .dependsOn(exampleTypes)

lazy val exampleTypes = project
  .in(file("example-types"))
  .settings(moduleName := "example-types")
  .settings(CommonSettings.build: _*)
  .settings(CommonSettings.macroBuild: _*)
  .dependsOn(core)

run := (run in Compile in example).evaluated
