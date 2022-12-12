package com.tom.chef.ui.allBottomSheets.otpScreen

import com.tom.chef.models.auth.PostResetPasssword

interface ResetOTPInterface {
   fun moveToNextScreen(postResetPasssword: PostResetPasssword)
   fun submitClicked()
   fun resendCode()
   fun setUpOTPView()
   fun validateOTP()
}