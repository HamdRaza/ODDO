package com.tom.chef.ui.allBottomSheets.changePassword

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tom.chef.databinding.ChangePasswordInputNewPasswordBinding
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.utils.Validation
import com.tom.chef.utils.getLocalText
import com.tom.chef.utils.intToBool
import com.tom.chef.utils.myToast

class ChangePasswordSheet {

    fun showChangePassword(baseActivity: BaseActivity) {
        val dialog = BottomSheetDialog(baseActivity)
        val binding= ChangePasswordInputNewPasswordBinding.inflate(baseActivity.layoutInflater)
        dialog.setContentView(binding.root)
        val changePasswordViewModel= ChangePasswordViewModel(baseActivity)
        changePasswordViewModel.mCallback=object : ChangePasswordInterface {
            override fun verifyInput() {
                val valid=Validation
                listOf(binding.oldPass,binding.newPass).forEach {
                    if (!valid.isAValidPassword(it)){
                        return
                    }
                }
                if (!valid.isPasswordMatch(binding.newPass,binding.newPassC,isNewPassword = true)){
                    return
                }
                changePasswordViewModel.callAPI()
            }

            override fun callAPI() {
                baseActivity.startAnim()
//                baseActivity.viewModel.changePassword(binding.oldPass.getLocalText(),binding.newPass.getLocalText())
//                baseActivity.viewModel.changePasswordLive.observe(baseActivity){
//                    baseActivity.stopAnim()
//                    baseActivity.myToast(it.message)
//                    if (it.status.intToBool()){
//                        dialog.dismiss()
//                    }
//                }
            }

            override fun closeDialog() {
                dialog.dismiss()
            }
        }
        binding.viewModel=changePasswordViewModel
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }
}