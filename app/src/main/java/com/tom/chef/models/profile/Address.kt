package com.tom.chef.models.profile


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("address")
    val address: String,
    @SerializedName("building_name")
    val buildingName: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_default")
    var isDefault: Int,
    @SerializedName("land_mark")
    val landMark: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("longitude")
    val longitude: String
)