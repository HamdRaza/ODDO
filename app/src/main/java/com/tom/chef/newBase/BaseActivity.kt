package com.tom.chef.newBase

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.utils.*
import com.tom.chef.utils.app_loader.CustomLoaderDialog


open class BaseActivity : AppCompatActivity() {
    private var mProgressDialog: ProgressDialog? = null
    val viewModel: AppViewModel by viewModels()
    // ToolBar Layout


    lateinit var connectionLiveData: ConnectionLiveData
    lateinit var commonDialog : Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commonDialog = showNoConnectionDialog(this)
        connectionLiveData = ConnectionLiveData(this)
        netWorkCheck()
        val mWindow = window
        mWindow.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

    }

    fun netWorkCheck() {
        connectionLiveData.observe(this) { isConnected ->
            isConnected?.let {
                showMessage(isConnected)
            }
        }
    }
    private fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            if (!commonDialog.isShowing){
                commonDialog.show()
            }
        } else {
            if (commonDialog.isShowing){
                commonDialog.dismiss()
            }
        }
    }

    fun showNoConnectionDialog(context: Context): AlertDialog {
        return AlertDialog.Builder(context).setMessage(
            "No internet"
        ).setCancelable(false).setNeutralButton("Check connection")
        { _, _ ->
            try {
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }.create()
    }


    open fun createProgressDialog(){
        mProgressDialog = CustomLoaderDialog.createProgressDialog(this, false)
    }

    open fun startAnim() {
        if (mProgressDialog == null) {
            mProgressDialog = CustomLoaderDialog.createProgressDialog(this, true)
        } else {
            mProgressDialog?.show()
        }
    }

    open fun stopAnim() {
        if (mProgressDialog != null) {
            mProgressDialog?.dismiss()
        }
    }

    open fun getCurrentFragment(): BaseFragment? {
        return supportFragmentManager.findFragmentById(android.R.id.content) as BaseFragment
    }





    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
  fun  hideSoftKeyboard(){
        currentFocus?.let {
            val  imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let {
            val  imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
