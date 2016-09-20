package components

import css.GlobalStyle
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import shared.HNProcessing.HNItem
import shared.RedditProcessing.RedditLink

import scalacss.ScalaCssReact._
import scalacss._
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import autowire._
import services.AjaxClient
import shared.Api

object SiteContainer {

  import upickle.default._

  @inline private def Style = GlobalStyle

  class Backend($: BackendScope[Unit, State]) {
    def render(s: State) = {
      <.div(Style.app,
        WeatherContainer(),
        <.div(Style.container,
          <.div(Style.row,
            <.div(Style.col(4),
              SubredditContainer(SubredditContainer.Props("scala", s.scala))
            ),
            <.div(Style.col(4),
              SubredditContainer(SubredditContainer.Props("elm", s.elm))
            ),
            <.div(Style.col(4),
              SubredditContainer(SubredditContainer.Props("reactjs", s.reactjs))
            )
          ),
          <.div(Style.row,
            <.div(Style.col(6),
              HNContainer(HNContainer.Props("Top Stories", s.hnTop))),
            <.div(Style.col(6),
              HNContainer(HNContainer.Props("Ask", s.hnAsk)))
          )
        ))
    }
  }

  case class State(scala: Seq[RedditLink], elm: Seq[RedditLink], reactjs: Seq[RedditLink], hnTop: Seq[HNItem], hnAsk: Seq[HNItem])

  val component = ReactComponentB[Unit]("siteContainer")
    .initialState(State(Nil, Nil, Nil, Nil, Nil))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {

      val subreddits = Seq("scala", "elm", "reactjs")
      subreddits.foreach { subreddit =>
        AjaxClient[Api].getSubReddit(subreddit).call().foreach { redditData =>
          subreddit match {
            case "scala" => scope.modState(_.copy(scala = redditData)).runNow()
            case "elm" => scope.modState(_.copy(elm = redditData)).runNow()
            case "reactjs" => scope.modState(_.copy(reactjs = redditData)).runNow()
          }
        }
      }

      AjaxClient[Api].getTopHN().call().foreach { topStories =>
        scope.modState(_.copy(hnTop = topStories)).runNow()
      }
      AjaxClient[Api].getAskHN().call().foreach { askStories =>
        scope.modState(_.copy(hnAsk = askStories)).runNow()
      }
    }).build

  def apply() = component()
}
