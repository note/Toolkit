package api.tool

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
  * Routes and URLs for the Tool Resource
  *
  */
class ToolRouter @Inject()(controller: ToolController) extends SimpleRouter {

  override def routes: Routes = {

    case GET(p"/") => controller.get
  }
}
