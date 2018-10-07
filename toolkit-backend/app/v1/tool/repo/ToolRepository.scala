package v1.tool.repo

import com.google.inject.ImplementedBy
import v1.tool.Tool

import scala.concurrent.Future

/**
  * Trait for entities that can make tools available
  *
  * @author Lukas Zimmermann
  */
@ImplementedBy(classOf[SimpleRunscriptToolRepository])
trait ToolRepository {

  /**
    * Lists all the tools that this repository has to offer
   */
  def list(): Future[Iterable[Tool]]
}
