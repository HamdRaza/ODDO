package com.tom.chef.network.app_view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tom.chef.app.SingleLiveEvent
import com.tom.chef.models.ResponseSuccess
import com.tom.chef.models.auth.*
import com.tom.chef.models.profile.RequestUpdateProfile
import com.tom.chef.models.profile.ResponseProfile
import com.tom.chef.models.profile.ResponseProfileUpdate
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
    private var _tokenValidation =SingleLiveEvent<String>()
    val tokenValidation:SingleLiveEvent<String>
         get() = _tokenValidation


    fun checkForAuth(forceLogin:Boolean=true):Boolean{
        if (sharedPreferenceManager.isLogedIn){
            return true
        }
        if (forceLogin){
            _tokenValidation.value="1005"
        }
        return false
    }



    // USE FOR LOGIN API

    fun loginAPI(logIn: RequestLogIn,callBack:(ResponseChefLogIn)->Unit) {
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
    fun signUpAPI(requestSignUp: RequestSignUp,callBack:(ResponseChefLogIn)->Unit){
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
    fun signUp2API(requestSignUp2: RequestSignUp2,callBack:(ResponseChefLogIn)->Unit){
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
    val resendOTPLiveDat:SingleLiveEvent<ResponseSuccess>
        get() = _resendOTPLiveData
    fun resendOTPAPI(user_id:String){
        viewModelScope.launch {
            try {
                appRepository.resendPhoneOTP(user_id = user_id).collect{
                    _resendOTPLiveData.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }


    // Use For Verify OTP
    private var _verifyOTPLiveData = SingleLiveEvent<ResponseOTPVerify>()
    val verifyOTPLiveDat:SingleLiveEvent<ResponseOTPVerify>
        get() = _verifyOTPLiveData
    fun verifyOTPAPI(user_id:String,otp:String){
        viewModelScope.launch {
            try {
                appRepository.verifyPhoneOTP(user_id = user_id, otp = otp).collect{
                    _verifyOTPLiveData.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }



    // Use For reset request
    private var _resetRequest = SingleLiveEvent<ResponseForgotPassword>()
    val resetRequest:SingleLiveEvent<ResponseForgotPassword>
        get() = _resetRequest
    fun resetRequest(requestResetPassword: RequestResetPassword){
        viewModelScope.launch {
            try {
                appRepository.forgotPassword(requestResetPassword).collect{
                    _resetRequest.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }



    // Use For resend OTP Forgot password
    private var _resendOTPForgot = SingleLiveEvent<ResponseSuccess>()
    val resendOTPForgot:SingleLiveEvent<ResponseSuccess>
        get() = _resendOTPForgot
    fun resendOTPForgotPassword(resendForgotPasswordOTP: PostResendForgotPasswordOTP){
        viewModelScope.launch {
            try {
                appRepository.resendForgotPasswordOTP(resendForgotPasswordOTP).collect{
                    _resendOTPForgot.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }




    // Use For Verify OTP and set new password forgot password
    private var _resetPassowrdForgot = SingleLiveEvent<ResponseSuccess>()
    val resetPassowrdForgot:SingleLiveEvent<ResponseSuccess>
        get() = _resetPassowrdForgot
    fun verifyOTPAPI(postResetPasssword: PostResetPasssword){
        viewModelScope.launch {
            try {
                appRepository.resetPassword(postResetPasssword).collect{
                    _resetPassowrdForgot.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }



    // Use For google login
    private var _googleLogin = SingleLiveEvent<ResponseGoogleLogIn>()
    val googleLogIn:SingleLiveEvent<ResponseGoogleLogIn>
        get() = _googleLogin
    fun googleLogIn(requestGoogleLogIn: RequestGoogleLogIn){
        viewModelScope.launch {
            try {
                appRepository.googleLogIn(requestGoogleLogIn).collect{
                    _googleLogin.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }



    // User by token
    private var _userByToken = SingleLiveEvent<ResponseGoogleLogIn>()
    val userByToken:SingleLiveEvent<ResponseGoogleLogIn>
        get() = _userByToken
    fun getUserByToken(token:String){
        viewModelScope.launch {
            try {
                appRepository.userByToken(token = token).collect{
                    _userByToken.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }



    // Use for countries list
    private var _countries = SingleLiveEvent<ResponseCountries>()
    val countries:SingleLiveEvent<ResponseCountries>
        get() = _countries
    fun getAllCountries(){
        viewModelScope.launch {
            try {
                appRepository.getCountries().collect{
                    _countries.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }


    // Use for get my Profile
    private var _myProfile = SingleLiveEvent<ResponseProfile>()
    val myProfile:SingleLiveEvent<ResponseProfile>
        get() = _myProfile
    fun getMyProfile(){
        if (!checkForAuth(forceLogin = false)){
            return
        }
        viewModelScope.launch {
            try {
                appRepository.myProfile().collect{
                    _myProfile.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }



    // Use for profile Update
    private var _updateProfileLive = SingleLiveEvent<ResponseProfileUpdate>()
    val updateProfileLive:SingleLiveEvent<ResponseProfileUpdate>
        get() = _updateProfileLive
    fun updateProfile(requestUpdateProfile: RequestUpdateProfile){
        viewModelScope.launch {
            try {
                appRepository.updateProfile(requestUpdateProfile).collect{
                    _updateProfileLive.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }




    // Use for change user password
    private var _changePasswordLive = SingleLiveEvent<ResponseSuccess>()
    val changePasswordLive:SingleLiveEvent<ResponseSuccess>
        get() = _changePasswordLive
    fun changePassword(oldPassword:String,newPassword:String){
        viewModelScope.launch {
            try {
                appRepository.changePassword(oldPassword = oldPassword, newPasword = newPassword).collect{
                    _changePasswordLive.value=it
                }
            }catch (e:HttpException){
                e.printStackTrace()
                manageApiError(e.code().toString(),e.message.toString())
            }
        }
    }






        // Manage API Response Exception
    fun manageApiError(errorCode: String, errorMessage: String){
         Log.i("error",errorMessage)
        _tokenValidation.value=errorCode
        /*
        Log.i("error",errorMessage)
        val myIntent = Intent("API-HITTING")
        myIntent.putExtra("code", errorCode)
        myIntent.putExtra("message", errorMessage)
        myIntent.putExtra("action", "EXPIRE")
        (DataFactory.baseActivity)?.sendBroadcast(myIntent)*/
    }
}
