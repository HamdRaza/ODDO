package com.tom.chef.ui.auth.termsCondition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.tom.chef.R
import com.tom.chef.databinding.ActivityTermsConditionsBinding
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.dashboard.toolBar.ToolBarInterface
import com.tom.chef.ui.dashboard.toolBar.ToolBarViewModel
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TermsAndConditionActivity : BaseActivity(), TermsAndConditionInterface,ToolBarInterface{
    private lateinit var binding: ActivityTermsConditionsBinding
    private lateinit var vm: TermsAndConditionViewModel

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, TermsAndConditionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_conditions)
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        vm = TermsAndConditionViewModel(this)
        binding.viewModel = vm
        vm.termsAndConditionInterface = this
        vm.init()

    }

    override fun initWindow() {
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()

        toolbarVM = ToolBarViewModel(this)
        binding.toolbar.toolbarViewModel=toolbarVM
        toolbarVM.toolBarInterface=this

        toolbarVM.manageToolBar(showToolbar = true, showCenterText = false,showBackButton = true, backButtonText = "Terms and conditions")
        getPage()
    }

    override fun onBackClicked() {
        finish()
    }

    fun getPage(){
        startAnim()
        stopAnim()
        /*
        viewModel.getPage(page_id = "2")
        viewModel.getPageLive.observe(this){
            stopAnim()
            if (it.status.intToBool()){
                vm.updateText(it)
            }else{
                myToast(it.message)
            }
        }

         */
    }

}