package com.tom.chef.models.auth


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id: Any,
    @SerializedName("jsonrpc")
    val jsonrpc: String,
    @SerializedName("result")
    val result: Result,
    @SerializedName("error")
    val error:Error?=null
) {
    data class Error(
        @SerializedName("code")
        var code:Int,
        @SerializedName("message")
        var message:String,
        )
    data class Result(
        @SerializedName("active_ids_limit")
        val activeIdsLimit: Int,
        @SerializedName("cache_hashes")
        val cacheHashes: CacheHashes,
        @SerializedName("company_id")
        val companyId: Int,
        @SerializedName("currencies")
        val currencies: Currencies,
        @SerializedName("db")
        val db: String,
        @SerializedName("display_switch_company_menu")
        val displaySwitchCompanyMenu: Boolean,
        @SerializedName("home_action_id")
        val homeActionId: Boolean,
        @SerializedName("is_admin")
        val isAdmin: Boolean,
        @SerializedName("is_system")
        val isSystem: Boolean,
        @SerializedName("max_file_upload_size")
        val maxFileUploadSize: Int,
        @SerializedName("max_time_between_keys_in_ms")
        val maxTimeBetweenKeysInMs: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("notification_type")
        val notificationType: String,
        @SerializedName("odoobot_initialized")
        val odoobotInitialized: Boolean,
        @SerializedName("partner_display_name")
        val partnerDisplayName: String,
        @SerializedName("partner_id")
        val partnerId: Int,
        @SerializedName("profile_collectors")
        val profileCollectors: Any,
        @SerializedName("profile_params")
        val profileParams: Any,
        @SerializedName("profile_session")
        val profileSession: Any,
        @SerializedName("server_version")
        val serverVersion: String,
        @SerializedName("server_version_info")
        val serverVersionInfo: List<Any>,
        @SerializedName("show_effect")
        val showEffect: String,
        @SerializedName("support_url")
        val supportUrl: String,
        @SerializedName("uid")
        val uid: Int,
        @SerializedName("user_companies")
        val userCompanies: UserCompanies,
        @SerializedName("user_context")
        val userContext: UserContext,
        @SerializedName("user_id")
        val userId: List<Int>,
        @SerializedName("username")
        val username: String,
        @SerializedName("web.base.url")
        val webBaseUrl: String
    ) {
        data class CacheHashes(
            @SerializedName("assets_discuss_public")
            val assetsDiscussPublic: String,
            @SerializedName("load_menus")
            val loadMenus: String,
            @SerializedName("qweb")
            val qweb: String,
            @SerializedName("translations")
            val translations: String
        )

        data class Currencies(
            @SerializedName("1")
            val x1: X1,
            @SerializedName("2")
            val x2: X2
        ) {
            data class X1(
                @SerializedName("digits")
                val digits: List<Int>,
                @SerializedName("position")
                val position: String,
                @SerializedName("symbol")
                val symbol: String
            )

            data class X2(
                @SerializedName("digits")
                val digits: List<Int>,
                @SerializedName("position")
                val position: String,
                @SerializedName("symbol")
                val symbol: String
            )
        }

        data class UserCompanies(
            @SerializedName("allowed_companies")
            val allowedCompanies: AllowedCompanies,
            @SerializedName("current_company")
            val currentCompany: Int
        ) {
            data class AllowedCompanies(
                @SerializedName("1")
                val x1: X1
            ) {
                data class X1(
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("sequence")
                    val sequence: Int
                )
            }
        }

        data class UserContext(
            @SerializedName("lang")
            val lang: String,
            @SerializedName("tz")
            val tz: String,
            @SerializedName("uid")
            val uid: Int
        )
    }
}