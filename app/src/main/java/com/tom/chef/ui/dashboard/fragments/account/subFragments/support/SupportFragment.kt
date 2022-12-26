package com.tom.chef.ui.dashboard.fragments.account.subFragments.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.tom.chef.R
import com.tom.chef.databinding.FragmentAllOrdersBinding
import com.tom.chef.databinding.FragmentDashboardHomeBinding
import com.tom.chef.databinding.FragmentDashboardHomeCurrentorderBinding
import com.tom.chef.databinding.FragmentOrderHistoryBinding
import com.tom.chef.databinding.FragmentSupportBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails.OrderDetailsFragment
import com.tom.chef.ui.dashboard.fragments.notification.NotificationFragment
import com.tom.chef.utils.handleHull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SupportFragment :  BaseFragment(){
    private lateinit var binding: FragmentSupportBinding

    lateinit var supportViewModel: SupportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSupportBinding.bind(inflater.inflate(R.layout.fragment_support, container, false))

        mActivity.toolbarVM.manageToolBar(showToolbar = true, showBackButton = true, backButtonText = "Customer Support")
        mainActivity.toolbarVM.makeBackRound(isRound = true)

        supportViewModel= SupportViewModel(baseActivity = mActivity)
        binding.viewModel=supportViewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}


