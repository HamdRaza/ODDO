package com.tom.chef.network

import android.util.Log
import android.webkit.CookieManager
import com.tom.chef.utils.Resource
import com.google.gson.Gson
import retrofit2.Response

abstract class BaseDataSource() {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {

                    response.headers().values("Set-Cookie").let {
                        it.forEach {
                            CookieManager.getInstance().setCookie(NetworkUtils.BASE_URL.removeSuffix("/"),it)
                            Log.i("Cookies",it)
                        }
                    }

                    return Resource.success(body, code = response.code())
                }
            }
            Log.i("ErrorCode","001")
            return  getErrorData(response)
        } catch (e: Exception) {
            Log.i("ErrorCode","002")
            e.printStackTrace()
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String,code:Int=0): Resource<T> {
        return Resource.error(" $message", code = code)
    }

    private fun <T> getErrorData(response: Response<T>): Resource<T> {
        val message: ErrorMessage = Gson().fromJson(response.errorBody()!!.charStream(), ErrorMessage::class.java)
        return error(message.message, code = response.code())
    }

}