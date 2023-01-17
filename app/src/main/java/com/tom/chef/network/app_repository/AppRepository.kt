package com.tom.chef.network.app_repository

import android.app.Activity
import com.tom.chef.models.*
import com.tom.chef.models.auth.*
import com.tom.chef.models.profile.*
import com.tom.chef.network.ApiServiceImple
import com.tom.chef.ui.auth.logIn.LoginActivity
import com.tom.chef.ui.dashboard.MainActivity
import com.tom.chef.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiServiceImple: ApiServiceImple) {

    // FOR LOGIN API
    fun loginAPI(requestLogIn: RequestLogIn): Flow<LoginResponse> = flow {
        try {
            emit(apiServiceImple.loginAPI(requestLogIn))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun signUpAPI(requestSignUp: RequestSignUp): Flow<Signup1Response> = flow {
        try {
            emit(apiServiceImple.signUpAPI(requestSignUp))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun signUp2API(requestSignUp2: RequestSignUp2): Flow<Signup2Response> = flow {
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

    fun getProfile(): Flow<ProfileResponse2> = flow {
        try {
            emit(apiServiceImple.getProfile())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun setAvailability(available: String): Flow<CommonResponse> = flow {
        try {
            emit(apiServiceImple.setAvailability(available))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun changePassword(
        current_password: String,
        new_password: String,
        confirm_password: String
    ): Flow<CommonResponse> = flow {
        try {
            emit(
                apiServiceImple.changePassword(
                    current_password = current_password,
                    new_password = new_password,
                    confirm_password = confirm_password
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun logout(): Flow<CommonResponse> = flow {
        try {
            emit(apiServiceImple.logout())
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

    fun getMenuList(
        contain_package: String
    ): Flow<DishListResponse> = flow {
        try {
            emit(
                apiServiceImple.getMenuList(
                    contain_package = contain_package
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun updateProfile(
        profileRequest: ProfileRequest
    ): Flow<CommonResponse> = flow {
        try {
            emit(
                apiServiceImple.updateProfile(
                    profileRequest = profileRequest
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun getOrderHistory(): Flow<OrderHistoryResponse> = flow {
        try {
            emit(
                apiServiceImple.getOrderHistory()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun getCuisineList(): Flow<CuisineResponse> = flow {
        try {
            emit(
                apiServiceImple.getCuisineList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    fun getOrderDetails(
        id: String
    ): Flow<OrderDetailsResponse> = flow {
        try {
            emit(
                apiServiceImple.getOrderDetails(
                    id = id
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

}