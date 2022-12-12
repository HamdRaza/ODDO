package com.tom.chef.models.profile

import com.tom.chef.models.auth.User

data class ResponseProfileUpdate(
    val message: String,
    val status: Int,
    val oData: User
)