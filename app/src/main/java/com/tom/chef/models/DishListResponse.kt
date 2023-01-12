package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class DishListResponse(
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
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("chef_id")
        val chefId: Int,
        @SerializedName("contain_package")
        val containPackage: Int,
        @SerializedName("cuisine_name")
        val cuisineName: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("favourate")
        val favourate: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("is_publish")
        val isPublish: Int,
        @SerializedName("menuname")
        val menuname: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("out_of_stock")
        val outOfStock: String,
        @SerializedName("preparation_time")
        val preparationTime: String,
        @SerializedName("preparation_unit")
        val preparationUnit: Int,
        @SerializedName("quantity")
        val quantity: String,
        @SerializedName("rated_users")
        val ratedUsers: Int,
        @SerializedName("regular_price")
        val regularPrice: String,
        @SerializedName("sale_price")
        val salePrice: String,
        @SerializedName("sufficient_for")
        val sufficientFor: Int,
        @SerializedName("sum_rating")
        val sumRating: String
    )
}