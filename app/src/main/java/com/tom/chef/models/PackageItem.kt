package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class PackageItem(
    @SerializedName("is_default")
    val isDefault: Int,
    @SerializedName("out_of_stock")
    val outOfStock: Int,
    @SerializedName("package_title_ar")
    val packageTitleAr: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("regular_price")
    val regularPrice: String,
    @SerializedName("sale_price")
    val salePrice: String,
    @SerializedName("sufficient_for")
    val sufficientFor: String,
    @SerializedName("title")
    val title: String
)