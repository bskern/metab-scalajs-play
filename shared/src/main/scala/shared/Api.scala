package shared

import shared.HNProcessing.HNItem

import scala.concurrent.Future


trait Api {
  def getWeather(): Future[Weather]

  def getSubReddit(sr: String): Future[Seq[RedditProcessing.RedditLink]]

  def getTopHN(): Future[Seq[HNItem]]

  def getAskHN(): Future[Seq[HNItem]]
}
