package com.tom.chef.ui.auth.logIn

import com.tom.chef.models.auth.RequestGoogleLogIn

interface LoginInterface {
    fun init()
    fun logInClicked()
    fun registerClicked()
    fun onResetClicked()
    fun showResetInputEmail()
    fun loadFcmToken()

    fun moveToOTP()
    fun moveToDashboard()
    fun moveToAddPhone()

    fun callGoogleLogIn()
    fun callGoogleLogInAPI(requestGoogleLogIn: RequestGoogleLogIn)
    fun callFaceBook()


}