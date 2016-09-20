package services

import com.google.inject.Inject
import play.api.cache.CacheApi
import play.api.mvc.Result
import services.DataService.{Ask, HN, Top}
import shared.HNProcessing.HNItem
import shared.RedditProcessing.RedditLink
import shared.{Api, HNProcessing, RedditProcessing, Weather}
import upickle.default._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}


class ApiService  @Inject()(ds: DataService, cache:CacheApi)(implicit context: ExecutionContext) extends Api {

  override def getWeather(): Weather = {
    cache.get[Weather]("weather") match {
      case Some(w) => w
      case None =>
        val w = Await.result(ds.getWeather(), 5 seconds)
        cache.set("weather", w, 15.minutes)
        w
    }
  }

  override def getSubReddit(sr: String): Seq[RedditProcessing.RedditLink] = {
    cache.get[Seq[RedditProcessing.RedditLink]](s"reddit-$sr") match {
      case Some(r) => r
      case None =>
        val r = Await.result(ds.getDataFromReddit(sr), 5 seconds)
        cache.set(s"reddit-$sr", r, 5.minutes)
        r
    }
  }

  override def getAskHN(): Seq[HNItem] = getHN(Ask)

  override def getTopHN(): Seq[HNItem] = getHN(Top)

  private def getHN(entityType: HN): Seq[HNProcessing.HNItem] = {
    cache.get[Seq[HNProcessing.HNItem]](entityType.cacheKey) match {
      case Some(hn) => hn
      case None =>
        val hn = Await.result(ds.fetchFromHN(entityType), 5 seconds)
        cache.set(entityType.cacheKey, hn, 5.minutes)
        hn
    }
  }
}
