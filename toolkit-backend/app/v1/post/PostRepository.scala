package v1.post

import javax.inject.{Inject, Singleton}
import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext
import play.api.libs.json.{JsValue, Json, Writes}
import play.api.{Logger, MarkerContext}

import scala.concurrent.Future

final case class PostData(id: Int, title: String, body: String)


object PostData {

  /**
    * Mapping to write a PostResource out as a JSON value.
    */
  implicit val implicitWrites = new Writes[PostData] {
    def writes(post: PostData): JsValue = {
      Json.obj(
        "id" -> post.id,
        "title" -> post.title,
        "body" -> post.body
      )
    }
  }

}


class PostExecutionContext @Inject()(actorSystem: ActorSystem) extends CustomExecutionContext(actorSystem, "repository.dispatcher")

/**
  * A pure non-blocking interface for the PostRepository.
  */
trait PostRepository {
  def create(data: PostData)(implicit mc: MarkerContext): Future[Int]

  def list()(implicit mc: MarkerContext): Future[Iterable[PostData]]

  def get(id: Int)(implicit mc: MarkerContext): Future[Option[PostData]]
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
    PostData(1, "title 1", "blog post 1"),
    PostData(2, "title 2", "blog post 2"),
    PostData(3, "title 3", "blog post 3"),
    PostData(4, "title 4", "blog post 4"),
    PostData(5, "title 5", "blog post 5")
  )

  override def list()(implicit mc: MarkerContext): Future[Iterable[PostData]] = {
    Future {
      logger.trace(s"list: ")
      postList
    }
  }

  override def get(id: Int)(implicit mc: MarkerContext): Future[Option[PostData]] = {
    Future {
      logger.trace(s"get: id = $id")
      postList.find(post => post.id == id)
    }
  }

  def create(data: PostData)(implicit mc: MarkerContext): Future[Int] = {
    Future {
      logger.trace(s"create: data = $data")
      data.id
    }
  }

}
