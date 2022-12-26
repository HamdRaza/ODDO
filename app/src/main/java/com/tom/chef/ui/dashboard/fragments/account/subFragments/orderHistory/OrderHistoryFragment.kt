package com.tom.chef.ui.dashboard.fragments.account.subFragments.orderHistory

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
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails.OrderDetailsFragment
import com.tom.chef.ui.dashboard.fragments.notification.NotificationFragment
import com.tom.chef.utils.handleHull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderHistoryFragment :  BaseFragment(),OrderInterface{
    private lateinit var binding: FragmentOrderHistoryBinding

    lateinit var orderHistoryViewModel: OrderHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrderHistoryBinding.bind(inflater.inflate(R.layout.fragment_order_history, container, false))

        mActivity.toolbarVM.manageToolBar(showToolbar = true, showBackButton = true, backButtonText = "Order History")
        mainActivity.toolbarVM.makeBackRound(isRound = true)

        orderHistoryViewModel= OrderHistoryViewModel()
        orderHistoryViewModel.fillOrders(status = "Request for payment", orderInterface = this)
        binding.viewModel=orderHistoryViewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onOrderClicked() {
        childFragmentManager.findFragmentById(mainActivity.binding.fragmentView.id).let { fragment->
            if (fragment !is OrderDetailsFragment) {
                val orderDetailsFragment=OrderDetailsFragment()
                orderDetailsFragment.arguments= bundleOf(Pair("tabName","Delivered"))
                mainActivity.replaceFragment(orderDetailsFragment)
            }
        }
    }

}


