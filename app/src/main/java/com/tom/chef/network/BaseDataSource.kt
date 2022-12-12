package com.tom.chef.network

import com.tom.chef.utils.Resource
import com.google.gson.Gson
import retrofit2.Response

abstract class BaseDataSource() {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return  getErrorData(response)
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error(" $message")
    }

    private fun <T> getErrorData(response: Response<T>): Resource<T> {
        val message: ErrorMessage = Gson().fromJson(response.errorBody()!!.charStream(), ErrorMessage::class.java)
        return error(message.message)
    }

}