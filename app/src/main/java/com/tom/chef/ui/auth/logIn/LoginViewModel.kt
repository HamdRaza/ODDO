package com.tom.chef.ui.auth.logIn

import android.view.View
import androidx.lifecycle.ViewModel
import com.tom.chef.models.auth.RequestGoogleLogIn
import com.tom.chef.newBase.BaseActivity

class LoginViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var mCallback: LoginInterface

    fun onResetClicked(view: View) = mCallback.onResetClicked()
    fun showResetInputEmail() = mCallback.showResetInputEmail()
    fun logInClicked(view: View) = mCallback.logInClicked()
    fun registerClicked(view: View) = mCallback.registerClicked()
    fun callGoogleLogIn(view: View) = mCallback.callGoogleLogIn()
    fun callGoogleLogInAPI(requestGoogleLogIn: RequestGoogleLogIn) =
        mCallback.callGoogleLogInAPI(requestGoogleLogIn)

    fun moveToOTP() = mCallback.moveToOTP()
    fun moveToDashboard() = mCallback.moveToDashboard()
    fun moveToAddPhone() = mCallback.moveToAddPhone()
    fun callFaceBook() = mCallback.callFaceBook()
    fun init() {
        mCallback.init()
        mCallback.loadFcmToken()
    }
}