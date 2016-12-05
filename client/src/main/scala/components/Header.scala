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
          <.div(Style.siteSpecific(P.isReddit),

            <.a(if (P.isReddit) ^.href:=s"http://www.reddit.com/r/${P.name}" else  if (P.name == "Ask")^.href := "http://news.ycombinator.com/ask" else ^.href := "http://news.ycombinator.com",
              ^.target := "_blank",
              Style.aNoStyle,
              P.name)
        )))
    }.build

  def apply(props: Props) = component(props)
}

