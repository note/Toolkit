package de.proteinevolution.models.param

import io.circe.generic.JsonCodec

@JsonCodec case class Param(
    name: String,
    paramType: ParamType,
    internalOrdering: Int,
    label: String
)
