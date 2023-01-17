package com.tom.chef.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.tom.chef.ui.auth.logIn.LoginActivity
import com.tom.chef.ui.dashboard.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Utils {
    companion object {
        fun userLogout(activity: MainActivity) {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(activity, "Your session is expired", Toast.LENGTH_SHORT).show()
            }
            activity.sharedPreferenceManager.isLogedIn = false
            activity.sharedPreferenceManager.clear()
            activity.startActivity(Intent(activity, LoginActivity::class.java))
            activity.finish()

        }
    }

}