package com.tom.chef.models.auth

data class ResponseCountries(
    val message: String,
    val oData: List<OData>,
    val status: Int
){
    data class OData(
        val active: String,
        val created_at: String,
        val deleted: Int,
        val dial_code: String,
        val id: Int,
        val name: String,
        val prefix: String,
        val updated_at: String
    )
}
