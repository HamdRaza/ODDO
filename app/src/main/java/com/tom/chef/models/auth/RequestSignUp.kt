package com.tom.chef.models.auth

//first_name:Chef
//last_name:Mb
//email:kiyara@gmail.com
//dial_code:971
//mobile:5465656
//address:safdsfds
//latitude:22.54545
//longitude:55.34454
//agree_terms:1
//password:Hello@1985
//confirm_password:Hello@1985
data class RequestSignUp(
    val first_name: String,
    val last_name: String,
    val email: String,
    val dial_code: String,
    val mobile: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val agree_terms: String,
    val password: String,
    val confirm_password: String
)