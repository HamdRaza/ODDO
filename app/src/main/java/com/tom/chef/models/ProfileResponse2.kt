package com.tom.chef.models


import com.google.gson.annotations.SerializedName

data class ProfileResponse2(
    @SerializedName("errors")
    val errors: Errors,
    @SerializedName("message")
    val message: String,
    @SerializedName("oData")
    val oData: OData,
    @SerializedName("status")
    val status: String
) {
    class Errors

    data class OData(
        @SerializedName("about_me")
        val aboutMe: String? = null,
        @SerializedName("brand_name")
        val brandName: String? = null,
        @SerializedName("account_no")
        val accountNo: Any? = null,
        @SerializedName("admin_commission")
        val adminCommission: String? = null,
        @SerializedName("allow_ordertype")
        val allowOrdertype: String? = null,
        @SerializedName("available")
        val available: Int? = null,
        @SerializedName("bank_account_proof")
        val bankAccountProof: String? = null,
        @SerializedName("bank_branch")
        val bankBranch: Any? = null,
        @SerializedName("bank_id")
        val bankId: Int? = null,
        @SerializedName("benificiary")
        val benificiary: Any? = null,
        @SerializedName("building")
        val building: String? = null,
        @SerializedName("chef_cuisines")
        val chefCuisines: List<ChefCuisine>? = null,
        @SerializedName("chef_timing")
        val chefTiming: ChefTiming? = null,
        @SerializedName("country_id")
        val countryId: Int? = null,
        @SerializedName("cover_image")
        val coverImage: String? = null,
        @SerializedName("delivery_fee")
        val deliveryFee: String? = null,
        @SerializedName("dial_code")
        val dialCode: String,
        @SerializedName("email")
        val email: String?,
        @SerializedName("emirates_id")
        val emiratesId: String? = null,
        @SerializedName("end_time")
        val endTime: String? = null,
        @SerializedName("firebase_user_key")
        val firebaseUserKey: String,//
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("id")
        val id: Int,//
        @SerializedName("ifsc")
        val ifsc: Any? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("landmark")
        val landmark: String? = null,
        @SerializedName("last_name")
        val lastName: String?,
        @SerializedName("latitude")
        val latitude: String? = null,
        @SerializedName("location")
        val location: String? = null,
        @SerializedName("longitude")
        val longitude: String? = null,
        @SerializedName("name")
        val name: String,
        @SerializedName("passport_id")
        val passportId: String? = null,
        @SerializedName("phone_number")
        val phoneNumber: String,
        @SerializedName("preparation_time")
        val preparationTime: String? = null,
        @SerializedName("preparation_unit")
        val preparationUnit: String? = null,
        @SerializedName("start_time")
        val startTime: String? = null,
        @SerializedName("street")
        val street: String? = null,
        @SerializedName("swift")
        val swift: Any? = null,
        @SerializedName("trade_license")
        val tradeLicense: Any? = null,
        @SerializedName("visa_copy")
        val visaCopy: String? = null
    ) {
        data class ChefCuisine(
            @SerializedName("cuisine_id")
            val cuisineId: Int,
            @SerializedName("cuisine_name")
            val cuisineName: String
        )

        data class ChefTiming(
            @SerializedName("fri_from")
            val friFrom: String,
            @SerializedName("fri_to")
            val friTo: String,
            @SerializedName("friday")
            val friday: Int,
            @SerializedName("mon_from")
            val monFrom: String,
            @SerializedName("mon_to")
            val monTo: String,
            @SerializedName("monday")
            val monday: Int,
            @SerializedName("sat_from")
            val satFrom: String,
            @SerializedName("sat_to")
            val satTo: String,
            @SerializedName("saturday")
            val saturday: Int,
            @SerializedName("sun_from")
            val sunFrom: String,
            @SerializedName("sun_to")
            val sunTo: String,
            @SerializedName("sunday")
            val sunday: Int,
            @SerializedName("thurs_from")
            val thursFrom: String,
            @SerializedName("thurs_to")
            val thursTo: String,
            @SerializedName("thursday")
            val thursday: Int,
            @SerializedName("tues_from")
            val tuesFrom: String,
            @SerializedName("tues_to")
            val tuesTo: String,
            @SerializedName("tuesday")
            val tuesday: Int,
            @SerializedName("wed_from")
            val wedFrom: String,
            @SerializedName("wed_to")
            val wedTo: String,
            @SerializedName("wednesday")
            val wednesday: Int
        )
    }
}