package com.tom.chef.models.auth


import com.google.gson.annotations.SerializedName

data class LogInRequest(
    @SerializedName("jsonrpc")
    val jsonrpc: String="2.0",
    @SerializedName("params")
    val params: Params
) {
    data class Params(
        @SerializedName("db")
        val db: String="odoo15_beta",
        @SerializedName("login")
        val login: String,
        @SerializedName("password")
        val password: String
    )
}