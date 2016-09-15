package shared

object HNProcessing {
  case class HNItem(title:String,id:Double, url:String="")

  case class tempHNAsk(title: String, id: Double)

  def toHNItem(title: String, id: Double): HNItem = {
    HNItem(title,id, s"https://news.ycombinator.com/item?id=${id}")
  }
}

