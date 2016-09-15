package components

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.HNProcessing.HNItem


object HNContainer {
  case class Props(title:String,items:Seq[HNItem])

  val component = ReactComponentB[Props]("MyContainer")
    .render_P { P =>
      <.div(
        Header(Header.Props(P.title,false)),
        ((Stream from 1) zip P.items).toIndexedSeq.sortBy(_._1).map {
          case(k,v)=> Article(Article.Props(v.title,v.url,false),k)
        }
      )
    }.build

  def apply(props:Props) = component(props)
}
