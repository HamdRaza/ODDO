package com.tom.chef.ui.allBottomSheets.otpScreen

import android.os.CountDownTimer
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.models.auth.PostResetPasssword
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.utils.getMinSec
import com.tom.chef.utils.myToast

class ResetOTPViewModel(val mActivity: BaseActivity, var userEmail: String?) : ViewModel() {

    lateinit var mCallback: ResetOTPInterface

    fun moveToNextScreen(postResetPasssword: PostResetPasssword) =
        mCallback.moveToNextScreen(postResetPasssword)

    fun submitClicked(view: View) = mCallback.submitClicked()
    fun setUpOTPView() = mCallback.setUpOTPView()
    fun validateOTP() = mCallback.validateOTP()

    @JvmField
    var heading = ObservableField<String>()

    init {
        userEmail?.let { email ->
            heading.set(
                buildString {
                    append("To reset your password, please enter the OTP that is sent to your email address ending in ")
                    append(email.subSequence(0..2))
                    append("****")
                    email.split("@").lastOrNull()?.let {
                        append("@${it}")
                    }
                }
            )
        }
    }


    @JvmField
    var resendActive = ObservableField<Boolean>()

    @JvmField
    var remainingTime = ObservableField<String>()

    init {
        resendActive.set(false)
        remainingTime.set("0:00 sec")
    }

    fun updateResend(resend: Boolean = false) {
        this.resendActive.set(resend)
    }

    fun resendOTP(view: View) {
        resendActive.get()?.let {
            if (it) {
                mCallback.resendCode()
                return
            }
        }
        mActivity.myToast("wait ${remainingTime.get()}")
    }

    val timer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            remainingTime.set(millisUntilFinished.getMinSec())
        }

        override fun onFinish() {
            val finished = 0L
            remainingTime.set(finished.getMinSec())
            updateResend(resend = true)
        }
    }

    fun startTimer() {
        updateResend(resend = false)
        timer.start()
    }

}