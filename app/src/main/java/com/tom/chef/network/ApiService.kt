package com.tom.chef.network

import com.tom.chef.models.*
import com.tom.chef.models.auth.*
import com.tom.chef.models.profile.ResponseProfile
import com.tom.chef.models.profile.ResponseProfileUpdate
import com.tom.chef.utils.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
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
        @Part("preparation_time") access_token: RequestBody,
        @Part("preparation_unit") preparation_unit: RequestBody
    ): CommonResponse

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

    @POST("chef/profile")
    @FormUrlEncoded
    suspend fun getProfile(
        @Field("access_token") access_token: String,
    ): ProfileResponse

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
    suspend fun getMenuList(
        @Field("access_token") access_token: String,
        @Field("contain_package") contain_package: String,
    ): DishListResponse

    @POST("chef/food_list")
    @FormUrlEncoded
    suspend fun getMenuListAll(
        @Field("access_token") access_token: String
    ): DishListResponse

    @POST("chef/order_history")
    @FormUrlEncoded
    suspend fun getOrderHistory(
        @Field("access_token") access_token: String
    ): OrderHistoryResponse

    @POST("chef/order_details")
    @FormUrlEncoded
    suspend fun getOrderDetails(
        @Field("access_token") access_token: String,
        @Field("id") id: String
    ): OrderDetailsResponse

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

