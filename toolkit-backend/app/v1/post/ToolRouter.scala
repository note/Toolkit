package v1.post

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
  * Routes and URLs to the PostResource controller.
  */
class ToolRouter @Inject()(controller: ToolController) extends SimpleRouter {

  override def routes: Routes = {
    case GET(p"/") =>
      controller.index
  }
}