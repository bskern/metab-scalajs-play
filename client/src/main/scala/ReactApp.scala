import components.SiteContainer
import css.AppCSS
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import japgolly.scalajs.react._

@JSExport
object ReactApp extends JSApp {
   def main(): Unit = {
    AppCSS.load
    SiteContainer() render dom.document.getElementById("root")

  }

}

