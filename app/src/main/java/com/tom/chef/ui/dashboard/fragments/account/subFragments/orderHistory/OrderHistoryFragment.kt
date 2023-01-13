package com.tom.chef.ui.dashboard.fragments.account.subFragments.orderHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.tom.chef.R
import com.tom.chef.databinding.FragmentOrderHistoryBinding
import com.tom.chef.models.OrderListResponse
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails.OrderDetailsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderHistoryFragment : BaseFragment(), OrderInterface {
    private lateinit var binding: FragmentOrderHistoryBinding

    lateinit var orderHistoryViewModel: OrderHistoryViewModel
    val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderHistoryBinding.bind(
            inflater.inflate(
                R.layout.fragment_order_history,
                container,
                false
            )
        )

        mActivity.toolbarVM.manageToolBar(
            showToolbar = true,
            showBackButton = true,
            backButtonText = "Order History"
        )
        mainActivity.toolbarVM.makeBackRound(isRound = true)

        orderHistoryViewModel = OrderHistoryViewModel()
        binding.viewModel = orderHistoryViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrderHistory()
    }

    private fun getOrderHistory() {
        appViewModel.getOrderHistory()
        appViewModel.getOrderHistoryLive.observe(viewLifecycleOwner) {
            if (it.status == "1") {
                orderHistoryViewModel.fillOrders(it.oData, orderInterface = this)
            } else {
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onOrderClicked(data: OrderListResponse.ODataItem) {
        childFragmentManager.findFragmentById(mainActivity.binding.fragmentView.id)
            .let { fragment ->
                if (fragment !is OrderDetailsFragment) {
                    val orderDetailsFragment = OrderDetailsFragment()
                    orderDetailsFragment.arguments =
                        bundleOf(
                            Pair("tabName", "Delivered"), Pair("fromOrderHistory", true),
                            Pair("id", "${data.id}"),
                            Pair("orderNumber", data.orderNumber)
                        )
                    mainActivity.replaceFragment(orderDetailsFragment)
                }
            }
    }

}


