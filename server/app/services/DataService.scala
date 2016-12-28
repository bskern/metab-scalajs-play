package services

import com.google.inject.Inject
import play.api.Logger
import play.api.libs.ws.{WSClient, WSResponse}
import services.DataService.HN
import shared.HNProcessing.HNItem
import shared.RedditProcessing.RedditResponse
import shared.{HNProcessing, RedditProcessing, Weather}
import shared.WeatherProcessing.{Forecast, WeatherResponse}
import upickle.default._

import scala.concurrent.{ExecutionContext, Future}

class DataService @Inject()(ws: WSClient)(implicit context: ExecutionContext) {

  def getWeather(): Future[Weather] = {
    Logger.debug("Fetching weather from Yahoo")
    val url = "https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where u='f' AND woeid in (select woeid from geo.places(1) where text=\"minneapolis\")&format=json"
    ws.url(url).get().map { wsresp =>
      Logger.warn(s"response body  is ${wsresp.body}")
      val weatherResp = read[WeatherResponse](wsresp.body)
      Logger.warn(s"WeatherResp is $weatherResp")
      val weatherItem = weatherResp.query.results.channel.item
      val forecast = weatherItem.forecast.head
      val currentTemp = weatherItem.condition.temp
      val Forecast(_, _, _, high, low, desc) = forecast
      Weather(currentTemp, desc, high, low)
    }
  }

  def getDataFromReddit(sr:String):Future[Seq[RedditProcessing.RedditLink]] = {
    Logger.debug(s"fetching data for reddit for subreddit:$sr")
    ws.url(s"https://www.reddit.com/r/$sr.json").get().map { wsresp =>
      val resp = read[RedditResponse](wsresp.body)
        resp.data.children.map(_.data)
    }
  }

  def fetchFromHN(itemType:HN):Future[Seq[HNProcessing.HNItem]] = {
    Logger.debug(s"about to fetch from HN for $itemType stories ")
  for {
      idResp <- ws.url(itemType.url).get()
      ids <- Future.successful(read[Seq[Double]](idResp.body))
      rawStories <- loadIndividualStories(ids)
    } yield {
    rawStories.map { res =>
        val data = read[HNItem](res.body)
        if (data.url.isEmpty) {
          HNProcessing.toHNItem(data.title, data.id)
        } else {
          data
        }
      }
    }

  }
  private def getHNItem(id: Double): Future[WSResponse] = {
    val url = f"https://hacker-news.firebaseio.com/v0/item/$id%8.0f.json"
    ws.url(url).get()
  }

  private def loadIndividualStories(ids: Seq[Double]): Future[Seq[WSResponse]] = {
    Future.sequence(ids.take(15).map(getHNItem(_)))
  }

}

object DataService {
  sealed trait HN {
    def url:String
    def cacheKey: String
  }
  case object Ask extends HN {
    override def url = "https://hacker-news.firebaseio.com/v0/askstories.json"
    override def cacheKey ="hnAsk"
    override def toString = "ask"
  }
  case object Top extends HN {
    override def url = "https://hacker-news.firebaseio.com/v0/topstories.json"
    override def cacheKey ="hnTop"
    override def toString = "top"
  }
}

