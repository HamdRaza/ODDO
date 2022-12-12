package com.tom.chef.ui.auth.signUp

interface SignUpInterface {
    fun registerClicked()
    fun moveToLogIn()
    fun moveToOTP()
    fun getCountriesList()
    fun showCountryCodeDialog()
}