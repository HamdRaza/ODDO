package com.tom.chef.models.auth

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

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
data class RequestSignUp2(
    val temp_id: RequestBody,
    val trade_license: MultipartBody.Part?,
    val passport: MultipartBody.Part?,
    val visa_copy: MultipartBody.Part?,
    val emirates_id: MultipartBody.Part?,
    val bank_account_proof: MultipartBody.Part?,
    val preparation_time: RequestBody,
    val preparation_unit: RequestBody,
)