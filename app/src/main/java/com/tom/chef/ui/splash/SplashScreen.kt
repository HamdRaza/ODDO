package com.tom.chef.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tom.chef.network.NetworkUtils
import com.tom.chef.ui.auth.logIn.LoginActivity
import com.tom.chef.ui.webview.WebViewActivity
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen=installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        callToNextActivity()
    }


   private fun callToNextActivity(){
        Handler(mainLooper).postDelayed({
            if (sharedPreferenceManager.isLogedIn){
                startActivity(WebViewActivity.getIntent(context = this, urlToShow = NetworkUtils.BASE_URL))
            }else{
                startActivity(LoginActivity.getIntent(this))
            }
            finishAffinity()
        }, Constants.SPLASH_DISPLAY_LENGTH)
    }



}