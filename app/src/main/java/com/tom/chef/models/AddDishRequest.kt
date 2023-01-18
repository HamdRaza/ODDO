package com.tom.chef.models

import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

//@Part("access_token") access_token: RequestBody,
//        @Part("name") name: RequestBody,
//        @Part("name_ar") name_ar: RequestBody,
//        @Part("description") description: RequestBody,
//        @Part("description_ar") description_ar: RequestBody,
//        @Part("regular_price") regular_price: RequestBody,
//        @Part("sale_price") sale_price: RequestBody,
//        @Part("sufficient_for") sufficient_for: RequestBody,
//        @Part("quantity") quantity: RequestBody,
//        @Part("out_of_stock") out_of_stock: RequestBody,//0
//        @Part("menu_id[]") menu_id: ArrayList<String>,
//        @Part("cuisine[]") cuisine: ArrayList<String>,
//        @Part("active") active: RequestBody,//0
//        @Part image: MultipartBody.Part? = null,
//        @Part gallery: List<MultipartBody.Part>? = null,
//        @Part("contain_package") contain_package: RequestBody,//0 or 1
//        @Part("package[]") packageList: ArrayList<PackageItem>
data class AddDishRequest(
    val access_token: RequestBody,
    val name: RequestBody,
    val name_ar: RequestBody,
    val description: RequestBody,
    val description_ar: RequestBody,
    val regular_price: RequestBody,
    val sale_price: RequestBody,
    val sufficient_for: RequestBody,
    val quantity: RequestBody,
    val out_of_stock: RequestBody = "0".toRequestBody("text/plain".toMediaType()),
    val menu_id: ArrayList<String>,
    val cuisine_id: ArrayList<String>,
    val active: RequestBody = "0".toRequestBody("text/plain".toMediaType()),
    val image: MultipartBody.Part? = null,
    val gallery: List<MultipartBody.Part>? = null,
    val contain_package: RequestBody,
    val packageList: ArrayList<JsonObject>? = null
)
