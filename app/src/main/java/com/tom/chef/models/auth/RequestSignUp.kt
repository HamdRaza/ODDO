package com.tom.chef.models.auth

data class RequestSignUp(
    val device_type: String="android",
    val dial_code: String,
    val email: String,
    val fcm_token: String,
    val first_name: String,
    val last_name: String,
    val password: String,
    val phone: String
)