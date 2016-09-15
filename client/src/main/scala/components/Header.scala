package components

import css.GlobalStyle
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._

import scalacss.ScalaCssReact._

object Header {
  @inline private def Style = GlobalStyle

  case class Props(name:String, isReddit: Boolean)
  val component = ReactComponentB[Props]("Header")
    .render_P { P =>
      <.div(Style.row,
        <.div(Style.col(12),
          <.div(Style.siteSpecific(P.isReddit),P.name)
        ))
    }.build

  def apply(props: Props) = component(props)
}

