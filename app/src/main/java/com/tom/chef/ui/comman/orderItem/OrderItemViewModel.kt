package com.tom.chef.ui.comman.orderItem

import androidx.databinding.ObservableField
import com.tom.chef.R
import com.tom.chef.ui.comman.ViewModel

class OrderItemViewModel : ViewModel {
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
        itemName.set("Meat Samosa ")
        peopleCount.set("4 People")
        price.set("AED 20.3")
        total.set("X 2")
        itemDescription.set("Lorem ipsum dolor sit amet, consectetur adipiscing ")
    }

}