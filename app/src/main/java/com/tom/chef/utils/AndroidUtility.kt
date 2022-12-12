package com.tom.chef.utils

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.tom.chef.R
import com.tom.chef.newBase.BaseActivity

class AndroidUtility {
    companion object {
        fun isAndroid5(): Boolean {
            return true
        }

        fun setImageTintUnCheck(view: ImageView, mActivity: BaseActivity){
            ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(
                ContextCompat.getColor(mActivity, R.color.white)))
        }
        fun textColorWhite(textView: TextView, mActivity: BaseActivity) {
            mActivity.resources?.getColor(R.color.white)?.let {
                textView.setTextColor(it)
            }
        }
    }
}