package com.tom.chef.ui.auth.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.tom.chef.R
import com.tom.chef.databinding.ActivityOtpBinding
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.auth.inPutDocuments.InputDocumentsActivity
import com.tom.chef.ui.dashboard.MainActivity
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OTPActivity : BaseActivity(), OTPInterface {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var vm: OTPViewModel

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, OTPActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp)
        init()
    }
    private fun init(){
        vm = OTPViewModel(this)
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        binding.viewModel = vm
        vm.mCallback = this
        vm.startTimer()
        vm.setUpOTPView()
        val items = listOf("    EN", "    AR")
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, items)
        binding.languageInput.setAdapter(adapter)
        binding.resendOtp.makeUnderLined()
    }

    override fun submitClicked() {
        if (listOfOTPViews().validOTP()){
            vm.moveToDocument()
          // vm.validateOTP()
        }
    }

    override fun validateOTP() {
        listOfOTPViews().getOTP().let { otp->
            startAnim()
            sharedPreferenceManager.getSavedUser()?.id?.toString()?.let { user_id->
                viewModel.verifyOTPAPI(user_id = user_id, otp = otp)
                viewModel.verifyOTPLiveDat.observe(this){
                    stopAnim()
                    sharedPreferenceManager.getAccessToken=it.accessToken
                    if (it.status.intToBool()){
                        vm.moveToDashboard()
                        return@observe
                    }
                    myToast(it.message)
                }
            }
        }
    }

    override fun moveToDashboard() {
        sharedPreferenceManager.isLogedIn=true
        startActivity(MainActivity.getIntent(this))
        finishAffinity()
    }

    override fun moveToDocument() {
        sharedPreferenceManager.isLogedIn=false
        startActivity(InputDocumentsActivity.getIntent(this))
        finishAffinity()
    }

    override fun resendCode() {
        startAnim()
        sharedPreferenceManager.getSavedUser()?.let {
          viewModel.resendOTPAPI(it.id.toString())
          viewModel.resendOTPLiveDat.observe(this){
              stopAnim()
              myToast(it.message)
              if (it.status.intToBool()){
                  vm.startTimer()
              }
          }
        }
    }




    fun listOfOTPViews():ArrayList<EditText>{
        val list=ArrayList<EditText>()
        listOf(binding.otpOne,binding.otpTwo,binding.otp3,binding.otp4).forEach {
            list.add(it.otpText)
        }
        return list
    }



    override fun setUpOTPView() {
       listOfOTPViews().makeCustomOTP()



    }




}