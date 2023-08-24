package com.tom.chef.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.tom.chef.models.auth.LoginResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceManager @Inject constructor(@ApplicationContext context: Context) {
    private val NAME = "mainSharedPreference"
    private val MODE = Context.MODE_PRIVATE
    private val fcmToken = Pair("fcmToken", "asd")
    private val logedIn = Pair("logedIn", false)
    private val savedUser = Pair("user", null)
    private val access_token = Pair("access_token", null)
    private val tempId = Pair("temp_id", 0)

    val prefs = context.getSharedPreferences(NAME, MODE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var getFcmToken: String
        get() = prefs.getString(fcmToken.first, fcmToken.second).toString()
        set(value) = prefs.edit() {
            it.putString(fcmToken.first, value)
        }

    var isLogedIn: Boolean
        get() = prefs.getBoolean(logedIn.first, logedIn.second)
        set(value) = prefs.edit() {
            it.putBoolean(logedIn.first, value)
        }

    var getTempId: Int
        get() = prefs.getInt(tempId.first, tempId.second)
        set(value) = prefs.edit() {
            it.putInt(tempId.first, value)
        }

    var getAccessToken: String?
        get() = prefs.getString(access_token.first, access_token.second)
        set(value) = prefs.edit {
            it.putString(access_token.first, value)
        }

    fun clear() {
        prefs.edit().clear()
    }

    fun saveUser(user: LoginResponse.Result) {
        user.let { thisUser ->
            prefs.edit {
                it.putString(savedUser.first, Gson().toJson(thisUser))
            }
        }

    }


    fun getSavedUser(): LoginResponse.Result? {
        prefs.getString(savedUser.first, savedUser.second)?.let {
            Gson().fromJson(it, LoginResponse.Result::class.java)?.let {
                return it
            }
        }
        return null
    }
}