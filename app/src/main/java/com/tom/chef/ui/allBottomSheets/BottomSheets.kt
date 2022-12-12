package com.tom.chef.ui.allBottomSheets

import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tom.chef.databinding.ResetPasswordInputEmailBinding
import com.tom.chef.databinding.ResetPasswordInputNewPasswordBinding
import com.tom.chef.databinding.ResetPasswordInputOtpBinding
import com.tom.chef.models.ResponseSuccess
import com.tom.chef.models.auth.*
import com.tom.chef.ui.auth.logIn.LoginActivity
import com.tom.chef.ui.allBottomSheets.otpScreen.ResetOTPInterface
import com.tom.chef.ui.allBottomSheets.otpScreen.ResetOTPViewModel
import com.tom.chef.utils.*

class BottomSheets {

    fun showInputEmail(context: LoginActivity) {
        val dialog = BottomSheetDialog(context)
        val binding= ResetPasswordInputEmailBinding.inflate(context.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.nextButton.setOnClickListener {
            if (binding.isValid()){
                val responseForgotPassword=ResponseForgotPassword(
                    message = "",
                    oData = "1111",
                    status = 1
                )
                showInputOTP(context = context, userEmail = binding.editTextTextEmailAddress.getLocalText(), resetTyp = "2", responseForgotPassword = responseForgotPassword)
                dialog.dismiss()
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
    fun ResetPasswordInputEmailBinding.isValid():Boolean{
        return Validation.checkIsAnEmail(editTextTextEmailAddress)
    }
    fun LoginActivity.callRequestResetPassword(requestResetPassword: RequestResetPassword,callBack:(ResponseForgotPassword)->Unit){
        this.startAnim()
        this.viewModel.resetRequest(requestResetPassword)
        this.viewModel.resetRequest.observe(this){
            this.stopAnim()
            if (it.status.intToBool()){
                this.myToast(it.message)
                callBack.invoke(it)
                return@observe
            }
            this.myToast(it.message)
        }
    }

    fun showInputOTP(context: LoginActivity,userEmail:String,resetTyp:String,responseForgotPassword: ResponseForgotPassword) {
        val dialog = BottomSheetDialog(context)
        val binding= ResetPasswordInputOtpBinding.inflate(context.layoutInflater)
        dialog.setContentView(binding.root)
        val resetOTPViewModel= ResetOTPViewModel(mActivity = context,userEmail = userEmail)
        binding.viewModel=resetOTPViewModel
        resetOTPViewModel.mCallback=object : ResetOTPInterface {
            override fun moveToNextScreen(postResetPasssword: PostResetPasssword) {
                showInputNewPassword(context,postResetPasssword)
                dialog.dismiss()
            }

            override fun submitClicked() {
                if (binding.getOTPTextViews().validOTP()){
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
                resetOTPViewModel.moveToNextScreen(PostResetPasssword(otp = binding.getOTPTextViews().getOTP(), password_reset_code = "1111"))

                /*
                responseForgotPassword.oData.getResetCode()?.let { reset_code->
                    resetOTPViewModel.moveToNextScreen(PostResetPasssword(otp = binding.getOTPTextViews().getOTP(), password_reset_code = reset_code))
                }*/
            }
        }
        resetOTPViewModel.setUpOTPView()
        resetOTPViewModel.startTimer()

        dialog.setCanceledOnTouchOutside(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }





    fun LoginActivity.callResendOTP(postResendForgotPasswordOTP: PostResendForgotPasswordOTP,callBack:(ResponseSuccess)->Unit){
        this.startAnim()
        this.viewModel.resendOTPForgotPassword(postResendForgotPasswordOTP)
        this.viewModel.resendOTPForgot.observe(this){
            this.stopAnim()
            if (it.status.intToBool()){
                this.myToast(it.message)
                callBack.invoke(it)
                return@observe
            }
            this.myToast(it.message)
        }
    }



    fun ResetPasswordInputOtpBinding.getOTPTextViews():List<EditText>{
        val list=ArrayList<EditText>()
        listOf(otp1,otp2,otp3,otp4).forEach {
            list.add(it.otpText)
        }
        return list;
    }
    fun showInputNewPassword(context: LoginActivity,postReset:PostResetPasssword) {
        val dialog = BottomSheetDialog(context)
        val binding= ResetPasswordInputNewPasswordBinding.inflate(context.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.nextButton.setOnClickListener {
            if (binding.isPutNewPassowrdValid()){
                postReset.password=binding.newPassword.getLocalText()
                postReset.password_confirmation=binding.confirmPassword.getLocalText()
                dialog.dismiss()
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
    fun LoginActivity.callVerifyOTPAndPassowrd(postResetPasssword: PostResetPasssword,callBack:(ResponseSuccess)->Unit){
        this.startAnim()
        this.viewModel.verifyOTPAPI(postResetPasssword)
        this.viewModel.resetPassowrdForgot.observe(this){
            this.stopAnim()
            if (it.status.intToBool()){
                this.myToast(it.message)
                callBack.invoke(it)
                return@observe
            }
            this.myToast(it.message)
        }
    }




    fun ResetPasswordInputNewPasswordBinding.isPutNewPassowrdValid():Boolean{
        val validation=Validation
        listOf(newPassword).forEach {
            if (!validation.isAValidPassword(it)){
                return false
            }
        }
        return validation.isPasswordMatch(password = newPassword,confirmPassword,isNewPassword = true)
    }




}