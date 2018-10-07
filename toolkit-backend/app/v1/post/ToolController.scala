package v1.post

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

/**
  * Takes HTTP requests and produces JSON.
  */
class ToolController @Inject()(cc: ControllerComponents,
                               repo: PostRepository)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def index: Action[AnyContent] = Action.async { implicit request =>

    repo.list().map { posts =>
      Ok(Json.toJson(posts))
    }
  }
}
