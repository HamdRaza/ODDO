package com.tom.chef.network

import com.tom.chef.models.*
import com.tom.chef.models.auth.*
import com.tom.chef.models.profile.*
import com.tom.chef.utils.SharedPreferenceManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ApiServiceImple @Inject constructor(val apiService: ApiService) : BaseDataSource() {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    // FOR LOGIN USER
    suspend fun loginAPI(requestLogIn: RequestLogIn): LoginResponse {
        return apiService.loginAPI(
            email = requestLogIn.email,
            password = requestLogIn.password,
            fcm_token = requestLogIn.fcm_token,
        )
    }

    suspend fun signUpAPI(requestSignUp: RequestSignUp): Signup1Response {
        return apiService.signUpAPI(
            first_name = requestSignUp.first_name,
            last_name = requestSignUp.last_name,
            email = requestSignUp.email,
            dial_code = requestSignUp.dial_code,
            mobile = requestSignUp.mobile,
            address = requestSignUp.address,
            latitude = requestSignUp.latitude,
            longitude = requestSignUp.longitude,
            agree_terms = requestSignUp.agree_terms,
            password = requestSignUp.password,
            confirm_password = requestSignUp.confirm_password
        )
    }

    suspend fun signUp2API(requestSignUp2: RequestSignUp2): Signup2Response {
        return apiService.signUp2API(
            temp_id = requestSignUp2.temp_id,
            trade_license = requestSignUp2.trade_license,
            passport = requestSignUp2.passport,
            visa_copy = requestSignUp2.visa_copy,
            emirates_id = requestSignUp2.emirates_id,
            bank_account_proof = requestSignUp2.bank_account_proof,
            preparation_time = requestSignUp2.preparation_time,
            preparation_unit = requestSignUp2.preparation_unit,
        )
    }

    suspend fun resendPhoneOTP(user_id: String): ResponseSuccess {
        return apiService.resendPhoneOTP(user_id = user_id)
    }

    suspend fun verifyPhoneOTP(user_id: String, otp: String): ResponseOTPVerify {
        return apiService.verifyPhoneOTP(user_id = user_id, otp = otp)
    }


    suspend fun forgotPassword(requestResetPassword: RequestResetPassword): ResponseForgotPassword {
        return apiService.forgotPassword(
            phone = requestResetPassword.phone,
            email = requestResetPassword.email,
            type = requestResetPassword.type,
            dial_code = requestResetPassword.dial_code
        )
    }

    suspend fun resetPassword(postResetPasssword: PostResetPasssword): ResponseSuccess {
        return apiService.resetPassword(
            password = postResetPasssword.password,
            password_confirmation = postResetPasssword.password_confirmation,
            password_reset_code = postResetPasssword.password_reset_code,
            otp = postResetPasssword.otp
        )
    }

    suspend fun resendForgotPasswordOTP(postResendForgotPasswordOTP: PostResendForgotPasswordOTP): ResponseSuccess {
        return apiService.resendForgotPasswordOTP(
            password_reset_code = postResendForgotPasswordOTP.password_reset_code,
            type = postResendForgotPasswordOTP.type
        )
    }

    suspend fun googleLogin(requestGoogleLogIn: RequestGoogleLogIn): ResponseGoogleLogIn {
        return apiService.googleLogIn(
            name = requestGoogleLogIn.name,
            email = requestGoogleLogIn.email,
            fcm_token = requestGoogleLogIn.fcm_token
        )
    }

    suspend fun userByToken(token: String): ResponseGoogleLogIn {
        return apiService.userByToken(access_token = token)
    }

    suspend fun getCountries(): ResponseCountries {
        return apiService.getCountries()
    }

    suspend fun myProfile(): ResponseProfile {
        return apiService.myProfile(access_token = sharedPreferenceManager.getAccessToken.toString())
    }


    suspend fun updateProfile(requestUpdateProfile: RequestUpdateProfile): ResponseProfileUpdate {
        var body: MultipartBody.Part? = null
        requestUpdateProfile.user_image?.let {
            val file = File(it)
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            body = MultipartBody.Part.createFormData("user_image", file.name, requestFile)
        }

        val map: HashMap<String, RequestBody> = HashMap()
        map["first_name"] = createPartFromString(requestUpdateProfile.first_name)
        map["last_name"] = createPartFromString(requestUpdateProfile.last_name)


        return apiService.updateUserProfile(
            access_token = sharedPreferenceManager.getAccessToken.toString(),
            partMap = map,
            file = body
        )
    }


    private fun createPartFromString(s: String): RequestBody {
        return s.toRequestBody("text/plain".toMediaTypeOrNull())
    }


    suspend fun getProfile(): ProfileResponse {
        return apiService.getProfile(
            access_token = sharedPreferenceManager.getAccessToken.toString(),
        )
    }

    suspend fun setAvailability(available: String): CommonResponse {
        return apiService.setAvailability(
            access_token = sharedPreferenceManager.getAccessToken.toString(),
            available = available
        )
    }

    suspend fun changePassword(
        current_password: String,
        new_password: String,
        confirm_password: String
    ): CommonResponse {
        return apiService.changePassword(
            access_token = sharedPreferenceManager.getAccessToken.toString(),
            current_password = current_password,
            new_password = new_password,
            confirm_password = confirm_password,
        )
    }

    suspend fun logout(): CommonResponse {
        return apiService.logout(
            access_token = sharedPreferenceManager.getAccessToken.toString(),
        )
    }

    suspend fun orderListAPI(
        order_type: String,
        page: String,
        limit: String,
        order_status: String,
        user_timezone: String
    ): OrderListResponse {
        return apiService.orderListAPI(
            access_token = sharedPreferenceManager.getAccessToken.toString(),
            order_type = order_type,
            page = page,
            limit = limit,
            order_status = order_status,
            user_timezone = user_timezone
        )
    }

    suspend fun getMenuList(
        contain_package: String
    ): DishListResponse {
        if (contain_package == "All") {
            return apiService.getMenuListAll(
                access_token = sharedPreferenceManager.getAccessToken.toString()
            )
        }
        return apiService.getMenuList(
            access_token = sharedPreferenceManager.getAccessToken.toString(),
            contain_package = contain_package
        )
    }

    suspend fun getOrderHistory(): OrderHistoryResponse {
        return apiService.getOrderHistory(
            access_token = sharedPreferenceManager.getAccessToken.toString()
        )
    }

    suspend fun getOrderDetails(
        id: String
    ): OrderDetailsResponse {
        return apiService.getOrderDetails(
            access_token = sharedPreferenceManager.getAccessToken.toString(),
            id = id
        )
    }


}