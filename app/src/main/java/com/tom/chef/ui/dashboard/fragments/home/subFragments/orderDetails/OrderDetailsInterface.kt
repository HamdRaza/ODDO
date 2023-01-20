package com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails

interface OrderDetailsInterface {
    fun onAcceptClicked()
    fun onRejectClicked(reason: String)
    fun onFoodPreparedClicked()
    fun onRequestExtraTimeClicked()
    fun callRiderClicked()
}