package com.tom.chef.ui.dashboard.fragments.account.subFragments.orderHistory

import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.comman.orders.OrderAdopter
import com.tom.chef.ui.comman.orders.OrderViewModel

class OrderHistoryViewModel: ViewModel {

    var orderAdopter=OrderAdopter(ArrayList())

    fun fillOrders(status:String,orderInterface: OrderInterface){
        val viewModels=ArrayList<ViewModel>()
        for (i in 0 until 10){
            val viewModel=OrderViewModel()
            viewModel.status.set(status)
            viewModel.orderInterface=orderInterface
            viewModels.add(viewModel)
        }
        orderAdopter.setList(viewModels)
    }
}