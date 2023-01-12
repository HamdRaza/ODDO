package com.tom.chef.ui.dashboard.fragments.account.subFragments.orderHistory

import com.tom.chef.models.OrderHistoryResponse
import com.tom.chef.models.OrderListResponse
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.comman.orders.OrderAdopter
import com.tom.chef.ui.comman.orders.OrderViewModel

class OrderHistoryViewModel : ViewModel {

    var orderAdopter = OrderAdopter(ArrayList())

    fun fillOrders(list: List<OrderHistoryResponse.OData>, orderInterface: OrderInterface) {
        val viewModels = ArrayList<ViewModel>()
        list.forEach {
            val data = OrderListResponse.ODataItem(
                orderNumber = it.orderNumber,
                totalItemQuantity = it.totalItemQuantity,
                id = it.id,
                deliveryDateTime = it.deliveryDateTime,
                foodNames = it.foodNames,
                grandTotal = it.grandTotal,
                orderStatus = it.chefWithdrawStatus,
                orderStatusText = it.chefWithdrawStatusTxt
            )
            val viewModel = OrderViewModel(data = data)
            viewModel.orderInterface = orderInterface
            viewModels.add(viewModel)
        }
        orderAdopter.setList(viewModels)
    }
}