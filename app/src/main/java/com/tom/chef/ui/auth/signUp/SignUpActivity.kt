package com.tom.chef.ui.auth.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.tom.chef.R
import com.tom.chef.databinding.ActivitySignUpBinding
import com.tom.chef.models.auth.RequestSignUp
import com.tom.chef.models.auth.ResponseCountries
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.auth.otp.OTPActivity
import com.tom.chef.ui.allBottomSheets.DialCodeSheet
import com.tom.chef.ui.auth.termsCondition.TermsAndConditionActivity
import com.tom.chef.ui.location.LocationPickerActivity
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpActivity : BaseActivity(), SignUpInterface {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var vm: SignUpViewModel

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        init()
        vm.getCountriesList()
        binding.agreeText.makeLinks(Pair(getString(R.string.terms_conditionsClick),View.OnClickListener {
            startActivity(TermsAndConditionActivity.getIntent(this))
        }))
    }
    private fun init(){
        vm = SignUpViewModel(this,"+971")
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        binding.viewModel = vm
        vm.mCallback = this
        binding.agreeText.setMovementMethod(LinkMovementMethod.getInstance());


        val items = listOf("EN", "AR")
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, items)
        binding.layoutChangeLanguage.languageInput.setAdapter(adapter)
    }

    override fun getCountriesList() {
        val country1=ResponseCountries.OData(
            active = "1",
            created_at = "",
            deleted = 0,
            dial_code = "971",
            id = 1,
            name = "United Arab Emirates",
            prefix = "971",
            updated_at = ""
        )

        val responseCountries=ResponseCountries(
            oData = listOf(country1),
            status = 1,
            message = ""
        )

        vm.counries.value=responseCountries
        /*
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

         */
    }

    override fun showCountryCodeDialog() {
        vm.counries.value?.oData?.let {
            DialCodeSheet().dailCodeSheet(this,"Select Dail Code",it){
                vm.updateDialCode("+${it.dial_code}")
            }
        }
    }

    override fun registerClicked() {
        if (validate()){
            vm.moveToOTP()
            /*
            val requestSignUp=RequestSignUp(
                dial_code = binding.countryCode.getLocalText().removePlus(),
                password = binding.password.getLocalText(),
                email = binding.email.getLocalText(),
                first_name = binding.firstName.getLocalText(),
                last_name = binding.lastName.getLocalText(),
                phone = binding.phone.getLocalText(),
                fcm_token = sharedPreferenceManager.getFcmToken
            )
            signUpPostAPI(requestSignUp)*/
        }
    }

    override fun moveToOTP() {
        startActivity(OTPActivity.getIntent(this))
    }


    override fun moveToLogIn() {
        finish()
    }

    private fun signUpPostAPI(requestSignUp: RequestSignUp){
        startAnim()
        viewModel.signUpAPI(requestSignUp){
            stopAnim()
            if (!it.status.checkForSuccess()){
                myToast(it.message)
                return@signUpAPI
            }
            sharedPreferenceManager.saveUser(it.oData,it.accessToken)
            vm.moveToOTP()
            finishAffinity()
        }
    }


    fun validate():Boolean{
        val valid = Validation
        listOf(binding.firstName,binding.lastName,binding.email,binding.phone).forEach {
            if (valid.checkIsEmpty(it)){
                return false
            }
        }
        if (valid.checkIsEmpty(binding.countryCode)){
            myToast("Country code required")
            return false
        }
        listOf(binding.firstName,binding.lastName).forEach {
            if (!valid.isAvalidName(it)){
                return false
            }
        }
        if (!valid.checkIsAnEmail(binding.email)){
            return false
        }
        if (!valid.isAValidMobile(binding.phone)){
            return false
        }
        if (!valid.isAValidPassword(binding.password)){
            return false
        }
        if (!valid.isAgree(this,binding.agree)){
            return false
        }
        return true
    }

    override fun pickLocation() {
        getLocation.launch(LocationPickerActivity.getIntent(this))
    }
    val getLocation=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it?.let {
            it.data?.extras?.let {

            }
        }
    }
}