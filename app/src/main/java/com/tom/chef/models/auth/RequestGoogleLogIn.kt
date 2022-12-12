package com.tom.chef.models.auth

data class RequestGoogleLogIn(
    val email: String,
    val fcm_token: String,
    val name: String
)