package com.tom.chef.models.auth

import com.google.gson.annotations.SerializedName

data class User(
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