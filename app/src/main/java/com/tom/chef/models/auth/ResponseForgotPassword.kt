package com.tom.chef.models.auth

import com.google.gson.Gson

data class ResponseForgotPassword(
    val message: String,
    val oData: Any,
    val status: Int
){

}

fun Any.getResetCode():String?{
    try {
        Gson().fromJson(this.toString(),oData::class.java)?.let {
            return it.password_reset_code
        }
    }catch (e:Exception){
        e.printStackTrace()
    }
    return null
}

data class oData(
   val password_reset_code:String
)