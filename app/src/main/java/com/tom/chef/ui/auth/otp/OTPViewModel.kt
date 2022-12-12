package com.tom.chef.ui.auth.otp

import android.os.CountDownTimer
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.utils.getMinSec
import com.tom.chef.utils.myToast

class OTPViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var mCallback: OTPInterface

    fun moveToDashboard()=mCallback.moveToDashboard()
    fun submitClicked(view: View)=mCallback.submitClicked()
    fun setUpOTPView()=mCallback.setUpOTPView()
    fun validateOTP()=mCallback.validateOTP()
    fun moveToDocument()=mCallback.moveToDocument()

    @JvmField
    var resendActive=ObservableField<Boolean>()

    @JvmField
    var remainingTime=ObservableField<String>()

    init {
        resendActive.set(false)
        remainingTime.set("0:00 sec")
    }
    fun updateResend(resend:Boolean=false){
        this.resendActive.set(resend)
    }

    fun resendOTP(view: View){
        resendActive.get()?.let {
              if (it){
                  mCallback.resendCode()
                  return
              }
        }
        mActivity.myToast("wait ${remainingTime.get()}")
    }

    val timer = object: CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            remainingTime.set(millisUntilFinished.getMinSec())
        }

        override fun onFinish() {
            val finished=0L
            remainingTime.set(finished.getMinSec())
            updateResend(resend = true)
        }
    }

    fun startTimer(){
        updateResend(resend = false)
        timer.start()
    }

}