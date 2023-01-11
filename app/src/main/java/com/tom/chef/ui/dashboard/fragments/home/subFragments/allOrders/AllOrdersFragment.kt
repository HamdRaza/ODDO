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
        allOrderViewModel = AllOrderViewModel()
        allOrderViewModel.fillOrders(status = status, orderInterface = this)
        binding.viewModel = allOrderViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrders()
    }

    override fun onOrderClicked() {
        childFragmentManager.findFragmentById(mainActivity.binding.fragmentView.id)
            .let { fragment ->
                if (fragment !is OrderDetailsFragment) {
                    val orderDetailsFragment = OrderDetailsFragment()
                    orderDetailsFragment.arguments = bundleOf(Pair("tabName", status))
                    mainActivity.replaceFragment(orderDetailsFragment)
                }
            }
    }

    //order_type
//1-current, 2-past, 3-scheduled

    //order_status
//0-Pending
//1,2,3,6,7- accepted
//4- delivered
//15- rejected
    private fun getOrders() {
        appViewModel.orderListAPI(
            "1",
            "1",
            "15",
            "0",
            "Asia/Kolkata"
        )
        appViewModel.orderListLive.observe(mActivity) {
            if (it.status == "1") {

                Toast.makeText(requireContext(), "Fetch Successful", Toast.LENGTH_SHORT).show()
            }
        }
    }


}


