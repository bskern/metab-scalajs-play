package controllers

import com.google.inject.Inject
import play.api.{Configuration, Logger}
import play.api.cache.CacheApi
import play.api.mvc._
import shared.{HNProcessing, RedditProcessing, Weather}
import services.DataService
import services.DataService.{Ask, HN, Top}
import upickle.default._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class Application @Inject()(cache: CacheApi, dataService: DataService)(implicit context: ExecutionContext, config: Configuration) extends Controller {

   def index = Action {
     Ok(views.html.main("METAB"))
   }

    def getWeather = Action.async { implicit request =>
    cache.get[Weather]("weather") match {
      case Some(w) => Future.successful(Ok(write(w)))
      case None => dataService.getWeather().map { w =>
        cache.set("weather", w, 15.minutes)
        Ok(write(w))
      }
    }

  }

  def getSubReddit(subreddit: String) = Action.async { implicit request =>
    cache.get[Seq[RedditProcessing.RedditLink]](s"reddit-$subreddit") match {
      case Some(r) => Future.successful(Ok(write(r)))
      case None => dataService.getDataFromReddit(subreddit).map { resp =>
        cache.set(s"reddit-$subreddit", resp, 5.minutes)
        Ok(write(resp))
      }
    }
  }

  def getHNTop = Action.async { implicit request =>
    getHN(Top)
  }

  def getHNAsk = Action.async { implicit request =>
    getHN(Ask)
  }

 private def getHN(entityType: HN): Future[Result] = {
    cache.get[Seq[HNProcessing.HNItem]](entityType.cacheKey) match {
      case Some(hn) => Future.successful(Ok(write(hn)))
      case None => dataService.fetchFromHN(Ask).map { data =>
        cache.set(entityType.cacheKey, data, 5.minutes)
        Ok(write(data))
      }
    }
  }
}

