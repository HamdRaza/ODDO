package com.tom.chef.models

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

    @field:SerializedName("oData")
    val oData: OData,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("errors")
    val errors: Errors,

    @field:SerializedName("status")
    val status: String
) {


    data class ChefCuisinesItem(

        @field:SerializedName("cuisine_name")
        val cuisineName: String,

        @field:SerializedName("cuisine_id")
        val cuisineId: Int
    )

    data class ChefTiming(

        @field:SerializedName("mon_to")
        val monTo: String,

        @field:SerializedName("saturday")
        val saturday: Int,

        @field:SerializedName("wed_from")
        val wedFrom: Any,

        @field:SerializedName("sat_from")
        val satFrom: String,

        @field:SerializedName("thursday")
        val thursday: Int,

        @field:SerializedName("wed_to")
        val wedTo: String,

        @field:SerializedName("thurs_from")
        val thursFrom: String,

        @field:SerializedName("thurs_to")
        val thursTo: String,

        @field:SerializedName("mon_from")
        val monFrom: String,

        @field:SerializedName("sun_to")
        val sunTo: String,

        @field:SerializedName("sunday")
        val sunday: Int,

        @field:SerializedName("tuesday")
        val tuesday: Int,

        @field:SerializedName("tues_from")
        val tuesFrom: String,

        @field:SerializedName("tues_to")
        val tuesTo: String,

        @field:SerializedName("fri_to")
        val friTo: String,

        @field:SerializedName("wednesday")
        val wednesday: Int,

        @field:SerializedName("friday")
        val friday: Int,

        @field:SerializedName("fri_from")
        val friFrom: String,

        @field:SerializedName("sun_from")
        val sunFrom: String,

        @field:SerializedName("sat_to")
        val satTo: String,

        @field:SerializedName("monday")
        val monday: Int
    )

    data class Errors(
        val any: Any? = null
    )

    data class OData(

        @field:SerializedName("preparation_unit")
        val preparationUnit: Int? = null,

        @field:SerializedName("bank_branch")
        val bankBranch: String? = null,

        @field:SerializedName("emirates_id")
        val emiratesId: String? = null,

        @field:SerializedName("latitude")
        val latitude: String? = null,

        @field:SerializedName("available")
        val available: Int? = null,

        @field:SerializedName("firebase_user_key")
        val firebaseUserKey: String,

        @field:SerializedName("account_no")
        val accountNo: String? = null,

        @field:SerializedName("building")
        val building: String? = null,

        @field:SerializedName("about_me")
        val aboutMe: String? = null,

        @field:SerializedName("passport_id")
        val passportId: String? = null,

        @field:SerializedName("admin_commission")
        val adminCommission: String? = null,

        @field:SerializedName("delivery_fee")
        val deliveryFee: String? = null,

        @field:SerializedName("dial_code")
        val dialCode: String,

        @field:SerializedName("street")
        val street: String? = null,

        @field:SerializedName("bank_id")
        val bankId: Int? = null,

        @field:SerializedName("preparation_time")
        val preparationTime: String? = null,

        @field:SerializedName("id")
        val id: Int,

        @field:SerializedName("cover_image")
        val coverImage: String? = null,

        @field:SerializedName("allow_ordertype")
        val allowOrdertype: Int? = null,

        @field:SerializedName("landmark")
        val landmark: String? = null,

        @field:SerializedName("ifsc")
        val ifsc: String? = null,

        @field:SerializedName("first_name")
        val firstName: String,

        @field:SerializedName("email")
        val email: String,

        @field:SerializedName("longitude")
        val longitude: String? = null,

        @field:SerializedName("swift")
        val swift: String? = null,

        @field:SerializedName("image")
        val image: String? = null,

        @field:SerializedName("last_name")
        val lastName: String,

        @field:SerializedName("bank_account_proof")
        val bankAccountProof: String? = null,

        @field:SerializedName("visa_copy")
        val visaCopy: String? = null,

        @field:SerializedName("chef_cuisines")
        val chefCuisines: List<ChefCuisinesItem>? = null,

        @field:SerializedName("trade_license")
        val tradeLicense: Any? = null,

        @field:SerializedName("benificiary")
        val benificiary: String? = null,

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("chef_timing")
        val chefTiming: ChefTiming? = null,

        @field:SerializedName("phone_number")
        val phoneNumber: String,

        @field:SerializedName("location")
        val location: String? = null,

        @field:SerializedName("country_id")
        val countryId: Int? = null
    )
}
