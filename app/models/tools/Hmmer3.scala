package models.tools

import play.api.data.Form
import play.api.data.Forms._

// TODO Dependency injection might come in handy here

/**
  * Singleton object that stores general information about a tool
  */
object Hmmer3 extends ToolModel {

  // --- Names for the Tool ---
  val toolNameShort:String        = "hmmer3"
  val toolNameLong:String         = "Hmmer3"
  val toolNameAbbreviation:String = "hm3"

  // --- Hmmer3 specific values ---
  // Returns the Input Form Definition of this tool
  val inputForm = Form(
    tuple(
      "sequences"       -> text,
      "mlalign_id_pair" -> boolean,
      "mfast_pair"      -> boolean,
      "mslow_pair"      -> boolean
    )
  )

  /*
    val inports  = Map(

      Sequences -> 1 // TCoffee needs one Set of Sequences
    )
  */

  //Map parameter identifier to the full names
  val parameterNames = Map(
    "sequences" -> "Sequences to be aligned")

  // TODO We need a better abstraction for the tool result names
  val resultFileNames = Vector("result")
}