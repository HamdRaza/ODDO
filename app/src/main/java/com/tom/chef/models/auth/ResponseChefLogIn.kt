package com.tom.chef.models.auth


import com.google.gson.annotations.SerializedName

data class ResponseChefLogIn(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("oData")
    val oData: User?,
    @SerializedName("status")
    val status: String
)