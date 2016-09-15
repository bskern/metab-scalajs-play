package components

import css.GlobalStyle
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import scalacss.ScalaCssReact._

object WeatherHeader {
  @inline private def Style = GlobalStyle

  case class Props(currentTemp:String, desc: String, high: String, low: String)
  val component = ReactComponentB[Props]("WeatherHeader")
    .render_P { P =>

      <.nav(Style.navbar,
        <.div(Style.container,
          <.div(Style.navbarHeader,
            <.span(Style.navbarBrand,s"Minneapolis ${P.currentTemp} - ${P.desc}")
          ),
          <.ul(Style.navbarList,
            <.li(<.a(s"High: ${P.high}")),
            <.li(<.a(s"Low: ${P.low}")))))
    }.build

  def apply(props: Props) = component(props)
}

