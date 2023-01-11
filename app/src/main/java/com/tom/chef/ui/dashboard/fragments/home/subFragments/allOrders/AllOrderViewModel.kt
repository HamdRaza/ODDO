package com.tom.chef.ui.dashboard.fragments.home.subFragments.allOrders

import com.tom.chef.models.OrderListResponse
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.comman.orders.OrderAdopter
import com.tom.chef.ui.comman.orders.OrderViewModel
import com.tom.chef.utils.orignalName

class AllOrderViewModel : ViewModel {

    var orderAdopter = OrderAdopter(ArrayList())

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