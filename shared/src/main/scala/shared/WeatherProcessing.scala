package shared

object WeatherProcessing {
  case class WeatherResponse(query: WeatherMetaObject)

  case class WeatherMetaObject(count: Int, created: String, lang: String, results: WeatherResult)

  case class WeatherResult(channel: WeatherData)

  case class WeatherData(item: WeatherItem)

  case class WeatherItem(condition: Condition, forecast: Seq[Forecast])

  case class Condition(temp: String, text: String)

  case class Forecast(code: String, date: String, day: String, high: String, low: String, text: String)

}

