package com.tom.chef.ui.comman.orders

import androidx.databinding.ObservableField
import com.tom.chef.models.OrderListResponse
import com.tom.chef.ui.comman.ViewModel

class OrderViewModel(val data: OrderListResponse.ODataItem) : ViewModel {
    lateinit var orderInterface: OrderInterface

    @JvmField
    var orderNumber = ObservableField<String>()

    @JvmField
    var pickUpTime = ObservableField<String>()

    @JvmField
    var deliveryTime = ObservableField<String>()

    @JvmField
    var itemsCount = ObservableField<String>()

    @JvmField
    var itemsNames = ObservableField<String>()

    @JvmField
    var price = ObservableField<String>()

    @JvmField
    var status = ObservableField<String>()

    init {
        update(data)
    }

    fun onClicked() {
        orderInterface.onOrderClicked(data = data)
    }

    fun update(data: OrderListResponse.ODataItem) {
        orderNumber.set(data.orderNumber)
        pickUpTime.set("Pick Up Time : ${data.pickupTime}")
        deliveryTime.set("Delivery Time : ${data.deliveryTime}")
        itemsCount.set("${data.totalItemQuantity} Items")
        itemsNames.set(data.foodNames)
        price.set(data.grandTotal)
        status.set(data.orderStatusText)
    }
}