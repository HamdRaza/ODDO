package com.tom.chef.models.auth

data class RequestLogIn(
    val device_type: String="android",
    val email: String,
    val fcm_token: String,
    val password: String
)