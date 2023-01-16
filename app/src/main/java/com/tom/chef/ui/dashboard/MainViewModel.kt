package com.tom.chef.ui.dashboard

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.tom.chef.data.notifications.NotificationModel
import com.tom.chef.data.notifications.NotificationRepository
import com.tom.chef.models.ProfileResponse
import com.tom.chef.models.auth.ResponseCountries
import com.tom.chef.models.auth.User
import com.tom.chef.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val unUsed: NotificationRepository) : ViewModel() {

    lateinit var mainInterface: MainInterface

    @JvmField
    var showFilterSelector = ObservableField<Boolean>()

    fun hideFilterView() {
        showFilterSelector.set(false)
    }

    fun makeUserOnline() {
        mainInterface.updateUserStatus(boolean = true)
        hideFilterView()
    }

    fun makeUserOffline() {
        mainInterface.updateUserStatus(boolean = false)
        hideFilterView()
    }

    fun onBackButtonClicked() = mainInterface.onBackButtonClicked()

    var userProfile = MutableLiveData<ProfileResponse.OData>()
    var counries = MutableLiveData<ResponseCountries>()
    var userLocationUpdate = MutableLiveData<LatLng>(null)


    fun callMyProfileAPI() = mainInterface.callMyProfileAPI()
    fun getCountryCodes() = mainInterface.getCountryCodes()

    fun init() {
        callMyProfileAPI()
        getCountryCodes()
        mainInterface.implementListners()
        getAllNotifications()
        askNotificationPermission()
    }


    fun askNotificationPermission() = mainInterface.askNotificationPermission()


    private val _userAlNotifications = MutableLiveData<UiState<List<NotificationModel>>>()
    val userAlNotifications: LiveData<UiState<List<NotificationModel>>>
        get() = _userAlNotifications

    fun getAllNotifications() {
        unUsed.getAllNotifications(true) {
            _userAlNotifications.value = it
        }
    }

    fun clearAllNotifications() {
        unUsed.deleteAll()
    }


    fun checkForIntent() = mainInterface.checkForIntent()

    fun moveToLogIn() = mainInterface.moveToLogInScreen()

    fun onNotificationClicked() = mainInterface.onNotificationClicked()
    fun onAccountClicked() = mainInterface.onAccountClicked()

}