package com.tom.chef.newBase

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.tom.chef.utils.SharedPreferenceManager
import com.tom.chef.utils.app_loader.CustomLoaderDialog
import javax.inject.Inject

open class BaseFragment : Fragment() {

    protected var TAG = "BNFT Application"
    protected lateinit var mActivity: BaseActivity
    internal lateinit var context: Context

    private var mProgressDialog: ProgressDialog? = null
    @Inject
    lateinit var pref: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = getActivity() as BaseActivity

        mActivity.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        TAG = javaClass.simpleName
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    open fun startAnim() {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = CustomLoaderDialog.createProgressDialog(context, true)
            } else {
                mProgressDialog?.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun stopAnim() {
        if (mProgressDialog != null) {
            try {
                mProgressDialog?.dismiss()
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

}