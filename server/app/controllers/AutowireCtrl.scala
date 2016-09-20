package controllers

import com.google.inject.Inject
import play.api.{Configuration, Logger}
import play.api.mvc.{Action, Controller}
import services.ApiService
import autowire._
import play.api.http.{ContentTypeOf, ContentTypes}
import shared.Api
import upickle.default._
import upickle.{Js, json}
import play.api.mvc._
import upickle.Js.Obj

import scala.concurrent.{ExecutionContext, Future}

object Router extends autowire.Server[Js.Value, Reader, Writer] {
  def write[Result: Writer](r: Result) = writeJs(r)
  def read[Result: Reader](p: Js.Value) = readJs[Result](p)
}

class AutowireCtrl @Inject()(apiService: ApiService)(implicit context: ExecutionContext, config: Configuration) extends Controller {
  implicit def contentTypeOf_upickle_Js_Value(implicit jsValue: Js.Value) =
    ContentTypeOf[Js.Value](Some(ContentTypes.JSON))

  def autowireApi(s: String) = Action.async { implicit request =>
    val path = s.split("/").toSeq
    Logger.debug(s"Request path: $path ${request.body}")

    request.body.asJson.map { json =>
      upickle.json.read(json.toString()) match {
        case Js.Obj(objArgs@_*) =>
          val r = autowire.Core.Request(path, objArgs.toMap)
          Router.route[Api](apiService)(r).flatMap { responseData =>
            Future.successful(Ok(upickle.json.write(responseData)))
          }
        case _ =>
          Future.failed(new Exception("Arguments need to be a valid JSON object"))
      }
    }.getOrElse {
      Logger.info("Got bad request: " + request.body.asRaw.toString)
      Future.successful(BadRequest)
    }
  }
}


