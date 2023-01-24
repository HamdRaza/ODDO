package com.tom.chef.network

import com.google.gson.JsonObject
import com.tom.chef.models.*
import com.tom.chef.models.auth.*
import com.tom.chef.models.profile.ResponseProfile
import com.tom.chef.models.profile.ResponseProfileUpdate
import com.tom.chef.utils.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    // LOGIN API
    @POST("chef_login")
    @FormUrlEncoded
    suspend fun loginAPI(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("user_device_token") fcm_token: String,
        @Field("user_device_type") device_type: String = Constants.DEVICE_TYPE,
    ): LoginResponse

    //first_name:Chef
//last_name:Mb
//email:kiyara@gmail.com
//dial_code:971
//mobile:5465656
//address:safdsfds
//latitude:22.54545
//longitude:55.34454
//agree_terms:1
//password:Hello@1985
//confirm_password:Hello@1985
    @POST("chef_register")
    @FormUrlEncoded
    suspend fun signUpAPI(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("email") email: String,
        @Field("dial_code") dial_code: String,
        @Field("mobile") mobile: String,
        @Field("address") address: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("agree_terms") agree_terms: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String,
    ): Signup1Response

    //    temp_id:40
//    trade_license
//    passport
//    visa_copy
//    emirates_id
//    bank_account_proof
//    preparation_time:15
//    preparation_unit:mins,hour,day
    @Multipart
    @POST("chefregister_step2")
    suspend fun signUp2API(
        @Part("temp_id") temp_id: RequestBody,
        @Part trade_license: MultipartBody.Part?,
        @Part passport: MultipartBody.Part?,
        @Part visa_copy: MultipartBody.Part?,
        @Part emirates_id: MultipartBody.Part?,
        @Part bank_account_proof: MultipartBody.Part?,
        @Part("preparation_time") preparation_time: RequestBody,
        @Part("preparation_unit") preparation_unit: RequestBody
    ): Signup2Response

    //    access_token:{{access_token}}
//order_type:2
//page:1
//limit:15
////search_key:TOM-1000-10150
//order_status:1
////from_date:2022-10-05
////to_date:2022-10-08
//user_timezone:Asia/Kolkata
    @POST("chef/order_list")
    @FormUrlEncoded
    suspend fun orderListAPI(
        @Field("access_token") access_token: String,
        @Field("order_type") order_type: String,
        @Field("page") page: String,
        @Field("limit") limit: String,
        @Field("order_status") order_status: String,
        @Field("user_timezone") user_timezone: String,
    ): OrderListResponse

    //    Update location
//    access_token:{{access_token}}
//building:Saeed tower ,30 th floor
//street:dubai investment park
//landmark:Near Ramla Hypermarket
//latitude:27.54535
//longitude:57.235345
//location:dubai investment park
//nick_name:Chef Pillai
//apartment_no:#310 ,3rd floor
    @POST("chef/update_location")
    @FormUrlEncoded
    suspend fun updateLocation(
        @Field("access_token") access_token: String,
        @Field("building") building: String,
        @Field("street") street: String,
        @Field("landmark") landmark: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("location") location: String,
        @Field("nick_name") nick_name: String,
        @Field("apartment_no") apartment_no: String
    ): CommonResponse2


    @Multipart
    @POST("chef/update_profile")
    suspend fun updateProfile(
        @Part("access_token") access_token: RequestBody,
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("about_me") about_me: RequestBody,
        @Part("brand_name") brand_name: RequestBody,
        @Part image: MultipartBody.Part? = null,
        @Part cover_image: MultipartBody.Part? = null,
        @Part("preparation_unit") preparation_unit: RequestBody,
        @Part("preparation_time") preparation_time: RequestBody,
        @Query("cuisines[]") cuisines: ArrayList<String>,
        @Part("order_limit_per_hour") order_limit_per_hour: RequestBody,
        @Part("weekly_mode") weekly_mode: RequestBody,
        @Part("allow_ordertype") allow_ordertype: RequestBody,
        @Part("start_time") start_time: RequestBody,
        @Part("end_time") end_time: RequestBody
    ): CommonResponse

    //access_token:{{access_token}}
