package components

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import org.scalajs.dom.ext.Ajax
import services.AjaxClient
import shared.{Api, Weather}
import upickle.default._
import upickle.Js
import autowire._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue


case class State(weather: Weather)

class Backend($: BackendScope[Unit, State]) {
  def render(s: State) = WeatherHeader(WeatherHeader.Props(s.weather.currentTemp, s.weather.desc, s.weather.high, s.weather.low))
}

object WeatherContainer {
  val app = ReactComponentB[Unit]("weatherApp")
    .initialState(State(weather = Weather("000", "empty", "44", "55")))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {
      AjaxClient[Api].getWeather().call().foreach { weather =>
        scope.setState(State(weather)).runNow()
      }
    }).build

  def apply() = app()
}