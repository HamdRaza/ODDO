package com.tom.chef.network

import com.tom.chef.models.*
import com.tom.chef.models.auth.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("session/authenticate")
    suspend fun loginAPI(@Body logInRequest: LogInRequest): Response<LoginResponse>

}

