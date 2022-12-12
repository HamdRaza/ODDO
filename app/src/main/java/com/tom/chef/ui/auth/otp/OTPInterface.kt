package com.tom.chef.ui.auth.otp

interface OTPInterface {
   fun moveToDashboard()
   fun submitClicked()
   fun resendCode()
   fun setUpOTPView()
   fun validateOTP()
   fun moveToDocument()
}