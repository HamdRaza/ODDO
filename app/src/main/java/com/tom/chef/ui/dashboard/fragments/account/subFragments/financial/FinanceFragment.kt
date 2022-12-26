package com.tom.chef.ui.dashboard.fragments.account.subFragments.financial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.tom.chef.R
import com.tom.chef.databinding.FragmentAllOrdersBinding
import com.tom.chef.databinding.FragmentDashboardHomeBinding
import com.tom.chef.databinding.FragmentDashboardHomeCurrentorderBinding
import com.tom.chef.databinding.FragmentFinanceBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails.OrderDetailsFragment
import com.tom.chef.ui.dashboard.fragments.notification.NotificationFragment
import com.tom.chef.utils.handleHull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FinanceFragment :  BaseFragment(),FinancialInterface{
    private lateinit var binding: FragmentFinanceBinding

    lateinit var financialViewModel: FinancialViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFinanceBinding.bind(inflater.inflate(R.layout.fragment_finance, container, false))
        mActivity.toolbarVM.manageToolBar(showToolbar = true, showBackButton = true, backButtonText = "Financial Dashboard")
        mainActivity.toolbarVM.makeBackRound(isRound = true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        financialViewModel= FinancialViewModel()
        financialViewModel.fillFinance()
        binding.viewModel=financialViewModel
    }


}


