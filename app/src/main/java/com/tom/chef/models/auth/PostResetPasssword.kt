package com.tom.chef.models.auth

data class PostResetPasssword(
    val otp: String,
    var password: String="",
    var password_confirmation: String="",
    val password_reset_code: String
)