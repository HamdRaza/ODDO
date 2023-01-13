package com.tom.chef.ui.comman.orderItem

import androidx.databinding.ObservableField
import com.tom.chef.R
import com.tom.chef.models.OrderDetailsResponse
import com.tom.chef.ui.comman.ViewModel

class OrderItemViewModel(val data: OrderDetailsResponse.OData.OrderItem) : ViewModel {
    lateinit var orderItemInterface: OrderItemInterface

    @JvmField
    var itemName = ObservableField<String>()

    @JvmField
    var itemDescription = ObservableField<String>()

    @JvmField
    var itemImage = ObservableField<Any?>(R.drawable.menu_dummy_image)

    @JvmField
    var peopleCount = ObservableField<String>()

    @JvmField
    var price = ObservableField<String>()

    @JvmField
    var total = ObservableField<String>()

    @JvmField
    var fromOrderDetails = ObservableField<Boolean>(false)

    init {
        update(data)
    }

    fun update(data: OrderDetailsResponse.OData.OrderItem) {
        itemName.set(data.itemName)
        peopleCount.set("${data.packageSufficientFor} People")
        price.set(data.itemPerPrice)
        total.set("X ${data.itemQuantity}")
        itemDescription.set(data.packageTitle)
    }
}