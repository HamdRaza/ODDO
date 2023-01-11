package com.tom.chef.models

import com.google.gson.annotations.SerializedName

data class OrderListResponse(

	@field:SerializedName("oData")
	val oData: List<ODataItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("total_orders")
	val totalOrders: Int? = null,

	@field:SerializedName("errors")
	val errors: Errors? = null,

	@field:SerializedName("status")
	val status: String? = null
){
	data class ODataItem(

		@field:SerializedName("preparation_unit")
		val preparationUnit: Int? = null,

		@field:SerializedName("preperation_time")
		val preperationTime: String? = null,

		@field:SerializedName("user_image")
		val userImage: String? = null,

		@field:SerializedName("user_name")
		val userName: String? = null,

		@field:SerializedName("delivery_date_time")
		val deliveryDateTime: String? = null,

		@field:SerializedName("order_number")
		val orderNumber: String? = null,

		@field:SerializedName("total_item_quantity")
		val totalItemQuantity: Int? = null,

		@field:SerializedName("pickup_datetime")
		val pickupDatetime: String? = null,

		@field:SerializedName("reject_reason")
		val rejectReason: Any? = null,

		@field:SerializedName("delivery_time")
		val deliveryTime: String? = null,

		@field:SerializedName("scheduled_date")
		val scheduledDate: String? = null,

		@field:SerializedName("createdat")
		val createdat: String? = null,

		@field:SerializedName("order_status")
		val orderStatus: Int? = null,

		@field:SerializedName("order_status_text")
		val orderStatusText: String? = null,

		@field:SerializedName("food_gallery")
		val foodGallery: List<String?>? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("grand_total")
		val grandTotal: String? = null,

		@field:SerializedName("pickup_time")
		val pickupTime: String? = null,

		@field:SerializedName("food_names")
		val foodNames: String? = null
	)
}

data class Errors(
	val any: Any? = null
)


