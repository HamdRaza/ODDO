package com.tom.chef.network.app_repository

import com.tom.chef.models.OrderListResponse
import com.tom.chef.models.ResponseSuccess
import com.tom.chef.models.auth.*
import com.tom.chef.models.profile.*
import com.tom.chef.network.ApiServiceImple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiServiceImple: ApiServiceImple) {

    // FOR LOGIN API
    fun loginAPI(requestLogIn: RequestLogIn): Flow<ResponseChefLogIn> = flow {
        try {
            emit(apiServiceImple.loginAPI(requestLogIn))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun signUpAPI(requestSignUp: RequestSignUp): Flow<ResponseChefLogIn> = flow {
        try {
            emit(apiServiceImple.signUpAPI(requestSignUp))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun signUp2API(requestSignUp2: RequestSignUp2): Flow<ResponseChefLogIn> = flow {
        try {
            emit(apiServiceImple.signUp2API(requestSignUp2))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun resendPhoneOTP(user_id: String): Flow<ResponseSuccess> = flow {
        try {
            emit(apiServiceImple.resendPhoneOTP(user_id = user_id))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun verifyPhoneOTP(user_id: String, otp: String): Flow<ResponseOTPVerify> = flow {
        try {
            emit(apiServiceImple.verifyPhoneOTP(user_id = user_id, otp = otp))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun forgotPassword(requestResetPassword: RequestResetPassword): Flow<ResponseForgotPassword> =
        flow {
            try {
                emit(apiServiceImple.forgotPassword(requestResetPassword))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)

    fun resendForgotPasswordOTP(postResendForgotPasswordOTP: PostResendForgotPasswordOTP): Flow<ResponseSuccess> =
        flow {
            try {
                emit(apiServiceImple.resendForgotPasswordOTP(postResendForgotPasswordOTP))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)

    fun resetPassword(postResetPasssword: PostResetPasssword): Flow<ResponseSuccess> = flow {
        try {
            emit(apiServiceImple.resetPassword(postResetPasssword))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }.flowOn(Dispatchers.IO)

    fun googleLogIn(requestGoogleLogIn: RequestGoogleLogIn): Flow<ResponseGoogleLogIn> = flow {
        try {
            emit(apiServiceImple.googleLogin(requestGoogleLogIn))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun userByToken(token: String): Flow<ResponseGoogleLogIn> = flow {
        try {
            emit(apiServiceImple.userByToken(token = token))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun getCountries(): Flow<ResponseCountries> = flow {
        try {
            emit(apiServiceImple.getCountries())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)


    fun myProfile(): Flow<ResponseProfile> = flow {
        try {
            emit(apiServiceImple.myProfile())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)


    fun updateProfile(requestUpdateProfile: RequestUpdateProfile): Flow<ResponseProfileUpdate> =
        flow {
            try {
                emit(apiServiceImple.updateProfile(requestUpdateProfile))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)

    fun changePassword(oldPassword: String, newPasword: String): Flow<ResponseSuccess> = flow {
        try {
            emit(apiServiceImple.changePassword(oldPassword = oldPassword, newPasword = newPasword))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun orderListAPI(
        order_type: String,
        page: String,
        limit: String,
        order_status: String,
        user_timezone: String
    ): Flow<OrderListResponse> = flow {
        try {
            emit(
                apiServiceImple.orderListAPI(
                    order_type = order_type,
                    page = page,
                    limit = limit,
                    order_status = order_status,
                    user_timezone = user_timezone
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

}