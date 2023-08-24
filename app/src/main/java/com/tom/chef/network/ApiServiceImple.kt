package com.tom.chef.network

import com.tom.chef.models.auth.*
import com.tom.chef.utils.SharedPreferenceManager
import javax.inject.Inject

class ApiServiceImple @Inject constructor(val apiService: ApiService) : BaseDataSource() {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    // FOR LOGIN USER
    suspend fun loginAPI(request: LogInRequest)=getResult {
        apiService.loginAPI(logInRequest = request)
    }
}