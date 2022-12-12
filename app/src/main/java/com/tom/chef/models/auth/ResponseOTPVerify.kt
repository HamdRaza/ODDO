package com.tom.chef.models.auth


import com.google.gson.annotations.SerializedName

data class ResponseOTPVerify(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("firebase_user_key")
    val firebaseUserKey: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)