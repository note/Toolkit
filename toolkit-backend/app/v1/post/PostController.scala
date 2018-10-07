package v1.post

import javax.inject.Inject

import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

case class PostFormInput(title: String, body: String)

/**
  * Takes HTTP requests and produces JSON.
  */
class PostController @Inject()(cc: PostControllerComponents)(implicit ec: ExecutionContext)
    extends PostBaseController(cc) {

  def index: Action[AnyContent] = PostAction.async { implicit request =>

    repo.list().map { posts =>
      Ok(Json.toJson(posts))
    }
  }
}
