package com.tom.chef.network.app_view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tom.chef.app.SingleLiveEvent
import com.tom.chef.models.*
import com.tom.chef.models.auth.*
import com.tom.chef.models.profile.ResponseProfile
import com.tom.chef.network.app_repository.AppRepository
import com.tom.chef.utils.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager


    //Use for token validation
    private var _tokenValidation = SingleLiveEvent<String>()
    val tokenValidation: SingleLiveEvent<String>
        get() = _tokenValidation


    fun checkForAuth(forceLogin: Boolean = true): Boolean {
        if (sharedPreferenceManager.isLogedIn) {
            return true
        }
        if (forceLogin) {
            _tokenValidation.value = "1005"
        }
        return false
    }


    // USE FOR LOGIN API

    fun loginAPI(logIn: RequestLogIn, callBack: (LoginResponse) -> Unit) {
        viewModelScope.launch {
            try {
                appRepository.loginAPI(logIn).collect {
                    callBack.invoke(it)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // USE For SignUp API
    fun signUpAPI(requestSignUp: RequestSignUp, callBack: (Signup1Response) -> Unit) {
        viewModelScope.launch {
            try {
                appRepository.signUpAPI(requestSignUp).collect {
                    callBack.invoke(it)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    fun signUp2API(requestSignUp2: RequestSignUp2, callBack: (Signup2Response) -> Unit) {
        viewModelScope.launch {
            try {
                appRepository.signUp2API(requestSignUp2).collect {
                    callBack.invoke(it)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // Use For Resend OTP
    private var _resendOTPLiveData = SingleLiveEvent<ResponseSuccess>()
    val resendOTPLiveDat: SingleLiveEvent<ResponseSuccess>
        get() = _resendOTPLiveData

    fun resendOTPAPI(user_id: String) {
        viewModelScope.launch {
            try {
                appRepository.resendPhoneOTP(user_id = user_id).collect {
                    _resendOTPLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // Use For Verify OTP
    private var _verifyOTPLiveData = SingleLiveEvent<ResponseOTPVerify>()
    val verifyOTPLiveDat: SingleLiveEvent<ResponseOTPVerify>
        get() = _verifyOTPLiveData

    fun verifyOTPAPI(user_id: String, otp: String) {
        viewModelScope.launch {
            try {
                appRepository.verifyPhoneOTP(user_id = user_id, otp = otp).collect {
                    _verifyOTPLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // Use For reset request
    private var _resetRequest = SingleLiveEvent<ResponseForgotPassword>()
    val resetRequest: SingleLiveEvent<ResponseForgotPassword>
        get() = _resetRequest

    fun resetRequest(requestResetPassword: RequestResetPassword) {
        viewModelScope.launch {
            try {
                appRepository.forgotPassword(requestResetPassword).collect {
                    _resetRequest.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // Use For resend OTP Forgot password
    private var _resendOTPForgot = SingleLiveEvent<ResponseSuccess>()
    val resendOTPForgot: SingleLiveEvent<ResponseSuccess>
        get() = _resendOTPForgot

    fun resendOTPForgotPassword(resendForgotPasswordOTP: PostResendForgotPasswordOTP) {
        viewModelScope.launch {
            try {
                appRepository.resendForgotPasswordOTP(resendForgotPasswordOTP).collect {
                    _resendOTPForgot.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // Use For Verify OTP and set new password forgot password
    private var _resetPassowrdForgot = SingleLiveEvent<ResponseSuccess>()
    val resetPassowrdForgot: SingleLiveEvent<ResponseSuccess>
        get() = _resetPassowrdForgot

    fun verifyOTPAPI(postResetPasssword: PostResetPasssword) {
        viewModelScope.launch {
            try {
                appRepository.resetPassword(postResetPasssword).collect {
                    _resetPassowrdForgot.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // Use For google login
    private var _googleLogin = SingleLiveEvent<ResponseGoogleLogIn>()
    val googleLogIn: SingleLiveEvent<ResponseGoogleLogIn>
        get() = _googleLogin

    fun googleLogIn(requestGoogleLogIn: RequestGoogleLogIn) {
        viewModelScope.launch {
            try {
                appRepository.googleLogIn(requestGoogleLogIn).collect {
                    _googleLogin.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // User by token
    private var _userByToken = SingleLiveEvent<ResponseGoogleLogIn>()
    val userByToken: SingleLiveEvent<ResponseGoogleLogIn>
        get() = _userByToken

    fun getUserByToken(token: String) {
        viewModelScope.launch {
            try {
                appRepository.userByToken(token = token).collect {
                    _userByToken.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // Use for countries list
    private var _countries = SingleLiveEvent<ResponseCountries>()
    val countries: SingleLiveEvent<ResponseCountries>
        get() = _countries

    fun getAllCountries() {
        viewModelScope.launch {
            try {
                appRepository.getCountries().collect {
                    _countries.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // Use for get my Profile
    private var _myProfile = SingleLiveEvent<ResponseProfile>()
    val myProfile: SingleLiveEvent<ResponseProfile>
        get() = _myProfile

    fun getMyProfile() {
        if (!checkForAuth(forceLogin = false)) {
            return
        }
        viewModelScope.launch {
            try {
                appRepository.myProfile().collect {
                    _myProfile.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // Use for profile Update
//    private var _updateProfileLive = SingleLiveEvent<ResponseProfileUpdate>()
//    val updateProfileLive: SingleLiveEvent<ResponseProfileUpdate>
//        get() = _updateProfileLive
//
//    fun updateProfile(requestUpdateProfile: RequestUpdateProfile) {
//        viewModelScope.launch {
//            try {
//                appRepository.updateProfile(requestUpdateProfile).collect {
//                    _updateProfileLive.value = it
//                }
//            } catch (e: HttpException) {
//                e.printStackTrace()
//                manageApiError(e.code().toString(), e.message.toString())
//            }
//        }
//    }


    // Use for change user password
    private var _changePasswordLive = SingleLiveEvent<CommonResponse>()
    val changePasswordLive: SingleLiveEvent<CommonResponse>
        get() = _changePasswordLive

    fun changePassword(
        current_password: String,
        new_password: String,
        confirm_password: String
    ) {
        viewModelScope.launch {
            try {
                appRepository.changePassword(
                    current_password = current_password,
                    new_password = new_password,
                    confirm_password = confirm_password
                )
                    .collect {
                        _changePasswordLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _getProfileLive = SingleLiveEvent<ProfileResponse2>()
    val getProfileLive: SingleLiveEvent<ProfileResponse2>
        get() = _getProfileLive

    fun getProfile() {
        viewModelScope.launch {
            try {
                appRepository.getProfile()
                    .collect {
                        _getProfileLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _getFaqLive = SingleLiveEvent<FaqResponse>()
    val getFaqLive: SingleLiveEvent<FaqResponse>
        get() = _getFaqLive

    fun getFaq() {
        viewModelScope.launch {
            try {
                appRepository.getFaq()
                    .collect {
                        _getFaqLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _acceptOrderLive = SingleLiveEvent<CommonResponse2>()
    val acceptOrderLive: SingleLiveEvent<CommonResponse2>
        get() = _acceptOrderLive

    fun acceptOrder(order_id: String) {
        viewModelScope.launch {
            try {
                appRepository.acceptOrder(order_id)
                    .collect {
                        _acceptOrderLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _acceptOrder2Live = SingleLiveEvent<CommonResponse2>()
    val acceptOrder2Live: SingleLiveEvent<CommonResponse2>
        get() = _acceptOrder2Live

    fun acceptOrder2(order_id: String) {
        viewModelScope.launch {
            try {
                appRepository.acceptOrder2(order_id)
                    .collect {
                        _acceptOrder2Live.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _rejectOrderLive = SingleLiveEvent<CommonResponse2>()
    val rejectOrderLive: SingleLiveEvent<CommonResponse2>
        get() = _rejectOrderLive

    fun rejectOrder(order_id: String, reason: String) {
        viewModelScope.launch {
            try {
                appRepository.rejectOrder(order_id, reason)
                    .collect {
                        _rejectOrderLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _foodReadyLive = SingleLiveEvent<CommonResponse2>()
    val foodReadyLive: SingleLiveEvent<CommonResponse2>
        get() = _foodReadyLive

    fun foodReady(order_id: String) {
        viewModelScope.launch {
            try {
                appRepository.foodReady(order_id)
                    .collect {
                        _foodReadyLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _toggleStatusLive = SingleLiveEvent<CommonResponse>()
    val toggleStatusLive: SingleLiveEvent<CommonResponse>
        get() = _toggleStatusLive

    fun toggleStatus(id: String) {
        viewModelScope.launch {
            try {
                appRepository.toggleStatus(id)
                    .collect {
                        _toggleStatusLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _earningWithdrawLive = SingleLiveEvent<CommonResponse>()
    val earningWithdrawLive: SingleLiveEvent<CommonResponse>
        get() = _earningWithdrawLive

    fun earningWithdraw(order_id: String) {
        viewModelScope.launch {
            try {
                appRepository.earningWithdraw(order_id)
                    .collect {
                        _earningWithdrawLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _getEarningsLive = SingleLiveEvent<FinancialResponse>()
    val getEarningsLive: SingleLiveEvent<FinancialResponse>
        get() = _getEarningsLive

    fun getEarnings() {
        viewModelScope.launch {
            try {
                appRepository.getEarnings()
                    .collect {
                        _getEarningsLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _setAvailabilityLive = SingleLiveEvent<CommonResponse>()
    val setAvailabilityLive: SingleLiveEvent<CommonResponse>
        get() = _setAvailabilityLive

    fun setAvailability(available: String) {
        viewModelScope.launch {
            try {
                appRepository.setAvailability(available)
                    .collect {
                        _setAvailabilityLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _logoutLive = SingleLiveEvent<CommonResponse>()
    val logoutLive: SingleLiveEvent<CommonResponse>
        get() = _logoutLive

    fun logout() {
        viewModelScope.launch {
            try {
                appRepository.logout()
                    .collect {
                        _logoutLive.value = it
                    }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _orderListLive = SingleLiveEvent<OrderListResponse>()
    val orderListLive: SingleLiveEvent<OrderListResponse>
        get() = _orderListLive

    fun orderListAPI(
        order_type: String,
        page: String,
        limit: String,
        order_status: String,
        user_timezone: String
    ) {
        viewModelScope.launch {
            try {
                appRepository.orderListAPI(
                    order_type = order_type,
                    page = page,
                    limit = limit,
                    order_status = order_status,
                    user_timezone = user_timezone
                ).collect {
                    _orderListLive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    private var _getDishListLive = SingleLiveEvent<DishListResponse>()
    val getDishListLive: SingleLiveEvent<DishListResponse>
        get() = _getDishListLive

    fun getDishList(
        contain_package: String
    ) {
        viewModelScope.launch {
            try {
                appRepository.getDishList(
                    contain_package = contain_package
                ).collect {
                    _getDishListLive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _updateLocationLive = SingleLiveEvent<CommonResponse2>()
    val updateLocationLive: SingleLiveEvent<CommonResponse2>
        get() = _updateLocationLive

    fun updateLocation(
        locationRequest: LocationRequest
    ) {
        viewModelScope.launch {
            try {
                appRepository.updateLocation(
                    locationRequest = locationRequest
                ).collect {
                    _updateLocationLive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _updateProfileAPILive = SingleLiveEvent<CommonResponse>()
    val updateProfileAPILive: SingleLiveEvent<CommonResponse>
        get() = _updateProfileAPILive

    fun updateProfileAPI(
        profileRequest: ProfileRequest
    ) {
        viewModelScope.launch {
            try {
                appRepository.updateProfile(
                    profileRequest = profileRequest
                ).collect {
                    _updateProfileAPILive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _addDishLive = SingleLiveEvent<CommonResponse2>()
    val addDishLive: SingleLiveEvent<CommonResponse2>
        get() = _addDishLive

    fun addDish(
        addDishRequest: AddDishRequest
    ) {
        viewModelScope.launch {
            try {
                appRepository.addDish(
                    addDishRequest = addDishRequest
                ).collect {
                    _addDishLive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _getOrderHistoryLive = SingleLiveEvent<OrderHistoryResponse>()
    val getOrderHistoryLive: SingleLiveEvent<OrderHistoryResponse>
        get() = _getOrderHistoryLive

    fun getOrderHistory() {
        viewModelScope.launch {
            try {
                appRepository.getOrderHistory().collect {
                    _getOrderHistoryLive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _getCuisineListLive = SingleLiveEvent<CuisineResponse>()
    val getCuisineListLive: SingleLiveEvent<CuisineResponse>
        get() = _getCuisineListLive

    fun getCuisineList() {
        viewModelScope.launch {
            try {
                appRepository.getCuisineList().collect {
                    _getCuisineListLive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _getMenuLive = SingleLiveEvent<MenuResponse>()
    val getMenuLive: SingleLiveEvent<MenuResponse>
        get() = _getMenuLive

    fun getMenuList() {
        viewModelScope.launch {
            try {
                appRepository.getMenuList().collect {
                    _getMenuLive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _getOrderDetailsLive = SingleLiveEvent<OrderDetailsResponse>()
    val getOrderDetailsLive: SingleLiveEvent<OrderDetailsResponse>
        get() = _getOrderDetailsLive

    fun getOrderDetails(
        id: String
    ) {
        viewModelScope.launch {
            try {
                appRepository.getOrderDetails(
                    id = id
                ).collect {
                    _getOrderDetailsLive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _forgotPasswordAPILive = SingleLiveEvent<CommonResponse2>()
    val forgotPasswordAPILive: SingleLiveEvent<CommonResponse2>
        get() = _forgotPasswordAPILive

    fun forgotPasswordAPI(
        email: String
    ) {
        viewModelScope.launch {
            try {
                appRepository.forgotPasswordAPI(
                    email = email
                ).collect {
                    _forgotPasswordAPILive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _forgotPasswordOtpVerifyLive = SingleLiveEvent<ForgotPasswordOtpResponse>()
    val forgotPasswordOtpVerifyLive: SingleLiveEvent<ForgotPasswordOtpResponse>
        get() = _forgotPasswordOtpVerifyLive

    fun forgotPasswordOtpVerify(
        email: String,
        otp: String
    ) {
        viewModelScope.launch {
            try {
                appRepository.forgotPasswordOtpVerify(
                    email = email,
                    otp = otp
                ).collect {
                    _forgotPasswordOtpVerifyLive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    private var _resetPasswordAPILive = SingleLiveEvent<ForgotPasswordOtpResponse>()
    val resetPasswordAPILive: SingleLiveEvent<ForgotPasswordOtpResponse>
        get() = _resetPasswordAPILive

    fun resetPasswordAPI(
        access_token: String,
        password: String,
        confirm_password: String,
    ) {
        viewModelScope.launch {
            try {
                appRepository.resetPasswordAPI(
                    access_token = access_token,
                    password = password,
                    confirm_password = confirm_password,
                ).collect {
                    _resetPasswordAPILive.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // Manage API Response Exception
    fun manageApiError(errorCode: String, errorMessage: String) {
        Log.i("error", errorMessage)
        _tokenValidation.value = errorCode
        /*
        Log.i("error",errorMessage)
        val myIntent = Intent("API-HITTING")
        myIntent.putExtra("code", errorCode)
        myIntent.putExtra("message", errorMessage)
        myIntent.putExtra("action", "EXPIRE")
        (DataFactory.baseActivity)?.sendBroadcast(myIntent)*/
    }
}
