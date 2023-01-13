package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class OrderDetailsResponse(
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
        @SerializedName("chef_commission")
        val chefCommission: String,
        @SerializedName("chef_instructions")
        val chefInstructions: String,
        @SerializedName("coupon_code")
        val couponCode: Any,
        @SerializedName("delivery_charges")
        val deliveryCharges: String,
        @SerializedName("delivery_time")
        val deliveryTime: String,
        @SerializedName("discount")
        val discount: String,
        @SerializedName("driver")
        val driver: Driver,
        @SerializedName("grand_total")
        val grandTotal: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("order_items")
        val orderItems: List<OrderItem>,
        @SerializedName("order_number")
        val orderNumber: String,
        @SerializedName("order_status")
        val orderStatus: Int,
        @SerializedName("order_status_text")
        val orderStatusText: String,
        @SerializedName("pickup_time")
        val pickupTime: String,
        @SerializedName("preparation_unit")
        val preparationUnit: Int,
        @SerializedName("preperation_time")
        val preperationTime: String,
        @SerializedName("rider_tip")
        val riderTip: String,
        @SerializedName("scheduled_date")
        val scheduledDate: String,
        @SerializedName("sub_total")
        val subTotal: String,
        @SerializedName("tax_amount")
        val taxAmount: String,
        @SerializedName("total_item_quantity")
        val totalItemQuantity: Int
    ) {
        data class Driver(
            @SerializedName("dial_code")
            val dialCode: String,
            @SerializedName("email")
            val email: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("phone_number")
            val phoneNumber: String
        )

        data class OrderItem(
            @SerializedName("image")
            val image: String,
            @SerializedName("item_id")
            val itemId: Int,
            @SerializedName("item_name")
            val itemName: String,
            @SerializedName("item_per_price")
            val itemPerPrice: String,
            @SerializedName("item_quantity")
            val itemQuantity: Int,
            @SerializedName("package_id")
            val packageId: Int,
            @SerializedName("package_quantity")
            val packageQuantity: String,
            @SerializedName("package_regular_price")
            val packageRegularPrice: String,
            @SerializedName("package_sufficient_for")
            val packageSufficientFor: Int,
            @SerializedName("package_title")
            val packageTitle: String
        )
    }
}