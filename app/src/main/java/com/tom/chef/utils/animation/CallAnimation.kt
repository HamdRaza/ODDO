package com.tom.chef.utils.animation

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import com.tom.chef.R
import com.tom.chef.newBase.BaseActivity

class CallAnimation {
    companion object {
        // FADE IN ANIMATION
        fun fadeInView(mActivity: BaseActivity, view: View){
            val animation = AnimationUtils.loadAnimation(mActivity, R.anim.fadein)
            //starting the animation
            view.startAnimation(animation)
        }
        // FADE IN ANIMATION
        fun fadeInView(mActivity: Activity, view: View){
            val animation = AnimationUtils.loadAnimation(mActivity, R.anim.fadein)
            //starting the animation
            view.startAnimation(animation)
        }
    }
}