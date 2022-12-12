package com.tom.chef.ui.splash

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
import com.tom.chef.utils.animation.CallAnimation

class SplashViewModel() : ViewModel() {
    fun callAnimation(activity: Activity,view: View) {
        CallAnimation.fadeInView(activity, view)
    }
}