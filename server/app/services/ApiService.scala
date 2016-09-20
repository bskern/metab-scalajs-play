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
import scala.concurrent.{ExecutionContext, Future}


class ApiService  @Inject()(ds: DataService, cache:CacheApi)(implicit context: ExecutionContext) extends Api {

  override def getWeather(): Future[Weather] = {
    cache.get[Weather]("weather") match {
      case Some(w) => Future.successful(w)
      case None =>
        ds.getWeather().map { w =>
          cache.set("weather", w, 15.minutes)
          w
        }
    }
  }

  override def getSubReddit(sr: String): Future[Seq[RedditProcessing.RedditLink]] = {
    cache.get[Seq[RedditProcessing.RedditLink]](s"reddit-$sr") match {
      case Some(r) => Future.successful(r)
      case None =>
      ds.getDataFromReddit(sr).map { r=>
        cache.set(s"reddit-$sr", r, 5.minutes)
        r
      }
    }
  }

  override def getAskHN(): Future[Seq[HNItem]] = getHN(Ask)

  override def getTopHN(): Future[Seq[HNItem]] = getHN(Top)

  private def getHN(entityType: HN): Future[Seq[HNProcessing.HNItem]] = {
    cache.get[Seq[HNProcessing.HNItem]](entityType.cacheKey) match {
      case Some(hn) => Future.successful(hn)
      case None =>
        ds.fetchFromHN(entityType).map { hn =>
          cache.set(entityType.cacheKey, hn, 5.minutes)
          hn
        }
    }
  }
}
