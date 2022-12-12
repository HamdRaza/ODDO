package com.tom.chef.models.auth

data class ResponseGoogleLogIn(
    val access_token: String,
    val expires_at: String,
    val message: String,
    val status: Int,
    val token_type: String,
    val user: User?
)