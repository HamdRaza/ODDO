package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class FaqResponse(
    @SerializedName("errors")
    val errors: List<Any>,
    @SerializedName("message")
    val message: String,
    @SerializedName("oData")
    val oData: List<OData>,
    @SerializedName("status")
    val status: String
) {
    data class OData(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("desc_ar")
        val descAr: String,
        @SerializedName("desc_en")
        val descEn: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("meta_description")
        val metaDescription: Any,
        @SerializedName("meta_keyword")
        val metaKeyword: Any,
        @SerializedName("meta_title")
        val metaTitle: Any,
        @SerializedName("status")
        val status: Int,
        @SerializedName("title_ar")
        val titleAr: String,
        @SerializedName("title_en")
        val titleEn: String,
        @SerializedName("updated_at")
        val updatedAt: String
    )
}