package com.tom.chef.models.auth

// Use email and type=2  for email reset
// Use dial_code,phone and type=1 for phone

data class RequestResetPassword(
    val dial_code: String="",
    val email: String="",
    val phone: String="",
    val type: String=""
)