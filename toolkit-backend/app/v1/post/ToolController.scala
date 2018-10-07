package v1.post

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._
import v1.post.repo.ToolRepository

import scala.concurrent.ExecutionContext

/**
  * Takes HTTP requests and produces JSON.
  */
class ToolController @Inject()(cc: ControllerComponents,
                               repo: ToolRepository)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def index: Action[AnyContent] = Action.async { implicit request =>

    repo.list().map { posts =>
      Ok(Json.toJson(posts))
    }
  }
}
