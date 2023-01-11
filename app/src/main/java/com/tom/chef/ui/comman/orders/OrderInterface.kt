package com.tom.chef.ui.comman.orders

import com.tom.chef.models.OrderListResponse

interface OrderInterface {
    fun onOrderClicked(data: OrderListResponse.ODataItem)

}