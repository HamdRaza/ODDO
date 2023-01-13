package com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tom.chef.R
import com.tom.chef.databinding.FragmentDashboardHomeScheduledorderBinding
import com.tom.chef.databinding.FragmentOrderDetailsBinding
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.tabsAdopter.ViewStateAdapter
import com.tom.chef.ui.dashboard.fragments.home.subFragments.allOrders.AllOrdersFragment
import com.tom.chef.utils.handleClick
import com.tom.chef.utils.handleHull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment(), OrderDetailsInterface {
    private lateinit var binding: FragmentOrderDetailsBinding

    lateinit var orderDetailsViewModel: OrderDetailsViewModel

    lateinit var status: String
    lateinit var id: String
    lateinit var orderNumber: String
    var fromOrderHistory: Boolean = false
    val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.bind(
            inflater.inflate(
                R.layout.fragment_order_details,
                container,
                false
            )
        )
        status = arguments?.getString("tabName", "").handleHull()
        id = arguments?.getString("id", "").handleHull()
        orderNumber = arguments?.getString("orderNumber", "").handleHull()
        arguments?.getBoolean("fromOrderHistory", false)?.let {
            fromOrderHistory = it
        }
        mActivity.toolbarVM.manageToolBar(
            showToolbar = true,
            showBackButton = true,
            backButtonText = orderNumber
        )
        mainActivity.toolbarVM.makeBackRound(isRound = true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderDetailsViewModel = OrderDetailsViewModel(mActivity)
        orderDetailsViewModel.orderDetailsInterface = this
        binding.viewModel = orderDetailsViewModel
        fillData()
        getOrderDetails()
    }

    private fun getOrderDetails() {
        appViewModel.getOrderDetails(id)
        appViewModel.getOrderDetailsLive.observe(viewLifecycleOwner) {
            if (it.status == "1") {
                orderDetailsViewModel.fillOrderItems(fromOrderHistory, it.oData)
            } else {
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun fillData() {
        if (fromOrderHistory) {
            orderDetailsViewModel.onFoodPreparedClicked()
        }
    }


    override fun callRiderClicked() {

    }

    override fun onFoodPreparedClicked() {

    }

    override fun onRequestExtraTimeClicked() {

    }

    override fun onRejectClicked() {

    }

    override fun onAcceptClicked() {

    }

}