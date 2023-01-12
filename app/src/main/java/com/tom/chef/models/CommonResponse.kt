package com.tom.chef.models

import com.google.gson.annotations.SerializedName

data class CommonResponse(

    @field:SerializedName("oData")
    val oData: List<Any>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("errors")
    val errors: Errors,

    @field:SerializedName("status")
    val status: String
) {

    data class Errors(
        val any: Any? = null
    )
}
