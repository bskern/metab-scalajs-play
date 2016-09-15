package css

import scalacss._
import scalacss.internal.mutable.GlobalRegistry
import scalacss.ScalaCssReact._
import scalacss.Defaults._
object AppCSS {

  def load = {
    GlobalRegistry.register(GlobalStyle)
    GlobalRegistry.onRegistration(_.addToDocument())
  }
}

