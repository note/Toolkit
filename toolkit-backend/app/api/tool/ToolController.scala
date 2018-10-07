package api.tool

import api.tool.repo.ToolRepository
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext


/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class ToolController @Inject()(cc: ControllerComponents,
                               implicit val executor: ExecutionContext,
                               repo: ToolRepository) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def get(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>

    repo.list().map( it => Ok(Json.toJson(it)))
  }
}
