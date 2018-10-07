package api.tool.resource

import play.api.libs.json.{JsValue, Json, Writes}

/**
  * A Tool in the sense of the Tool resource of the Toolkit.
  *
  */
final case class ToolResource(shortName: String, longName: String)

object ToolResource {

  /**
    * Mapping to write a PostResource out as a JSON value.
    */
  implicit val implicitWrites = new Writes[ToolResource] {
    def writes(tool: ToolResource): JsValue = Json.obj(
        "shortName" -> tool.shortName,
        "longName" -> tool.longName
      )
  }
}
