package com.tom.chef.network.app_view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tom.chef.app.SingleLiveEvent
import com.tom.chef.models.auth.*
import com.tom.chef.network.app_repository.AppRepository
import com.tom.chef.utils.SharedPreferenceManager
import com.tom.chef.utils.getOnlySuccess
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

    fun loginAPI(request: LogInRequest,callback: (LoginResponse) -> Unit){
        viewModelScope.launch {
            try {
                appRepository.loginAPI(request = request).collect {
                    getOnlySuccess(data = it,_tokenValidation){
                        callback.invoke(it)
                    }
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
    }
}
