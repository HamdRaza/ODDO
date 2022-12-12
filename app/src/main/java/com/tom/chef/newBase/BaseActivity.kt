package com.tom.chef.newBase

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.transition.Transition
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.tom.chef.R
import com.tom.chef.app.SingleLiveEvent
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.ui.dashboard.fragments.account.ProfileFragment
import com.tom.chef.ui.dashboard.fragments.home.HomeFragment
import com.tom.chef.ui.dashboard.fragments.notification.NotificationFragment
import com.tom.chef.ui.dashboard.toolBar.ToolBarViewModel
import com.tom.chef.utils.*
import com.tom.chef.utils.app_loader.CustomLoaderDialog
import com.stripe.android.PaymentConfiguration
import com.stripe.android.googlepaylauncher.GooglePayEnvironment
import com.stripe.android.googlepaylauncher.GooglePayLauncher
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult


open class BaseActivity : AppCompatActivity() {
    private var mProgressDialog: ProgressDialog? = null
    val viewModel: AppViewModel by viewModels()
    // ToolBar Layout
    lateinit var toolbarVM: ToolBarViewModel

    lateinit var localStore:SharedPreferenceManager

    lateinit var paymentSheet: PaymentSheet

    lateinit var connectionLiveData: ConnectionLiveData
    lateinit var commonDialog : Dialog


    var StripPublicKey: String = "pk_test_51KdqxdBjsMxFtgBeSKmSXVjwG6yqKIUT89jWGFrZcON2gxqhtfhH6EFSHYVdrqPAU4UxEsIlAUEhnmPAlkvxMkzK0009RlNxWJ"

    private lateinit var googlePayLauncher:GooglePayLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PaymentConfiguration.init(this, StripPublicKey)

         googlePayLauncher = GooglePayLauncher(
            activity = this,
            config = GooglePayLauncher.Config(
                environment = GooglePayEnvironment.Test,
                merchantCountryCode = "US",
                merchantName = "Widget Store",
                existingPaymentMethodRequired = false
            ),
            readyCallback = ::onGooglePayReady,
            resultCallback = ::onGooglePayResult
        )

        paymentSheet = PaymentSheet(this) { result -> onPaymentSheetResult(result) }
        // Google API key manage
        /*val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["com.google.android.geo.API_KEY"]
        apiKey = value.toString()*/


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
            mProgressDialog = CustomLoaderDialog.createProgressDialog(this, false)
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

