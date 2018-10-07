package v1.post

import javax.inject.{Inject, Singleton}
import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext
import play.api.libs.json.{JsValue, Json, Writes}
import play.api.{Logger, MarkerContext}

import scala.concurrent.Future

final case class Tool(shortName: String, longName: String)


object Tool {

  /**
    * Mapping to write a PostResource out as a JSON value.
    */
  implicit val implicitWrites = new Writes[Tool] {
    def writes(post: Tool): JsValue = {
      Json.obj(
        "shortName" -> post.shortName,
        "longName" -> post.longName
      )
    }
  }

}


class PostExecutionContext @Inject()(actorSystem: ActorSystem) extends CustomExecutionContext(actorSystem, "repository.dispatcher")

/**
  * A pure non-blocking interface for the PostRepository.
  */
trait PostRepository {
  def list()(implicit mc: MarkerContext): Future[Iterable[Tool]]
}

/**
  * A trivial implementation for the Post Repository.
  *
  * A custom execution context is used here to establish that blocking operations should be
  * executed in a different thread than Play's ExecutionContext, which is used for CPU bound tasks
  * such as rendering.
  */
@Singleton
class PostRepositoryImpl @Inject()()(implicit ec: PostExecutionContext) extends PostRepository {

  private val logger = Logger(this.getClass)

  private val postList = List(
    Tool("title 1", "blog post 1"),
    Tool("title 2", "blog post 2"),
    Tool("title 3", "blog post 3"),
    Tool("title 4", "blog post 4"),
    Tool("title 5", "blog post 5")
  )

  override def list()(implicit mc: MarkerContext): Future[Iterable[Tool]] = {
    Future {
      logger.trace(s"list: ")
      postList
    }
  }
}
