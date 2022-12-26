package com.tom.chef.ui.comman.googleLogIn

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.tom.chef.R
import com.tom.chef.models.auth.RequestGoogleLogIn
import com.tom.chef.utils.SharedPreferenceManager

class GoogleLogInViewModel(private val registry : ActivityResultRegistry, val context: Activity,var sharedPreferenceManager: SharedPreferenceManager) :DefaultLifecycleObserver {

    lateinit var googleLoginInterface: GoogleLoginInterface

    lateinit var googleResultLauncher : ActivityResultLauncher<Intent>

    var mGoogleSignInClient: GoogleSignInClient? = null

    lateinit var owner: LifecycleOwner
    override fun onCreate(owner: LifecycleOwner) {
        this.owner=owner

        googleResultLauncher = registry.register("key", owner, ActivityResultContracts.StartActivityForResult()){
            Log.i("socailLogIn","001")
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                Log.i("socailLogIn","002")
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    RequestGoogleLogIn(name = it.displayName!!, email = it.email!!, fcm_token = sharedPreferenceManager.getFcmToken).let {
                        googleLoginInterface.onLogInCompleted(requestGoogleLogIn = it)
                    }
                }
                Log.i("socailLogIn","003")
                mGoogleSignInClient?.signOut()
            } catch (e: ApiException) {
                e.printStackTrace()
                Log.i("socailLogIn","004")
                Log.w("data", "Google sign in failed", e)
            }
        }

    }

    fun callGoogleLogIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso).also {
            Log.i("socailLogIn","005")
            googleResultLauncher.launch(it.signInIntent)
        }
    }


    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        mGoogleSignInClient?.signOut()
    }



}