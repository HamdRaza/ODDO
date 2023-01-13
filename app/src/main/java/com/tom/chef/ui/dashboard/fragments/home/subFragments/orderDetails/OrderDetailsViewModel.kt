package com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.models.OrderDetailsResponse
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.allBottomSheets.rejectOffer.RejectOfferBottomSheet
import com.tom.chef.ui.comman.orderItem.OrderItemAdopter
import com.tom.chef.ui.comman.orderItem.OrderItemInterface
import com.tom.chef.ui.comman.orderItem.OrderItemViewModel
import com.tom.chef.ui.comman.orders.OrderAdopter
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.comman.orders.OrderViewModel

class OrderDetailsViewModel(var baseActivity: BaseActivity) : ViewModel(), OrderItemInterface {
    lateinit var orderDetailsInterface: OrderDetailsInterface
    fun onAcceptClicked() {
        status.set("Accepted")
        orderDetailsInterface.onAcceptClicked()
        updateStatus()
    }

    fun onRejectClicked() {
        RejectOfferBottomSheet().showRejectOffer(context = baseActivity) {
            status.set("Rejected")
            orderDetailsInterface.onRejectClicked()
            updateStatus()
        }
    }

    fun onFoodPreparedClicked() {
        status.set("Completed")
        orderDetailsInterface.onFoodPreparedClicked()
        updateStatus()
    }

    fun onRequestExtraTimeClicked() {
        orderDetailsInterface.onRequestExtraTimeClicked()
        updateStatus()
    }

    fun callRiderClicked() {
        orderDetailsInterface.callRiderClicked()
        updateStatus()
    }


    @JvmField
    val showAcceptReject = ObservableField<Boolean>(false)

    @JvmField
    val showPrepared = ObservableField<Boolean>(false)

    @JvmField
    val showRequestExtraTime = ObservableField<Boolean>(false)

    @JvmField
    val showChefNote = ObservableField<Boolean>(false)

    @JvmField
    val showDriverBox = ObservableField<Boolean>(true)

    @JvmField
    val pickUpTime = ObservableField<String>()

    @JvmField
    val deliveryTime = ObservableField<String>()

    @JvmField
    val noteToChef = ObservableField<String>()

    @JvmField
    val chefShareValue = ObservableField<String>()

    @JvmField
    val riderName = ObservableField<String>()

    @JvmField
    val status = ObservableField<String>()
    var orderItemAdopter = OrderItemAdopter(ArrayList())

    init {

        updateStatus()
    }

    private fun updateStatus() {
        when (status.get()) {
            "Pending" -> {
                showAcceptReject.set(true)
                showPrepared.set(false)
                showRequestExtraTime.set(false)
            }
            "Accepted" -> {
                showAcceptReject.set(false)
                showPrepared.set(true)
                showRequestExtraTime.set(true)
            }
            "Rejected", "Ready", "Completed" -> {
                showAcceptReject.set(false)
                showPrepared.set(false)
                showRequestExtraTime.set(false)
            }
        }
    }


    fun fillOrderItems(fromOrderHistory: Boolean, data: OrderDetailsResponse.OData) {
        showDriverBox.set(!fromOrderHistory)
        status.set(data.orderStatusText)
        pickUpTime.set(data.pickupTime)
        deliveryTime.set(data.deliveryTime)
        chefShareValue.set(data.chefCommission)
        showChefNote.set(true)
        noteToChef.set(data.chefInstructions)
        riderName.set(data.driver.name)
        val viewModels = ArrayList<com.tom.chef.ui.comman.ViewModel>()
        data.orderItems.forEach {
            val viewModel = OrderItemViewModel(it)
            viewModel.orderItemInterface = this
            viewModels.add(viewModel)
        }
        orderItemAdopter.setList(viewModels)
    }

}