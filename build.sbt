import Dependencies._

ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github"
ThisBuild / organizationName := ""

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)

lazy val circeVersion = "0.13.0"

lazy val eventModelDeps = Seq(
  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.typelevel" %% "cats-effect" % "2.1.3",
  "org.typelevel" %% "cats-mtl-core" % "0.7.0",
  "org.typelevel" %% "simulacrum" % "1.0.0",
  "io.estatico" %% "newtype" % "0.4.4",
  scalaTest % Test
)

lazy val eventModel = (project in file("."))
  .settings(
    name := "mtl-example",
    libraryDependencies ++= eventModelDeps,
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-feature",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-Ymacro-annotations",
      "-Xfatal-warnings"
    ),
    scalacOptions in (Compile, console) ~= {
      _.filterNot(
        Set("-Ywarn-unused-import", "-Ywarn-unused:imports", "-Xfatal-warnings")
      )
    },
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
