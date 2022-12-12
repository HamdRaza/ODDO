package com.tom.chef.models.profile


import com.google.gson.annotations.SerializedName

data class RequestContactUs(
    @SerializedName("dial_code")
    val dialCode: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String
)