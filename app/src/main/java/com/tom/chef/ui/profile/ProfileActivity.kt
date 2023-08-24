package com.tom.chef.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.databinding.DataBindingUtil
import com.tom.chef.R
import com.tom.chef.databinding.ActivityProfileBinding
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.auth.logIn.LoginActivity
import com.tom.chef.ui.comman.toolBar.ToolBarInterface
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : BaseActivity(), ProfileContracts, ToolBarInterface {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var binding: ActivityProfileBinding
    lateinit var profileViewModel: ProfileVM

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        profileViewModel= ProfileVM()
        binding.viewModel = profileViewModel
        profileViewModel.viewInteractor = this
        profileViewModel.setData()
        profileViewModel.toolBarViewModel.toolBarInterface=this
    }




    override fun initiate() {
        CookieManager.getInstance().setAcceptCookie(true);
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        sharedPreferenceManager.getSavedUser()?.let {
            profileViewModel.profileViewModel.updateUserDetails(it)
        }
        profileViewModel.toolBarViewModel.manageToolBar(showToolbar = true, showBackButton = true, backButtonText = "Profile")
        binding.profile=profileViewModel.profileViewModel
    }

    override fun logOutClicked() {
        sharedPreferenceManager.clear()
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        startActivity(LoginActivity.getIntent(this))
        finishAffinity()
    }

    override fun ShowLoading() {
        startAnim()
    }

    override fun HideLoading() {
       stopAnim()
    }

    override fun checkForBackExit() {
        finish()
    }

    override fun onBackClicked() {
        checkForBackExit()
    }
}