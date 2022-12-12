package com.tom.chef.models

data class ResponseSuccess(
    val message: String,
    val status: Int,
    val error:Any
)