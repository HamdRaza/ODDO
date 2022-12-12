package com.tom.chef.network

import com.tom.chef.models.ResponseSuccess
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
    @POST("auth/email_login")
    @FormUrlEncoded
    suspend fun loginAPI(
        @Field("user_device_type") device_type: String = Constants.DEVICE_TYPE,
        @Field("user_device_token") fcm_token: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): ResponseChefLogIn


    @POST("auth/signup")
    @FormUrlEncoded
    suspend fun signUpAPI(
        @Field("first_name") first_name: String ,
        @Field("last_name") last_name: String,
        @Field("dial_code") dial_code: String,
        @Field("phone") phone: String,
        @Field("device_type") device_type: String = Constants.DEVICE_TYPE,
        @Field("fcm_token") fcm_token: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ):ResponseChefLogIn

    @POST("auth/resend_phone_code")
    @FormUrlEncoded
    suspend fun resendPhoneOTP(
        @Field("user_id") user_id:String
    ):ResponseSuccess

    @POST("auth/confirm_phone_code")
    @FormUrlEncoded
    suspend fun verifyPhoneOTP(
      @Field("user_id") user_id:String,
      @Field("otp") otp:String
    ): ResponseOTPVerify


    @POST("auth/forgot_password")
    @FormUrlEncoded
    suspend fun forgotPassword(
        @Field("phone") phone:String="",
        @Field("email") email:String="",
        @Field("type") type:String="",
        @Field("dial_code") dial_code:String
    ): ResponseForgotPassword

    @POST("auth/reset_password")
    @FormUrlEncoded
    suspend fun resetPassword(
        @Field("password_reset_code") password_reset_code:String,
        @Field("password") password:String,
        @Field("password_confirmation") password_confirmation:String,
        @Field("otp") otp:String
    ):ResponseSuccess

    @POST("auth/resend_forgot_password_otp")
    @FormUrlEncoded
    suspend fun resendForgotPasswordOTP(
        @Field("password_reset_code") password_reset_code:String,
        @Field("type") type:String,
    ):ResponseSuccess


    @POST("auth/social_login")
    @FormUrlEncoded
    suspend fun googleLogIn(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("fcm_token") fcm_token:String,
        @Field("device_type") device_type:String=Constants.DEVICE_TYPE,
    ): ResponseGoogleLogIn


    @POST("auth/get_user_by_token")
    @FormUrlEncoded
    suspend fun userByToken(
        @Field("access_token") access_token:String
    ):ResponseGoogleLogIn


    @GET("countries")
    suspend fun getCountries(): ResponseCountries


    @POST("my_profile")
    @FormUrlEncoded
    suspend fun myProfile(
        @Field("access_token") access_token:String
    ): ResponseProfile


    @Multipart
    @POST("update_user_profile")
    suspend fun updateUserProfile(
        @Query(Constants.TOKEN_KEY) access_token: String,
        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part? = null
    ): ResponseProfileUpdate

    @POST("change_password")
    @FormUrlEncoded
    suspend fun changePassword(
        @Field(Constants.TOKEN_KEY) access_token: String,
        @Field("old_password") old_password:String,
        @Field("new_password") new_password:String
    ):ResponseSuccess

}

