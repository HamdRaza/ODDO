package com.tom.chef.ui.dashboard.fragments.home.subFragments.allOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.tom.chef.R
import com.tom.chef.databinding.FragmentAllOrdersBinding
import com.tom.chef.databinding.FragmentDashboardHomeBinding
import com.tom.chef.databinding.FragmentDashboardHomeCurrentorderBinding
import com.tom.chef.models.OrderListResponse
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails.OrderDetailsFragment
import com.tom.chef.ui.dashboard.fragments.notification.NotificationFragment
import com.tom.chef.utils.DataFactory.Companion.baseActivity
import com.tom.chef.utils.getLocalText
import com.tom.chef.utils.handleHull
import com.tom.chef.utils.intToBool
import com.tom.chef.utils.myToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllOrdersFragment : BaseFragment(), OrderInterface {
    private lateinit var binding: FragmentAllOrdersBinding

    lateinit var allOrderViewModel: AllOrderViewModel
    val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var status: String
    lateinit var type: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllOrdersBinding.bind(
            inflater.inflate(
                R.layout.fragment_all_orders,
                container,
                false
            )
        )
        status = arguments?.getString("tabName", "").handleHull()
        type = arguments?.getString("type", "").handleHull()
        allOrderViewModel = AllOrderViewModel()
        binding.viewModel = allOrderViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrders()
    }

    override fun onOrderClicked(data: OrderListResponse.ODataItem) {
        childFragmentManager.findFragmentById(mainActivity.binding.fragmentView.id)
            .let { fragment ->
                if (fragment !is OrderDetailsFragment) {
                    val orderDetailsFragment = OrderDetailsFragment()
                    orderDetailsFragment.arguments = bundleOf(
                        Pair("tabName", status),
                        Pair("id", "${data.id}"),
                        Pair("orderNumber", data.orderNumber)
                    )
                    mainActivity.replaceFragment(orderDetailsFragment)
                }
            }
    }

    //order_type
//1-current, 2-past, 3-scheduled

    //order_status
//0-Pending
//1,2,3,6,7- accepted
//15- rejected
//4- delivered
    private fun getOrders() {
        var orderStatus = "0"
        var orderType = "1"
        when (type) {
            "current" -> {
                orderType = "1"
            }
            "past" -> {
                orderType = "2"
            }
            "scheduled" -> {
                orderType = "3"
            }
        }
        when (status) {
//            "PENDING", "ACCEPTED", "REJECTED", "COMPLETED"
            "PENDING" -> {
                orderStatus = "0"
            }
            "ACCEPTED" -> {
                orderStatus = "1,2,3,6,7"
            }
            "REJECTED" -> {
                orderStatus = "15"
            }
            "COMPLETED" -> {
                orderStatus = "4"
            }
        }
        appViewModel.orderListAPI(
            order_type = orderType,
            page = "1",
            limit = "15",
            order_status = orderStatus,
            user_timezone = "Asia/Kolkata"
        )
        appViewModel.orderListLive.observe(mActivity) {
            if (it.status == "1") {
                it?.oData?.let {
                    allOrderViewModel.fillOrders(list = it, orderInterface = this)
                }
            }
        }
    }


}


