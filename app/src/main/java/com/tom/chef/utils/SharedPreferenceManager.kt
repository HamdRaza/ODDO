package com.tom.chef.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.tom.chef.models.ProfileResponse
import com.tom.chef.models.auth.User
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


    fun saveUser(user: ProfileResponse.OData?, accessToken: String?) {
        user?.let { thisUser ->
            prefs.edit {
                it.putString(savedUser.first, Gson().toJson(thisUser))
                accessToken?.let {
                    getAccessToken = accessToken
                }
            }
        }

    }

    var getAccessToken: String?
        get() = prefs.getString(access_token.first, access_token.second)
        set(value) = prefs.edit {
            it.putString(access_token.first, value)
        }

    fun getSavedUser(): User? {
        prefs.getString(savedUser.first, savedUser.second)?.let {
            Gson().fromJson(it, User::class.java)?.let {
                return it
            }
        }
        return null
    }

    fun clear() {
        prefs.edit().clear()
    }
}