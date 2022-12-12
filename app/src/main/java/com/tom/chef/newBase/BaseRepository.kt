package com.tom.chef.newBase

import android.app.ProgressDialog
import android.content.Context
import com.tom.chef.utils.SharedPreferenceManager
import com.tom.chef.utils.app_loader.CustomLoaderDialog
import com.tom.chef.utils.handler.ExceptionHelper
import javax.inject.Inject

abstract class BaseRepository {

    @Inject
    lateinit var pref: SharedPreferenceManager
    private var mProgressDialog: ProgressDialog? = null


    open fun startAnim(context: Context) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = CustomLoaderDialog.createProgressDialog(context, false)
            } else {
                mProgressDialog!!.show()
            }
        } catch (e: Exception) {
            ExceptionHelper.printStackTrace(e)
        }
    }

    open fun stopAnim() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }
}