import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object FlowershopBuild extends Build {
  val Organization = "com.example"
  val Name = "FileProcessing"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.10.2"
  val ScalatraVersion = "2.2.1"

  lazy val project = Project (
    "fileprocessing",
    file("."),
    settings = Defaults.defaultSettings ++ ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
      libraryDependencies ++= Seq(
        "org.json4s"   %% "json4s-native" % "3.2.4",
        "org.json4s"   %% "json4s-jackson" % "3.2.4",
        "org.scalatra" %% "scalatra-json" % "2.2.1",
        "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.12",
        "org.scalatra" %% "scalatra-swagger"  % ScalatraVersion,
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.8.v20121106" % "container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar")),
        "org.codehaus.jackson" % "jackson-jaxrs" % "1.9.13",
        "org.scalaforge"      % "scalax"          % "0.1",
        "com.wordnik" % "swagger-jaxrs_2.9.1" % "1.3.0",
        "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.2",
        "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.2"
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq.empty,  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}
