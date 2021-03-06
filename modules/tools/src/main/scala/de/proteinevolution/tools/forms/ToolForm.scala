package de.proteinevolution.tools.forms
import de.proteinevolution.params.Param
import io.circe.generic.JsonCodec

@JsonCodec case class ToolForm(
    toolname: String,
    toolnameLong: String,
    toolnameAbbrev: String,
    category: String,
    params: Seq[(String, Seq[Param])]
)
