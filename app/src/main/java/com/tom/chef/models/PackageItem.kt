package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class PackageItem(
    @SerializedName("is_default")
    val isDefault: Int = 0,
    @SerializedName("out_of_stock")
    val outOfStock: Int = 0,
    @SerializedName("package_title_ar")
    var packageTitleAr: String = "",
    @SerializedName("quantity")
    val quantity: String = "",
    @SerializedName("regular_price")
    val regularPrice: String = "",
    @SerializedName("sale_price")
    var salePrice: String = "",
    @SerializedName("sufficient_for")
    val sufficientFor: String = "",
    @SerializedName("title")
    val title: String = ""
)