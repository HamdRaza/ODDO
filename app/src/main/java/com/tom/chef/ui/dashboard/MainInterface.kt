package com.tom.chef.ui.dashboard

interface MainInterface {
    fun callMyProfileAPI()
    fun getImageFile()
    fun implementListners()
    fun getCountryCodes()
    fun onBackButtonClicked()
    fun onNotificationClicked()
    fun onAccountClicked()
    fun onHomeClicked()

    //Location
    fun askNotificationPermission()
    fun checkForIntent()
    fun moveToLogInScreen()
    fun updateUserStatus(boolean: Boolean)

}