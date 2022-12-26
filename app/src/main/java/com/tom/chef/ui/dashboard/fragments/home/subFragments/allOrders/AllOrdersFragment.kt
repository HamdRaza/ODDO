package com.tom.chef.ui.dashboard.fragments.home.subFragments.allOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.tom.chef.R
import com.tom.chef.databinding.FragmentAllOrdersBinding
import com.tom.chef.databinding.FragmentDashboardHomeBinding
import com.tom.chef.databinding.FragmentDashboardHomeCurrentorderBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails.OrderDetailsFragment
import com.tom.chef.ui.dashboard.fragments.notification.NotificationFragment
import com.tom.chef.utils.handleHull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllOrdersFragment :  BaseFragment(),OrderInterface{
    private lateinit var binding: FragmentAllOrdersBinding

    lateinit var allOrderViewModel: AllOrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var status:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAllOrdersBinding.bind(inflater.inflate(R.layout.fragment_all_orders, container, false))
        status=arguments?.getString("tabName","").handleHull()
        allOrderViewModel= AllOrderViewModel()
        allOrderViewModel.fillOrders(status = status, orderInterface = this)
        binding.viewModel=allOrderViewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onOrderClicked() {
        childFragmentManager.findFragmentById(mainActivity.binding.fragmentView.id).let { fragment->
            if (fragment !is OrderDetailsFragment) {
                val orderDetailsFragment=OrderDetailsFragment()
                orderDetailsFragment.arguments= bundleOf(Pair("tabName",status))
                mainActivity.replaceFragment(orderDetailsFragment)
            }
        }
    }

}


