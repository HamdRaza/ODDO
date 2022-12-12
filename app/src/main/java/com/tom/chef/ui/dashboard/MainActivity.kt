package com.tom.chef.ui.dashboard

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.tom.chef.R
import com.tom.chef.databinding.ActivityMainBinding
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.auth.logIn.LoginActivity
import com.tom.chef.ui.dashboard.fragments.account.AccountInterface
import com.tom.chef.ui.dashboard.fragments.account.AccountViewModel
import com.tom.chef.ui.dashboard.fragments.account.ProfileFragment
import com.tom.chef.ui.dashboard.fragments.home.HomeFragment
import com.tom.chef.ui.dashboard.fragments.notification.NotificationFragment
import com.tom.chef.ui.dashboard.toolBar.ToolBarInterface
import com.tom.chef.ui.dashboard.toolBar.ToolBarViewModel
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity(),ToolBarInterface,MainInterface,AccountInterface{
    lateinit var binding: ActivityMainBinding
    val vm: MainViewModel by viewModels()



    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        sharedPreferenceManager.isLogedIn=true
        localStore=sharedPreferenceManager
        init()
        handleAuthenToken()
        onHomeClicked()
    }

    private fun handleAuthenToken() {
        viewModel.tokenValidation.observe(this){
            it?.let {
                when(it){
                    "401"->{
                        stopAnim()
                        myToast("Invalid login")
                        sharedPreferenceManager.isLogedIn=false
                        sharedPreferenceManager.getAccessToken="getAccessToken"
                    }
                    "500"->{
                        myToast("Internal Server Error")
                        stopAnim()
                    }
                    "1005"->{
                        stopAnim()
                        myToast("Please login")
                        vm.moveToLogIn()
                    }
                }
                Log.i("errorCode",it)
            }
        }
    }

    private fun init() {
        toolbarVM = ToolBarViewModel(this)
        binding.toolbar.toolbarViewModel=toolbarVM
        toolbarVM.toolBarInterface=this

        val accountViewModel=AccountViewModel(this)
        accountViewModel.accountInterface=this
        binding.toolbar.profile=accountViewModel

        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        binding.viewModel = vm
        vm.mainInterface=this
        vm.init()

    }

    override fun onHomeClicked() {
        supportFragmentManager.findFragmentById(binding.fragmentView.id).let { fragment->
            if (fragment !is HomeFragment) {
                replaceFragment(HomeFragment())
            }
        }
    }

    override fun onAccountClicked() {
        if (!sharedPreferenceManager.isLogedIn){
            vm.moveToLogIn()
            return
        }
        supportFragmentManager.findFragmentById(binding.fragmentView.id).let { fragment->
            if (fragment !is ProfileFragment) {
                replaceFragment(ProfileFragment())
            }
        }
    }

    override fun onNotificationClicked() {
        supportFragmentManager.findFragmentById(binding.fragmentView.id).let { fragment->
            if (fragment !is NotificationFragment) {
                replaceFragment(NotificationFragment())
            }
        }
    }


    override fun moveToLogInScreen() {
        startLoginActivity.launch(LoginActivity.getIntent(this))
    }

    override fun onBackPressed() {
        Log.i("backClicked","006")
        vm.onBackButtonClicked()
    }
    override fun onBackClicked() {
        Log.i("backClicked","005")
        vm.onBackButtonClicked()
    }
    var doubleTab=false
    override fun onBackButtonClicked() {
        Log.i("backClicked","001")
        if (dashboardVisible(binding.fragmentView.id)){
            Log.i("backClicked","002")
            if (doubleTab){
                finishAffinity()
            }
            Log.i("backClicked","003")
            myToast("Press back again to exit")
            doubleTab=true
            Handler(mainLooper).postDelayed({
             doubleTab=false
            },2000)
        }else{
            Log.i("backClicked","004")
            super.onBackPressed()
        }
    }



    override fun callMyProfileAPI() {
        viewModel.getMyProfile()
        viewModel.myProfile.observe(this){
            stopAnim()
            if (it.status.intToBool()){
                vm.userProfile.value=it.oData
                sharedPreferenceManager.saveUser(it.oData,null)
            }
        }
    }

    override fun getCountryCodes() {
        viewModel.getAllCountries()
        viewModel.countries.observe(this){
            if (it.status.intToBool()){
                vm.counries.value=it
            }
        }
    }


    override fun getImageFile() {
    }



    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {

            }
        }


    override fun implementListners() {
        vm.userAlNotifications.observe(this){
            if (it is UiState.Success){

            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
    }




    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    override fun askNotificationPermission() {
        val job = lifecycleScope.launch(Dispatchers.Main, start = CoroutineStart.LAZY) {
            delay(2000)
            vm.checkForIntent()
            delay(4000)
            // This is only necessary for API level >= 33 (TIRAMISU)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    // FCM SDK (and your app) can post notifications.
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    // TODO: display an educational UI explaining to the user the features that will be enabled
                    //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                    //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                    //       If the user selects "No thanks," allow the user to continue without notifications.
                } else {
                    // Directly ask for the permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
        job.start()

    }


    override fun checkForIntent() {
        intent?.extras?.let {
            if (!it.containsKey("fromNotification")){
                //Not a notification
                return
            }
            if (!it.containsKey("type")){
                //does not have type
                return
            }
            when(it.getString("type")){
                else->{
                }
            }
        }
    }


    override fun moveToNotifications() {
        vm.onNotificationClicked()
    }

    override fun moveToProfile() {
        vm.onAccountClicked()
    }

    override fun deleteAllNotification() {
        vm.clearAllNotifications()
    }
}