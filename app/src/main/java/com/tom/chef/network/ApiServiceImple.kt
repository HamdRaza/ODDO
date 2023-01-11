package com.tom.chef.network

import com.tom.chef.models.ResponseSuccess
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
    suspend fun loginAPI(requestLogIn: RequestLogIn): ResponseChefLogIn {
        return apiService.loginAPI(
            email = requestLogIn.email,
            password = requestLogIn.password,
            fcm_token = requestLogIn.fcm_token,
        )
    }

    suspend fun signUpAPI(requestSignUp: RequestSignUp): ResponseChefLogIn {
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

    suspend fun signUp2API(requestSignUp2: RequestSignUp2): ResponseChefLogIn {
        return apiService.signUp2API(
            temp_id = requestSignUp2.temp_id,
            trade_license = requestSignUp2.trade_license,
            passport = requestSignUp2.passport,
            visa_copy = requestSignUp2.visa_copy,
            emirates_id = requestSignUp2.emirates_id,
            bank_account_proof = requestSignUp2.bank_account_proof,
            access_token = requestSignUp2.access_token,
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

    suspend fun changePassword(oldPassword: String, newPasword: String): ResponseSuccess {
        return apiService.changePassword(
            access_token = sharedPreferenceManager.getAccessToken.toString(),
            old_password = oldPassword,
            new_password = newPasword
        )
    }


}