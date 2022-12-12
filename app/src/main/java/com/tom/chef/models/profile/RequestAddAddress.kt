package com.tom.chef.models.profile


import com.google.gson.annotations.SerializedName

data class RequestAddAddress(
    @SerializedName("address")
    val address: String,
    @SerializedName("building_name")
    val buildingName: String,
    @SerializedName("is_default")
    val isDefault: String,
    @SerializedName("land_mark")
    val landMark: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("full_name")
    val fullName:String?=null,
    @SerializedName("address_id")
    val addressID:String?=null
)