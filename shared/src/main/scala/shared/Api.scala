package shared

import shared.HNProcessing.HNItem

import scala.concurrent.Future


trait Api {
  def getWeather(): Weather

  def getSubReddit(sr: String): Seq[RedditProcessing.RedditLink]

  def getTopHN(): Seq[HNItem]

  def getAskHN(): Seq[HNItem]
}