//name:Dosa
//name_ar:Dosa
//description:
//description_ar:
//regular_price:30
//sale_price:20
//sufficient_for:2
//quantity:3
//out_of_stock:0
//menu_id[]:1
//cuisine_id[]:1
//active:1
//image
//gallery[]
//contain_package:0
//package[]:{}
    @Multipart
    @POST("chef/add_dish")
    suspend fun addDish(
        @Part("access_token") access_token: RequestBody,
        @Part("name") name: RequestBody,
        @Part("name_ar") name_ar: RequestBody,
        @Part("description") description: RequestBody,
        @Part("description_ar") description_ar: RequestBody,
        @Part("regular_price") regular_price: RequestBody,
        @Part("sale_price") sale_price: RequestBody,
        @Part("sufficient_for") sufficient_for: RequestBody,
        @Part("quantity") quantity: RequestBody,
        @Part("out_of_stock") out_of_stock: RequestBody,//0
        @Query("menu_id[]") menu_id: ArrayList<String>,
        @Query("cuisine_id[]") cuisine_id: ArrayList<String>,
        @Part("active") active: RequestBody,//0
        @Part image: MultipartBody.Part? = null,
        @Part gallery: List<MultipartBody.Part>? = null,
        @Part("contain_package") contain_package: RequestBody,//0 or 1
        @Query("package[]") packageList: ArrayList<JsonObject>? = null
    ): CommonResponse2

    @POST("cms/faq")
    suspend fun getFaq(): FaqResponse

    @POST("chef/profile")
    @FormUrlEncoded
    suspend fun getProfile(
        @Field("access_token") access_token: String,
    ): ProfileResponse2

    @POST("chef/accept_order")
    @FormUrlEncoded
    suspend fun acceptOrder(
        @Field("access_token") access_token: String,
        @Field("order_id") order_id: String,
    ): CommonResponse2

    @POST("chef/acceptsecond")
    @FormUrlEncoded
    suspend fun acceptOrder2(
        @Field("access_token") access_token: String,
        @Field("order_id") order_id: String,
    ): CommonResponse2

    @POST("chef/reject_order")
    @FormUrlEncoded
    suspend fun rejectOrder(
        @Field("access_token") access_token: String,
        @Field("order_id") order_id: String,
        @Field("reason") reason: String,
    ): CommonResponse2

    @POST("chef/order_ready_for_delivery")
    @FormUrlEncoded
    suspend fun foodReady(
        @Field("access_token") access_token: String,
        @Field("order_id") order_id: String,
    ): CommonResponse2

    @POST("chef/toggle_stock_status")
    @FormUrlEncoded
    suspend fun toggleStatus(
        @Field("access_token") access_token: String,
        @Field("id") id: String,
    ): CommonResponse

    @POST("chef/earning_withdraw")
    @FormUrlEncoded
    suspend fun earningWithdraw(
        @Field("access_token") access_token: String,
        @Field("order_id") order_id: String,
    ): CommonResponse

    @POST("chef/earning")
    @FormUrlEncoded
    suspend fun getEarnings(
        @Field("access_token") access_token: String
    ): FinancialResponse

    @POST("user/update_available")
    @FormUrlEncoded
    suspend fun setAvailability(
        @Field("access_token") access_token: String,
        @Field("available") available: String
    ): CommonResponse

    @POST("change_password")
    @FormUrlEncoded
    suspend fun changePassword(
        @Field("access_token") access_token: String,
        @Field("current_password") current_password: String,
        @Field("new_password") new_password: String,
        @Field("confirm_password") confirm_password: String,
    ): CommonResponse

    @POST("logout")
    @FormUrlEncoded
    suspend fun logout(
        @Field("access_token") access_token: String,
    ): CommonResponse

    @POST("chef/food_list")
    @FormUrlEncoded
    suspend fun getDishList(
        @Field("access_token") access_token: String,
        @Field("contain_package") contain_package: String,
    ): DishListResponse

    @POST("chef/food_list")
    @FormUrlEncoded
    suspend fun getDishListAll(
        @Field("access_token") access_token: String
    ): DishListResponse

    @POST("chef/order_history")
    @FormUrlEncoded
    suspend fun getOrderHistory(
        @Field("access_token") access_token: String
    ): OrderHistoryResponse

    @POST("cuisine_list")
    @FormUrlEncoded
    suspend fun getCuisineList(
        @Field("access_token") access_token: String
    ): CuisineResponse

    @POST("menu_list")
    @FormUrlEncoded
    suspend fun getMenuList(
        @Field("access_token") access_token: String
    ): MenuResponse

    @POST("chef/order_details")
    @FormUrlEncoded
    suspend fun getOrderDetails(
        @Field("access_token") access_token: String,
        @Field("id") id: String
    ): OrderDetailsResponse

    @POST("forgot_password")
    @FormUrlEncoded
    suspend fun forgotPasswordAPI(
        @Field("email") email: String,
    ): CommonResponse2

    @POST("forgot_otp_verify")
    @FormUrlEncoded
    suspend fun forgotPasswordOtpVerify(
        @Field("email") email: String,
        @Field("otp") otp: String,
    ): ForgotPasswordOtpResponse

    @POST("reset_password")
    @FormUrlEncoded
    suspend fun resetPasswordAPI(
        @Field("access_token") access_token: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String
    ): ForgotPasswordOtpResponse


    @POST("auth/resend_phone_code")
    @FormUrlEncoded
    suspend fun resendPhoneOTP(
        @Field("user_id") user_id: String
    ): ResponseSuccess

    @POST("auth/confirm_phone_code")
    @FormUrlEncoded
    suspend fun verifyPhoneOTP(
        @Field("user_id") user_id: String,
        @Field("otp") otp: String
    ): ResponseOTPVerify


    @POST("auth/forgot_password")
    @FormUrlEncoded
    suspend fun forgotPassword(
        @Field("phone") phone: String = "",
        @Field("email") email: String = "",
        @Field("type") type: String = "",
        @Field("dial_code") dial_code: String
    ): ResponseForgotPassword

    @POST("auth/reset_password")
    @FormUrlEncoded
    suspend fun resetPassword(
        @Field("password_reset_code") password_reset_code: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("otp") otp: String
    ): ResponseSuccess

    @POST("auth/resend_forgot_password_otp")
    @FormUrlEncoded
    suspend fun resendForgotPasswordOTP(
        @Field("password_reset_code") password_reset_code: String,
        @Field("type") type: String,
    ): ResponseSuccess


    @POST("auth/social_login")
    @FormUrlEncoded
    suspend fun googleLogIn(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("fcm_token") fcm_token: String,
        @Field("device_type") device_type: String = Constants.DEVICE_TYPE,
    ): ResponseGoogleLogIn


    @POST("auth/get_user_by_token")
    @FormUrlEncoded
    suspend fun userByToken(
        @Field("access_token") access_token: String
    ): ResponseGoogleLogIn


    @GET("countries")
    suspend fun getCountries(): ResponseCountries


    @POST("my_profile")
    @FormUrlEncoded
    suspend fun myProfile(
        @Field("access_token") access_token: String
    ): ResponseProfile


    @Multipart
    @POST("update_user_profile")
    suspend fun updateUserProfile(
        @Query(Constants.TOKEN_KEY) access_token: String,
        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part? = null
    ): ResponseProfileUpdate

}

