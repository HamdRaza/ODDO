package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class OrderHistoryResponse(
    @SerializedName("errors")
    val errors: Errors,
    @SerializedName("message")
    val message: String,
    @SerializedName("oData")
    val oData: List<OData>,
    @SerializedName("status")
    val status: String
) {
    class Errors

    data class OData(
        @SerializedName("chef_withdraw_request_at")
        val chefWithdrawRequestAt: String,
        @SerializedName("chef_withdraw_status")
        val chefWithdrawStatus: Int,
        @SerializedName("chef_withdraw_status_txt")
        val chefWithdrawStatusTxt: String,
        @SerializedName("delivery_date_time")
        val deliveryDateTime: Any,
        @SerializedName("food_names")
        val foodNames: String,
        @SerializedName("grand_total")
        val grandTotal: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("order_number")
        val orderNumber: String,
        @SerializedName("restaurant_commission_amount")
        val restaurantCommissionAmount: String,
        @SerializedName("total_item_quantity")
        val totalItemQuantity: Int
    )
}