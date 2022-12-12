package com.tom.chef.models.profile


import com.google.gson.annotations.SerializedName

data class ResponseGetFAQ(
    @SerializedName("message")
    val message: String,
    @SerializedName("oData")
    val oData: List<OData>,
    @SerializedName("status")
    val status: Int
){
    data class OData(
        @SerializedName("active")
        val active: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("created_by")
        val createdBy: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("updated_by")
        val updatedBy: Int
    )
}