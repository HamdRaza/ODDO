package com.tom.chef.ui.allBottomSheets

import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tom.chef.databinding.ResetPasswordInputEmailBinding
import com.tom.chef.databinding.ResetPasswordInputNewPasswordBinding
import com.tom.chef.databinding.ResetPasswordInputOtpBinding
import com.tom.chef.models.ResponseSuccess
import com.tom.chef.models.auth.*
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.ui.auth.logIn.LoginActivity
import com.tom.chef.ui.allBottomSheets.otpScreen.ResetOTPInterface
import com.tom.chef.ui.allBottomSheets.otpScreen.ResetOTPViewModel
import com.tom.chef.utils.*

class BottomSheets {

    lateinit var appViewModel: AppViewModel
    var email = ""
    var access_token = ""

    fun showInputEmail(loginActivity: LoginActivity, appViewModel: AppViewModel) {
        this.appViewModel = appViewModel
        val dialog = BottomSheetDialog(loginActivity)
        val binding = ResetPasswordInputEmailBinding.inflate(loginActivity.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.nextButton.setOnClickListener {
            if (binding.isValid()) {
                loginActivity.startAnim()
                email = binding.etEmailAddress.getLocalText()
                appViewModel.forgotPasswordAPI(email)
                appViewModel.forgotPasswordAPILive.observe(loginActivity) {
                    if (it.status == "1") {
                        loginActivity.stopAnim()
                        showInputOTP(
                            loginActivity = loginActivity,
                            userEmail = binding.etEmailAddress.getLocalText()
                        )
                        dialog.dismiss()
                    } else {
                        Toast.makeText(loginActivity, "Invalid Email Address", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                /*
                context.callRequestResetPassword(RequestResetPassword(email = binding.editTextTextEmailAddress.getLocalText(), type = "2")){
                    showInputOTP(context = context, userEmail = binding.editTextTextEmailAddress.getLocalText(), resetTyp = "2", responseForgotPassword = it)
                    dialog.dismiss()
                }
                 */
            }

        }

        dialog.show()
    }

    fun ResetPasswordInputEmailBinding.isValid(): Boolean {
        return Validation.checkIsAnEmail(etEmailAddress)
    }

    fun LoginActivity.callRequestResetPassword(
        requestResetPassword: RequestResetPassword,
        callBack: (ResponseForgotPassword) -> Unit
    ) {
        this.startAnim()
        this.viewModel.resetRequest(requestResetPassword)
        this.viewModel.resetRequest.observe(this) {
            this.stopAnim()
            if (it.status.intToBool()) {
                this.myToast(it.message)
                callBack.invoke(it)
                return@observe
            }
            this.myToast(it.message)
        }
    }

    fun showInputOTP(
        loginActivity: LoginActivity,
        userEmail: String,
    ) {
        val dialog = BottomSheetDialog(loginActivity)
        val binding = ResetPasswordInputOtpBinding.inflate(loginActivity.layoutInflater)
        dialog.setContentView(binding.root)
        val resetOTPViewModel = ResetOTPViewModel(mActivity = loginActivity, userEmail = userEmail)
        binding.viewModel = resetOTPViewModel
        resetOTPViewModel.mCallback = object : ResetOTPInterface {
            override fun moveToNextScreen(postResetPasssword: PostResetPasssword) {
                showInputNewPassword(loginActivity, postResetPasssword)
                dialog.dismiss()
            }

            override fun submitClicked() {
                if (binding.getOTPTextViews().validOTP()) {
                    resetOTPViewModel.validateOTP()
                }
            }

            override fun resendCode() {
                /*
               responseForgotPassword.oData.getResetCode()?.let { reset_code->
                   context.callResendOTP(PostResendForgotPasswordOTP(password_reset_code = reset_code, type = resetTyp)){
                       resetOTPViewModel.startTimer()
                   }
               }
                 */
            }

            override fun setUpOTPView() {
                binding.getOTPTextViews().makeCustomOTP()
            }

            override fun validateOTP() {
                loginActivity.startAnim()

                appViewModel.forgotPasswordOtpVerify(email, binding.getOTPTextViews().getOTP())
                appViewModel.forgotPasswordOtpVerifyLive.observe(loginActivity) {
                    if (it.status == "1") {
                        loginActivity.stopAnim()
                        access_token = it.accessToken
                        resetOTPViewModel.moveToNextScreen(
                            PostResetPasssword(
                                otp = binding.getOTPTextViews().getOTP(),
                                password_reset_code = "1111"
                            )
                        )
                    } else {
                        Toast.makeText(loginActivity, "Invalid Otp", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                /*
                responseForgotPassword.oData.getResetCode()?.let { reset_code->
                    resetOTPViewModel.moveToNextScreen(PostResetPasssword(otp = binding.getOTPTextViews().getOTP(), password_reset_code = reset_code))
                }*/
            }
        }
        resetOTPViewModel.setUpOTPView()
        resetOTPViewModel.startTimer()
        binding.resendOtp.makeUnderLined()
        dialog.setCanceledOnTouchOutside(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    fun LoginActivity.callResendOTP(
        postResendForgotPasswordOTP: PostResendForgotPasswordOTP,
        callBack: (ResponseSuccess) -> Unit
    ) {
        this.startAnim()
        this.viewModel.resendOTPForgotPassword(postResendForgotPasswordOTP)
        this.viewModel.resendOTPForgot.observe(this) {
            this.stopAnim()
            if (it.status.intToBool()) {
                this.myToast(it.message)
                callBack.invoke(it)
                return@observe
            }
            this.myToast(it.message)
        }
    }


    fun ResetPasswordInputOtpBinding.getOTPTextViews(): List<EditText> {
        val list = ArrayList<EditText>()
        listOf(otp1, otp2, otp3, otp4).forEach {
            list.add(it.otpText)
        }
        return list;
    }

    fun showInputNewPassword(loginActivity: LoginActivity, postReset: PostResetPasssword) {
        val dialog = BottomSheetDialog(loginActivity)
        val binding = ResetPasswordInputNewPasswordBinding.inflate(loginActivity.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.nextButton.setOnClickListener {
            if (binding.isPutNewPasswordValid()) {
                loginActivity.startAnim()

                appViewModel.resetPasswordAPI(
                    access_token,
                    binding.newPassword.getLocalText(),
                    binding.confirmPassword.getLocalText()
                )
                appViewModel.resetPasswordAPILive.observe(loginActivity) {
                    if (it.status == "1") {
                        loginActivity.stopAnim()
                        Toast.makeText(loginActivity, "Password updated", Toast.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(loginActivity, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
//                postReset.password = binding.newPassword.getLocalText()
//                postReset.password_confirmation = binding.confirmPassword.getLocalText()
//
//                dialog.dismiss()
                /*
                context.callVerifyOTPAndPassowrd(postResetPasssword = postReset){
                    if (it.status.intToBool()){
                        dialog.dismiss()
                    }
                }
                 */
            }
        }
        dialog.show()
    }

    fun LoginActivity.callVerifyOTPAndPassowrd(
        postResetPasssword: PostResetPasssword,
        callBack: (ResponseSuccess) -> Unit
    ) {
        this.startAnim()
        this.viewModel.verifyOTPAPI(postResetPasssword)
        this.viewModel.resetPassowrdForgot.observe(this) {
            this.stopAnim()
            if (it.status.intToBool()) {
                this.myToast(it.message)
                callBack.invoke(it)
                return@observe
            }
            this.myToast(it.message)
        }
    }


    fun ResetPasswordInputNewPasswordBinding.isPutNewPasswordValid(): Boolean {
        val validation = Validation
        listOf(newPassword).forEach {
            if (!validation.isAValidPassword(it)) {
                return false
            }
        }
        return validation.isPasswordMatch(
            password = newPassword,
            confirmPassword,
            isNewPassword = true
        )
    }


}