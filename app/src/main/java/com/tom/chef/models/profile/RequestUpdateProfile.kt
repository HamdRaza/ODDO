package com.tom.chef.models.profile


data class RequestUpdateProfile(
    val first_name: String,
    val last_name: String,
    val user_image: String?
)