package com.tom.chef.ui.allBottomSheets.changePassword

import android.view.View
import androidx.lifecycle.ViewModel
import com.tom.chef.newBase.BaseActivity

class ChangePasswordViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var mCallback: ChangePasswordInterface

    fun verifyInput(view: View)=mCallback.verifyInput()
    fun closeDialog(view: View)=mCallback.closeDialog()
    fun callAPI()=mCallback.callAPI()

}