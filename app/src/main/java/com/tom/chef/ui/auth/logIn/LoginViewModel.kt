package com.tom.chef.ui.auth.logIn

import android.view.View
import androidx.lifecycle.ViewModel

class LoginViewModel() : ViewModel() {

    var mCallback: LoginInterface?=null

    fun logInClicked() = mCallback?.logInClicked()

    fun moveToDashboard() = mCallback?.moveToDashboard()
    fun init() {
        mCallback?.init()
        mCallback?.keyClockSetUP()
    }
    fun keyClockLogIn(){
        mCallback?.keyClockLogIn()
    }
}