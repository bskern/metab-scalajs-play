package shared

object RedditProcessing {

  case class RedditLink(title:String,url:String)
  case class RedditData(data:RedditLink)
  case class RedditWrapper(children: Seq[RedditData])

  case class RedditResponse(data: RedditWrapper)
}

