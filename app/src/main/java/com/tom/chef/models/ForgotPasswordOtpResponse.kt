package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class ForgotPasswordOtpResponse(
    @SerializedName("access_token")
    val accessToken: String,
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