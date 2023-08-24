package com.tom.chef.network.app_repository

import com.tom.chef.models.auth.*
import com.tom.chef.network.ApiServiceImple
import com.tom.chef.utils.performGetOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiServiceImple: ApiServiceImple) {

    // FOR LOGIN API
    fun loginAPI(request: LogInRequest)= performGetOperation {
        apiServiceImple.loginAPI(request)
    }
}