package com.tom.chef.ui.comman.orders

import androidx.databinding.ObservableField
import com.tom.chef.ui.comman.ViewModel

class OrderViewModel:ViewModel {
    lateinit var orderInterface: OrderInterface

    @JvmField
    var orderNumber=ObservableField<String>()
    @JvmField
    var pickUpTime=ObservableField<String>()
    @JvmField
    var deliveryTime=ObservableField<String>()
    @JvmField
    var itemsCount=ObservableField<String>()
    @JvmField
    var itemsNames=ObservableField<String>()
    @JvmField
    var price=ObservableField<String>()
    @JvmField
    var status=ObservableField<String>()

    init {
        orderNumber.set("#TM-011550455587")
        pickUpTime.set("Pick Up Time : 03:45 PM")
        deliveryTime.set("Delivery Time : 04:45 PM")
        itemsCount.set("5 Items")
        itemsNames.set("Shawarma x2, Chicken Roll x5, Sharja Shake x4 Butterscotch x6")
        price.set("AED  100.00")
        status.set("Pending")
    }
    fun onClicked(){
        orderInterface.onOrderClicked()
    }
    fun update(){

    }
}