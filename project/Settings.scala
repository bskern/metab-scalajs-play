import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object Settings {

  val name = "metaab-scalajs-play"

  val version = "0.3"

  /** Options for the scala compiler */
  val scalacOptions = Seq(
    "-Xlint",
    "-unchecked",
    "-deprecation",
    "-feature"
  )

  object versions {
    val scala = "2.11.8"
    val react = "15.3.2"
    val scalajsReact = "0.11.3"
    val scalaCss = "0.5.1"
    val jquery = "1.11.1"
    val bootstrap = "3.3.6"
    val upickle = "0.4.3"
    val autowire = "0.2.6"
  }

  val sharedDependencies = Def.setting(Seq(
    "com.lihaoyi" %%% "upickle" % versions.upickle,
    "com.lihaoyi" %%% "autowire" % versions.autowire
  ))

  val jvmDependencies = Def.setting(Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.0.0",
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test"
  ))

  //Dependencies only used by the JS project
  val scalajsDependencies = Def.setting(Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % versions.scalajsReact,
    "com.github.japgolly.scalajs-react" %%% "extra" % versions.scalajsReact,
    "com.github.japgolly.scalacss" %%% "core" % versions.scalaCss,
    "com.github.japgolly.scalacss" %%% "ext-react" % versions.scalaCss
  ))

  //Dependencies for external JS libs that are bundled into single .js according to dependency order
  val jsDependencies = Def.setting(Seq(
    "org.webjars.bower" % "react" % versions.react / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
    "org.webjars.bower" % "react" % versions.react / "react-dom.js" minified "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM",
    "org.webjars.bower" % "react" % versions.react / "react-dom-server.js" minified "react-dom-server.min.js" dependsOn "react-dom.js" commonJSName "ReactDOMServer",
    "org.webjars" % "jquery" % versions.jquery / "jquery.js" minified "jquery.min.js",
    "org.webjars" % "bootstrap" % versions.bootstrap / "bootstrap.js" minified "bootstrap.min.js" dependsOn "jquery.js"
  ))
}