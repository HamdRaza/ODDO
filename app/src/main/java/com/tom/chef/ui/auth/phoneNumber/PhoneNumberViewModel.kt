package com.tom.chef.ui.auth.phoneNumber

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.chef.models.auth.ResponseCountries
import com.tom.chef.newBase.BaseActivity

class PhoneNumberViewModel(val mActivity: BaseActivity, val dial_code:String) : ViewModel() {

    lateinit var mCallback: PhoneNumberInterface
    fun verifyInput(view: View)=mCallback.verifyInput()
    fun callAPI(dialCode:String,phone:String)=mCallback.callAPI(dialCode = dialCode, phone = phone)
    fun moveToOTP()=mCallback.moveToOTP()
    fun getCountriesList()=mCallback.getCountriesList()
    fun showCountryCodeDialog(view: View)=mCallback.showCountryCodeDialog()
    var counries    = MutableLiveData<ResponseCountries>()
    @JvmField
    var dialCode=ObservableField<String>()
    init {
        dialCode.set(dial_code)
    }
    fun updateDialCode(dial_code: String){
        dialCode.set(dial_code)
    }

}