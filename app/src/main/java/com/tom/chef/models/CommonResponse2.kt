package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class CommonResponse2(
    @SerializedName("errors")
    val errors: Errors,
    @SerializedName("message")
    val message: String,
    @SerializedName("oData")
    val oData: OData,
    @SerializedName("status")
    val status: String
) {
    class Errors

    class OData
}