package com.tom.chef.ui.auth.logIn

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.tom.chef.R
import com.tom.chef.databinding.ActivityLoginBinding
import com.tom.chef.models.auth.LogInRequest
import com.tom.chef.network.NetworkUtils
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.webview.WebViewActivity
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationServiceConfiguration
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : BaseActivity(), LoginInterface {
    private lateinit var binding: ActivityLoginBinding
    val vm: LoginViewModel by viewModels()

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
        binding.viewModel = vm
        vm.mCallback = this
        vm.init()

    }

    override fun init() {

    }

    override fun logInClicked() {
        if (validate()) {
            loginPostAPI(
                email = binding.editTextTextEmailAddress.getLocalText(),
                password = binding.editTextTextPassword.getLocalText(),
            )
        }
    }



    private fun validate(): Boolean {
        val valid = Validation
        listOf(binding.editTextTextEmailAddress,binding.editTextTextPassword).forEach {
            if (valid.checkIsEmpty(it)) {
                return false
            }
        }
        return true
    }
    private fun loginPostAPI(email: String, password: String) {
        hideSoftKeyboard(activity = this)
        val logInRequest=LogInRequest(params = LogInRequest.Params(login = email, password = password))
        startAnim()
        viewModel.loginAPI(logInRequest) {
            stopAnim()
            it.error?.let {
                myToast(message = it.message)
                return@loginAPI
            }
            sharedPreferenceManager.saveUser(it.result)
            myToast("Login Successful")
            vm.moveToDashboard()
        }
    }


    override fun moveToDashboard() {
        sharedPreferenceManager.isLogedIn = true
        startActivity(WebViewActivity.getIntent(context = this, urlToShow = NetworkUtils.PageURL))
    }

    override fun onStart() {
        super.onStart()
        handleAuthenToken()
    }
    private fun handleAuthenToken() {
        viewModel.tokenValidation.observe(this) {
            it?.let {
                when (it) {
                    "500" -> {
                        myToast("Internal Server Error")
                        stopAnim()
                    }
                    else -> {
                        myToast("Error code it")
                    }
                }
                Log.i("errorCode", it)
            }
        }
    }

    override fun keyClockSetUP() {
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://idp.example.com/auth"),  // authorization endpoint
            Uri.parse("https://idp.example.com/token")
        ) // token endpoint
    }

    override fun keyClockLogIn() {

    }
}