package com.tom.chef.ui.thankYou

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.databinding.DataBindingUtil
import com.tom.chef.R
import com.tom.chef.databinding.ActivityOrderSuccessBinding
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ThankYouActivity : BaseActivity(), ThankYouInterface{
    private lateinit var binding: ActivityOrderSuccessBinding
    private lateinit var vm: ThankYouViewModel

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ThankYouActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_success)
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        vm = ThankYouViewModel()
        vm.thankYouInterface=this
        binding.viewModel = vm
        intent?.extras?.let {
            vm.updateOrderId(it)
        }

    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        vm.onGotoHomeClicked()
        return super.getOnBackInvokedDispatcher()
    }
    override fun onGoToHomeClicked() {
        finish()
    }

}