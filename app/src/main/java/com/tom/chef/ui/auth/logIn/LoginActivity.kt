package com.tom.chef.ui.auth.logIn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.messaging.FirebaseMessaging
import com.tom.chef.R
import com.tom.chef.databinding.ActivityLoginBinding
import com.tom.chef.models.auth.RequestGoogleLogIn
import com.tom.chef.models.auth.RequestLogIn
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.auth.otp.OTPActivity
import com.tom.chef.ui.allBottomSheets.BottomSheets
import com.tom.chef.ui.auth.phoneNumber.PhoneNumberActivity
import com.tom.chef.ui.auth.signUp.SignUpActivity
import com.tom.chef.ui.dashboard.MainActivity
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : BaseActivity(), LoginInterface{
    private lateinit var binding: ActivityLoginBinding
    private lateinit var vm: LoginViewModel

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        vm = LoginViewModel(this)
        binding.viewModel = vm
        vm.mCallback = this
        vm.init()

    }
    override fun init() {



    }

    override fun logInClicked() {
        if (validate()){
            vm.moveToOTP()
           // loginPostAPI(email = binding.editTextTextEmailAddress.getLocalText(), password = binding.editTextTextPassword.getLocalText(), fcm_token = sharedPreferenceManager.getFcmToken)
        }
    }
    override fun registerClicked() {
        startActivity(SignUpActivity.getIntent(this@LoginActivity))
    }

    override fun onResetClicked() {
         vm.showResetInputEmail()
    }

    override fun showResetInputEmail() {
        BottomSheets().showInputEmail(this)
    }


    fun validate():Boolean{
        val valid = Validation
        listOf(binding.editTextTextEmailAddress).forEach {
            if (valid.checkIsEmpty(it)){
                return false
            }
        }
        listOf(binding.editTextTextEmailAddress).forEach {
            if (!valid.checkIsAnEmail(it)){
                return false
            }
        }
        if (!valid.isAValidPassword(binding.editTextTextPassword)){
            return false
        }
        return true
    }

    override fun loadFcmToken() {
        FirebaseMessaging.getInstance().subscribeToTopic("Android").addOnSuccessListener {}
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.i("Token",it.toString())
            it?.let {
                sharedPreferenceManager.getFcmToken=it
            }
        }
    }

    private fun loginPostAPI(email: String, password: String,fcm_token:String){
        startAnim()
        viewModel.loginAPI(RequestLogIn( email = email, password = password, fcm_token = fcm_token)){
            stopAnim()
            if (!it.status.checkForSuccess()){
                myToast(it.message)
                return@loginAPI
            }
            sharedPreferenceManager.saveUser(it.oData,it.accessToken)
            vm.moveToDashboard()

            /* Move to OTP get check
            it.oData?.let {user->
                sharedPreferenceManager.saveUser(user = user,it.accessToken)
                vm.moveToOTP()
                return@let
            }
              */
        }
    }

    override fun moveToOTP() {
        startActivity(OTPActivity.getIntent(this@LoginActivity))
    }

    override fun moveToDashboard() {
        sharedPreferenceManager.isLogedIn=true
        startActivity(MainActivity.getIntent(this@LoginActivity))
        finishAffinity()
    }

    var mGoogleSignInClient: GoogleSignInClient? = null
    override fun callGoogleLogIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso).also {
            googleResultLauncher.launch(it.signInIntent)
        }
    }

    private val googleResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let {
                RequestGoogleLogIn(name = it.displayName!!, email = it.email!!, fcm_token = sharedPreferenceManager.getFcmToken).let {
                    vm.moveToOTP()
                //  vm.callGoogleLogInAPI(it)
                }
            }
            mGoogleSignInClient?.signOut()
        } catch (e: ApiException) {
            Log.w("data", "Google sign in failed", e)
        }
    }

    override fun callGoogleLogInAPI(requestGoogleLogIn: RequestGoogleLogIn) {
        startAnim()
        viewModel.googleLogIn(requestGoogleLogIn)
        viewModel.googleLogIn.observe(this){
            stopAnim()
            if (it.status.intToBool()){
                sharedPreferenceManager.saveUser(it.user,it.access_token)
                /* Phone verified missing
                it.user?.let {
                    if (it.phone_verified.checkForSuccess()){
                        vm.moveToDashboard()
                        return@observe
                    }else{
                        vm.moveToAddPhone()
                        return@observe
                    }
                }*/
                vm.moveToDashboard()
                return@observe
            }
            myToast(it.message)
        }
    }

    override fun moveToAddPhone() {
        startActivity(PhoneNumberActivity.getIntent(this))
    }

    override fun callFaceBook() {
        startActivity(MainActivity.getIntent(this))
        finishAffinity()
    }

}