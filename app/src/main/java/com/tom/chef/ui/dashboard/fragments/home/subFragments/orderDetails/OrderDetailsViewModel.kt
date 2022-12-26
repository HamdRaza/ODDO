package com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.ui.comman.orderItem.OrderItemAdopter
import com.tom.chef.ui.comman.orderItem.OrderItemInterface
import com.tom.chef.ui.comman.orderItem.OrderItemViewModel
import com.tom.chef.ui.comman.orders.OrderAdopter
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.comman.orders.OrderViewModel

class OrderDetailsViewModel:ViewModel(),OrderItemInterface {
    lateinit var orderDetailsInterface: OrderDetailsInterface
    fun onAcceptClicked(){
        status.set("Accepted")
        orderDetailsInterface.onAcceptClicked()
        updateStatus()
    }
    fun onRejectClicked(){
        status.set("Rejected")
        orderDetailsInterface.onRejectClicked()
        updateStatus()
    }
    fun onFoodPreparedClicked(){
        status.set("Completed")
        orderDetailsInterface.onFoodPreparedClicked()
        updateStatus()
    }
    fun onRequestExtraTimeClicked(){
        orderDetailsInterface.onRequestExtraTimeClicked()
        updateStatus()
    }
    fun callRiderClicked(){
        orderDetailsInterface.callRiderClicked()
        updateStatus()
    }







    @JvmField
    val showAcceptReject=ObservableField<Boolean>(false)
    @JvmField
    val showPrepared=ObservableField<Boolean>(false)
    @JvmField
    val showRequestExtraTime=ObservableField<Boolean>(false)
    @JvmField
    val showChefNote=ObservableField<Boolean>(false)

    @JvmField
    val pickUpTime=ObservableField<String>()
    @JvmField
    val deliveryTime=ObservableField<String>()
    @JvmField
    val noteToChef=ObservableField<String>()

    @JvmField
    val chefShareValue=ObservableField<String>()
    @JvmField
    val riderName=ObservableField<String>()

    @JvmField
    val status=ObservableField<String>()
    var orderItemAdopter= OrderItemAdopter(ArrayList())

    init {
        status.set("Pending")
        pickUpTime.set("03:45 PM")
        deliveryTime.set("04:45 PM")
        chefShareValue.set("AED 300.00")
        showChefNote.set(true)
        noteToChef.set("Make it less spicy and add more butter. Please give extra sauce")
        riderName.set("Kamal Das")
        updateStatus()
    }

    private fun updateStatus(){
        when(status.get()){
            "Pending"->{
                showAcceptReject.set(true)
                showPrepared.set(false)
                showRequestExtraTime.set(false)
            }
            "Accepted"->{
                showAcceptReject.set(false)
                showPrepared.set(true)
                showRequestExtraTime.set(true)
            }
            "Rejected","Ready","Completed"->{
                showAcceptReject.set(false)
                showPrepared.set(false)
                showRequestExtraTime.set(false)
            }
        }
    }



    fun fillOrderItems(){
        val viewModels=ArrayList<com.tom.chef.ui.comman.ViewModel>()
        for (i in 0 until 2){
            val viewModel= OrderItemViewModel()
            viewModel.orderItemInterface=this
            viewModels.add(viewModel)
        }
        orderItemAdopter.setList(viewModels)
    }

}