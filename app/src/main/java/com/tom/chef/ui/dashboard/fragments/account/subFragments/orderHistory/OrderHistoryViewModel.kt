package com.tom.chef.ui.dashboard.fragments.account.subFragments.orderHistory

import com.tom.chef.models.OrderListResponse
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.comman.orders.OrderAdopter
import com.tom.chef.ui.comman.orders.OrderViewModel

class OrderHistoryViewModel: ViewModel {

    var orderAdopter=OrderAdopter(ArrayList())

    fun fillOrders(list:List<OrderListResponse.ODataItem>, orderInterface: OrderInterface) {
        val viewModels = ArrayList<ViewModel>()
        list.forEach {
            val viewModel = OrderViewModel(data = it)
            viewModel.orderInterface=orderInterface
            viewModels.add(viewModel)
        }
        orderAdopter.setList(viewModels)
    }
}