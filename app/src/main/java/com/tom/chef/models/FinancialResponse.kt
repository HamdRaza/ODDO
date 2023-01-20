package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class FinancialResponse(
    @SerializedName("errors")
    val errors: Errors,
    @SerializedName("message")
    val message: String,
    @SerializedName("oData")
    val oData: OData,
    @SerializedName("status")
    val status: String
) {
    class Errors

    data class OData(
        @SerializedName("labels")
        val labels: List<Label>,
        @SerializedName("list")
        val list: List<Item>
    ) {
        data class Label(
            @SerializedName("amount")
            val amount: String,
            @SerializedName("title")
            val title: String
        )

        data class Item(
            @SerializedName("chef_withdraw_status")
            val chefWithdrawStatus: Int,
            @SerializedName("chef_withdraw_status_text")
            val chefWithdrawStatusText: String,
            @SerializedName("customer_name")
            val customerName: String,
            @SerializedName("grand_total")
            val grandTotal: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("order_number")
            val orderNumber: String,
            @SerializedName("total_commission")
            val totalCommission: String,
            @SerializedName("user_id")
            val userId: Int
        )
    }
}