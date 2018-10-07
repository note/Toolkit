package v1.post

import play.api.libs.json.{JsValue, Json, Writes}

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
