package com.tom.chef.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tom.chef.ui.auth.logIn.LoginActivity
import com.tom.chef.ui.dashboard.MainActivity
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


    fun callToNextActivity(){
        Handler(mainLooper).postDelayed({
            sharedPreferenceManager.isLogedIn=false
            if (sharedPreferenceManager.isLogedIn){
                startActivity(MainActivity.getIntent(this).putExtras(intent.extras.handleEmpty()))
            }else{
                startActivity(LoginActivity.getIntent(this))
            }
            finishAffinity()
        }, Constants.SPLASH_DISPLAY_LENGTH)
    }



}