    fun removeFragments(no: Int) {
        try {
            val fragmentManager = supportFragmentManager
            fragmentManager.popBackStack(
                fragmentManager.getBackStackEntryAt(
                    fragmentManager.backStackEntryCount - no
                ).id, FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearAllStack() {
        val fm = supportFragmentManager
        for (i in 0 until fm.getBackStackEntryCount()) {
            fm.popBackStack()
        }
    }
    fun clearAllStackInstedOfDashBoard() {
        val fm = supportFragmentManager
        for (i in 1 until fm.getBackStackEntryCount()) {
            fm.popBackStack()
        }
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

    fun replaceFragment(fragment: Fragment){
        replaceFragment(fragment, false, true, false)
    }

    fun replaceFragment(fragment: Fragment, isAdd: Boolean){
        replaceFragment(fragment, isAdd, true, false)
    }

    fun replaceFragment(fragment: Fragment, isAdd: Boolean,addtobs: Boolean){
        replaceFragment(fragment, isAdd, addtobs, false)
    }

    fun replaceFragment(fragment: Fragment, isAdd: Boolean, addtobs: Boolean, forceWithoutAnimation: Boolean){
        replaceFragment(fragment, isAdd, addtobs, forceWithoutAnimation, null)
    }

    fun replaceFragment(
        fragment: Fragment,
        isAdd: Boolean,
        addtobs: Boolean,
        forceWithoutAnimation: Boolean,
        transition: Transition?
    ) {
        Thread {
            if (!isLastStackedFragment(fragment)) {
                if (fragment is HomeFragment ) {
                    val fm: FragmentManager = supportFragmentManager
                    for (i in 0 until fm.backStackEntryCount) {
                        fm.popBackStack()
                    }
                }
                val ft =  supportFragmentManager.beginTransaction()
                val tag = fragment.javaClass.name.toString()
                ft.setReorderingAllowed(true)
                if (!forceWithoutAnimation) {
                    if (AndroidUtility.isAndroid5()) {
                        if (transition != null) {
                            fragment.enterTransition = transition
                        } else {
                            fragment.enterTransition = TransitionUtil().slide(Gravity.RIGHT)
                        }
                    } else {
                        ft.setCustomAnimations(
                            R.anim.pull_in_right,
                            R.anim.push_out_left,
                            R.anim.pull_in_left,
                            R.anim.push_out_right
                        )
                    }
                } else {
                    ft.setCustomAnimations(
                        R.anim.from_down,
                        R.anim.from_up,
                        R.anim.from_down,
                        R.anim.from_up
                    )
                }
                if (isAdd){
                    ft.add(R.id.fragment_view, fragment, tag)
                } else {
                    ft.replace(R.id.fragment_view, fragment, tag)
                }
                if (addtobs){
                    ft.addToBackStack(tag)
                }
                ft.commit()

            }
            try {
                runOnUiThread {
                    YoYo.with(Techniques.SlideInRight)
                        .duration(700)
                        .repeat(0)
                        .playOn(findViewById<View>(R.id.fragment_view))
                }
            } catch (e:Exception){
                e.printStackTrace()
            }
        }.start()
    }


    fun isLastStackedFragment(fragment: Fragment): Boolean {
        var status = false
        try {
            val index: Int = supportFragmentManager.backStackEntryCount - 1
            val tag: String =
                supportFragmentManager.getBackStackEntryAt(index).name.toString()
            val frg: Fragment? = supportFragmentManager.findFragmentByTag(tag)
            if (frg?.tag.toString() == fragment.javaClass.name.toString()) {
                status = true
            }
        } catch (e: Exception) {
            Log.e("isLastStackedFragment: ", e.toString())
        }
        return status
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let {
            val  imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }
        return super.dispatchTouchEvent(ev)
    }
  fun  hideSoftKeyboard(){
        currentFocus?.let {
            val  imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }
    }







    val getLocation=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it?.let {
            it.data?.extras?.let {

            }
        }
    }

    val startLoginActivity=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it?.let {
            it.data?.extras?.let {

            }
        }
    }


    private var _stripPayment = SingleLiveEvent<UiState<Boolean>>()
    val stripPayment: SingleLiveEvent<UiState<Boolean>>
        get() = _stripPayment




    fun presentPaymentSheet(paymentRef:String,type:String) {
        Log.i("paymentValue","asd  =  $paymentRef     =  $type")
        PaymentConfiguration.init(this, StripPublicKey)

        _stripPayment.value = UiState.Loading

        when(type){
            "2"->{
                paymentSheet.presentWithPaymentIntent(paymentRef,PaymentSheet.Configuration(getString(R.string.app_name)))
            }
            "4"->{
                googlePayLauncher.presentForPaymentIntent(paymentRef)
            }
            else->{}
        }

    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult){
            is PaymentSheetResult.Canceled->{
                _stripPayment.value=UiState.Failure("Payment Canceled")
                myToast("Payment Canceled")
            }
            is PaymentSheetResult.Failed->{
                _stripPayment.value=UiState.Failure(paymentSheetResult.error.message)
                paymentSheetResult.error.message?.let {
                    myToast(it)
                    return
                }
                myToast("Payment Failed")
            }
            is PaymentSheetResult.Completed->{
                _stripPayment.value=UiState.Success(true)
            }
            else->{
                myToast("Payment Canceled")
            }
        }
    }







    fun dashboardVisible(fragmentId:Int):Boolean{
        supportFragmentManager.findFragmentById(fragmentId)?.let { fragment->
            when(fragment){
                is HomeFragment->{
                    return true
                }
                else->{
                    return false
                }
            }

        }
        return false
    }




    fun showYesNoButton(){

    }

    private fun onGooglePayReady(isReady: Boolean) {
        // implemented below
    }

    private fun onGooglePayResult(result: GooglePayLauncher.Result) {
        when (result) {
            GooglePayLauncher.Result.Completed -> {
                _stripPayment.value=UiState.Success(true)
            }
            GooglePayLauncher.Result.Canceled -> {
                _stripPayment.value=UiState.Failure("Payment Canceled")
                myToast("Payment Canceled")
            }
            is GooglePayLauncher.Result.Failed -> {
                _stripPayment.value=UiState.Failure(result.error.message)
                result.error.message?.let {
                    myToast(it)
                    return
                }
                myToast("Payment Failed")
            }
        }
    }

}
