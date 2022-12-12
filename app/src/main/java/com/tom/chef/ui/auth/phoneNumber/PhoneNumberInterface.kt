package com.tom.chef.ui.auth.phoneNumber

interface PhoneNumberInterface {
    fun verifyInput()
    fun callAPI(dialCode:String,phone:String)
    fun moveToOTP()
    fun getCountriesList()
    fun showCountryCodeDialog()
}