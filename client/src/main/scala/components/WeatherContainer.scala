package components

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import org.scalajs.dom.ext.Ajax
import shared.Weather

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class State(weather: Weather)

class Backend($: BackendScope[Unit, State]) {
  def render(s: State) = WeatherHeader(WeatherHeader.Props(s.weather.currentTemp, s.weather.desc, s.weather.high, s.weather.low))
}

object WeatherContainer {

  import upickle.default._


  val app = ReactComponentB[Unit]("weatherApp")
    .initialState(State(weather = Weather("000", "empty", "44", "55")))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {
      val url = "/weather"

      Ajax.get(url).onSuccess {
        case xhr => {
          scope.setState(State(read[Weather](xhr.responseText))).runNow()
        }
      }
    }).build

  def apply() = app()
}