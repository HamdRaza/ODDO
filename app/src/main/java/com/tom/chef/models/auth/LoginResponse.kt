package com.tom.chef.models.auth


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
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

    data class OData(
        @SerializedName("device_token")
        val deviceToken: String,
        @SerializedName("dial_code")
        val dialCode: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("fcm_token")
        val fcmToken: String,
        @SerializedName("firebase_user_key")
        val firebaseUserKey: String,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone_number")
        val phoneNumber: String,
        @SerializedName("user_type")
        val userType: String
    )
}