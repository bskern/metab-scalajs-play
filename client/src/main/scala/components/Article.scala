package components

import css.GlobalStyle
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._

import scalacss.ScalaCssReact._
import scalacss.Defaults._

object Article {
  @inline private def Style = GlobalStyle
  case class Props(title:String, url:String, isReddit:Boolean)
  val component = ReactComponentB[Props]("Article")
    .render_P { P =>
      <.div(Style.row,
        <.div(Style.col(12),
          <.div(Style.content,
            <.a(Style.title,
              ^.target := "_blank",
              ^.href := P.url,
              P.title))))
    }.build

  def apply(props: Props,key:Int) = component.withKey(key)(props)
}

