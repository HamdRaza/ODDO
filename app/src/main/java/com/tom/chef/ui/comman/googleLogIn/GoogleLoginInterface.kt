package com.tom.chef.ui.comman.googleLogIn

import com.tom.chef.models.auth.RequestGoogleLogIn

interface GoogleLoginInterface {
    fun onLogInCompleted(requestGoogleLogIn: RequestGoogleLogIn)
}