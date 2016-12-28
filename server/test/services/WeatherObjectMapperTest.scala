package services
import org.scalatest._
import Matchers._
import shared.Weather
import shared.WeatherProcessing.{Forecast, WeatherResponse}
import upickle.default._
/**
  */
class WeatherObjectMapperTest extends FlatSpec {

  "My Weather Object Mapper" should "properly transform JSON to my object model via uPickle" in {
    val result = read[WeatherResponse](WeatherObjectMapperTest.json)
    println(s"WeatherResp is $result")
    result shouldNot be (null)
    result.query.results.channel.item shouldNot be (null)

  }
  "My object mapping logic should" should "match values in JSON" in {
    val weatherResp = read[WeatherResponse](WeatherObjectMapperTest.json)
    val result = WeatherObjectMapperTest.getWeather(weatherResp)

    result.currentTemp should equal ("27")
    result.high should equal ("32")
    result.low should equal ("21")
    result.desc should equal ("Partly Cloudy")
  }
}
object WeatherObjectMapperTest {
  def getWeather(wResp:WeatherResponse):Weather = {
    val weatherItem = wResp.query.results.channel.item
    val forecast = weatherItem.forecast.head
    val currentTemp = weatherItem.condition.temp
    val Forecast(_, _, _, high, low, desc) = forecast
    Weather(currentTemp, desc, high, low)
  }
  val json  =
    """
      |{
      |  "query": {
      |    "count": 1,
      |    "created": "2016-12-28T15:39:28Z",
      |    "lang": "en-US",
      |    "results": {
      |      "channel": {
      |        "units": {
      |          "distance": "mi",
      |          "pressure": "in",
      |          "speed": "mph",
      |          "temperature": "F"
      |        },
      |        "title": "Yahoo! Weather - Minneapolis, MN, US",
      |        "link": "http:\/\/us.rd.yahoo.com\/dailynews\/rss\/weather\/Country__Country\/*https:\/\/weather.yahoo.com\/country\/state\/city-2452078\/",
      |        "description": "Yahoo! Weather for Minneapolis, MN, US",
      |        "language": "en-us",
      |        "lastBuildDate": "Wed, 28 Dec 2016 09:39 AM CST",
      |        "ttl": "60",
      |        "location": {
      |          "city": "Minneapolis",
      |          "country": "United States",
      |          "region": " MN"
      |        },
      |        "wind": {
      |          "chill": "19",
      |          "direction": "245",
      |          "speed": "11"
      |        },
      |        "atmosphere": {
      |          "humidity": "77",
      |          "pressure": "975.0",
      |          "rising": "0",
      |          "visibility": "16.1"
      |        },
      |        "astronomy": {
      |          "sunrise": "7:51 am",
      |          "sunset": "4:39 pm"
      |        },
      |        "image": {
      |          "title": "Yahoo! Weather",
      |          "width": "142",
      |          "height": "18",
      |          "link": "http:\/\/weather.yahoo.com",
      |          "url": "http:\/\/l.yimg.com\/a\/i\/brand\/purplelogo\/\/uh\/us\/news-wea.gif"
      |        },
      |        "item": {
      |          "title": "Conditions for Minneapolis, MN, US at 09:00 AM CST",
      |          "lat": "44.979031",
      |          "long": "-93.264931",
      |          "link": "http:\/\/us.rd.yahoo.com\/dailynews\/rss\/weather\/Country__Country\/*https:\/\/weather.yahoo.com\/country\/state\/city-2452078\/",
      |          "pubDate": "Wed, 28 Dec 2016 09:00 AM CST",
      |          "condition": {
      |            "code": "30",
      |            "date": "Wed, 28 Dec 2016 09:00 AM CST",
      |            "temp": "27",
      |            "text": "Partly Cloudy"
      |          },
      |          "forecast": [
      |            {
      |              "code": "30",
      |              "date": "28 Dec 2016",
      |              "day": "Wed",
      |              "high": "32",
      |              "low": "21",
      |              "text": "Partly Cloudy"
      |            },
      |            {
      |              "code": "28",
      |              "date": "29 Dec 2016",
      |              "day": "Thu",
      |              "high": "29",
      |              "low": "26",
      |              "text": "Mostly Cloudy"
      |            },
      |            {
      |              "code": "28",
      |              "date": "30 Dec 2016",
      |              "day": "Fri",
      |              "high": "31",
      |              "low": "22",
      |              "text": "Mostly Cloudy"
      |            },
      |            {
      |              "code": "30",
      |              "date": "31 Dec 2016",
      |              "day": "Sat",
      |              "high": "32",
      |              "low": "17",
      |              "text": "Partly Cloudy"
      |            },
      |            {
      |              "code": "30",
      |              "date": "01 Jan 2017",
      |              "day": "Sun",
      |              "high": "32",
      |              "low": "17",
      |              "text": "Partly Cloudy"
      |            },
      |            {
      |              "code": "16",
      |              "date": "02 Jan 2017",
      |              "day": "Mon",
      |              "high": "30",
      |              "low": "22",
      |              "text": "Snow"
      |            },
      |            {
      |              "code": "28",
      |              "date": "03 Jan 2017",
      |              "day": "Tue",
      |              "high": "21",
      |              "low": "4",
      |              "text": "Mostly Cloudy"
      |            },
      |            {
      |              "code": "30",
      |              "date": "04 Jan 2017",
      |              "day": "Wed",
      |              "high": "4",
      |              "low": "-3",
      |              "text": "Partly Cloudy"
      |            },
      |            {
      |              "code": "30",
      |              "date": "05 Jan 2017",
      |              "day": "Thu",
      |              "high": "2",
      |              "low": "-6",
      |              "text": "Partly Cloudy"
      |            },
      |            {
      |              "code": "30",
      |              "date": "06 Jan 2017",
      |              "day": "Fri",
      |              "high": "4",
      |              "low": "-9",
      |              "text": "Partly Cloudy"
      |            }
      |          ],
      |          "description": "<![CDATA[<img src=\"http:\/\/l.yimg.com\/a\/i\/us\/we\/52\/30.gif\"\/>\n<BR \/>\n<b>Current Conditions:<\/b>\n<BR \/>Partly Cloudy\n<BR \/>\n<BR \/>\n<b>Forecast:<\/b>\n<BR \/> Wed - Partly Cloudy. High: 32Low: 21\n<BR \/> Thu - Mostly Cloudy. High: 29Low: 26\n<BR \/> Fri - Mostly Cloudy. High: 31Low: 22\n<BR \/> Sat - Partly Cloudy. High: 32Low: 17\n<BR \/> Sun - Partly Cloudy. High: 32Low: 17\n<BR \/>\n<BR \/>\n<a href=\"http:\/\/us.rd.yahoo.com\/dailynews\/rss\/weather\/Country__Country\/*https:\/\/weather.yahoo.com\/country\/state\/city-2452078\/\">Full Forecast at Yahoo! Weather<\/a>\n<BR \/>\n<BR \/>\n(provided by <a href=\"http:\/\/www.weather.com\" >The Weather Channel<\/a>)\n<BR \/>\n]]>",
      |          "guid": {
      |            "isPermaLink": "false"
      |          }
      |        }
      |      }
      |    }
      |  }
      |}
    """.stripMargin
}
