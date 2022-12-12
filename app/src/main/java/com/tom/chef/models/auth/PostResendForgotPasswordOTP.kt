package com.tom.chef.models.auth

data class PostResendForgotPasswordOTP(
    val password_reset_code: String,
    val type: String
)