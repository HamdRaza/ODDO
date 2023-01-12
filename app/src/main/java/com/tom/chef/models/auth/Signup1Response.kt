package com.tom.chef.models.auth


import com.google.gson.annotations.SerializedName

data class Signup1Response(
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

    data class OData(
        @SerializedName("temp_id")
        val tempId: Int
    )
}