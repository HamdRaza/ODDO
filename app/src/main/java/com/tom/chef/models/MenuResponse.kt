package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @SerializedName("errors")
    val errors: Errors,
    @SerializedName("message")
    val message: String,
    @SerializedName("oData")
    val oData: ArrayList<OData>,
    @SerializedName("status")
    val status: String
) {
    class Errors

    data class OData(
        @SerializedName("count")
        val count: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}