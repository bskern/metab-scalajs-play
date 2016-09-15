package components


import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.RedditProcessing.RedditLink


object SubredditContainer {

  case class Props(subreddit:String,articles:Seq[RedditLink])

  val component = ReactComponentB[Props]("SubredditContainer")
    .render_P { P =>
      <.div(
        Header(Header.Props(P.subreddit,true)),
        ((Stream from 1) zip P.articles.take(10)).toIndexedSeq.sortBy(_._1).map {
          case (k, v) => Article(Article.Props(v.title, v.url, true), k)
        }
      )
    }.build


  def apply(props: Props) = component(props)
}
