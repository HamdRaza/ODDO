package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class CuisineResponse(
    @SerializedName("errors")
    val errors: Errors,
    @SerializedName("message")
    val message: String,
    @SerializedName("oData")
    val oData: List<OData>,
    @SerializedName("status")
    val status: String
) {
    class Errors

    data class OData(
        @SerializedName("count")
        val count: Int,
        @SerializedName("cuisine_name")
        val cuisineName: String,
        @SerializedName("id")
        val id: Int
    )
}