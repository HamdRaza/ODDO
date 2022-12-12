package com.tom.chef.ui.auth.phoneNumber

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.tom.chef.R
import com.tom.chef.databinding.ActivityPhoneNumberBinding
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.auth.otp.OTPActivity
import com.tom.chef.ui.allBottomSheets.DialCodeSheet
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhoneNumberActivity : BaseActivity(), PhoneNumberInterface {
    private lateinit var binding: ActivityPhoneNumberBinding
    private lateinit var vm: PhoneNumberViewModel

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, PhoneNumberActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_number)
        init()
        vm.getCountriesList()
    }
    private fun init(){
        vm = PhoneNumberViewModel(this,"+971")
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        binding.viewModel = vm
        vm.mCallback = this
    }

    override fun getCountriesList() {
        startAnim()
        viewModel.getAllCountries()
        viewModel.countries.observe(this){
            stopAnim()
            Log.i("codeCheck","004")
            if(it.status.intToBool()){
                Log.i("codeCheck","005")
                vm.counries.value=it
            }
        }
    }

    override fun showCountryCodeDialog() {
        Log.i("codeCheck","001")
        vm.counries.value?.oData?.let {
            Log.i("codeCheck","002")
            DialCodeSheet().dailCodeSheet(this,"Select Dail Code",it){
                vm.updateDialCode("+${it.dial_code}")
            }
        }
    }

    override fun callAPI(dialCode: String, phone: String) {
        startAnim()
        stopAnim()
        vm.moveToOTP()
        /*
        viewModel.changePhoneNumber(dialCode = dialCode, phone = phone){
            stopAnim()
            myToast(it.message)
            if (it.status.intToBool()){
                vm.moveToOTP()
            }
        }
         */
    }


    override fun verifyInput() {
        val valid = Validation
        listOf(binding.phone).forEach {
            if (valid.checkIsEmpty(it)){
                return
            }
        }
        if (valid.checkIsEmpty(binding.countryCode)){
            myToast("Country code required")
            return
        }
        if (!valid.isAValidMobile(binding.phone)){
            return
        }

        vm.callAPI(dialCode = binding.countryCode.getLocalText(), phone = binding.phone.getLocalText())
    }


    override fun moveToOTP() {
        startActivity(OTPActivity.getIntent(this))
    }

}