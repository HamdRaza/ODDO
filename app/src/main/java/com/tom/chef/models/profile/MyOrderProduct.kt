package com.tom.chef.models.profile


import com.google.gson.annotations.SerializedName

data class MyOrderProduct(
    @SerializedName("admin_commission")
    val adminCommission: String,
    @SerializedName("default_attribute_id")
    val defaultAttributeId: Int,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("order_id")
    val orderId: Int,
    @SerializedName("order_status")
    val orderStatus: Int,
    @SerializedName("order_status_text")
    val orderStatusText: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_attribute_id")
    val productAttributeId: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("product_type")
    val productType: Int,
    @SerializedName("product_variant_id")
    val productVariantId: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("shipping_charge")
    val shippingCharge: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("vendor_commission")
    val vendorCommission: String,
    @SerializedName("vendor_id")
    val vendorId: Int
)