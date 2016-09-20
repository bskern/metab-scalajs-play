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
}

