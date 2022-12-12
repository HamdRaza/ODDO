package com.tom.chef.models.profile

import com.tom.chef.models.auth.User

data class ResponseProfile(
    val error: Any,
    val message: String,
    val status: Int,
    val oData:User
)