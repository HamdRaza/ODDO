package com.tom.chef.ui.location

import android.location.Location
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.tom.chef.models.ProfileResponse2
import com.tom.chef.newBase.BaseActivity

class LocationPickerViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var locationPickerInterface: LocationPickerInterface

    fun init() {
        locationPickerInterface.initWindow()
    }

    var moveCamera = true
    var userLocation: LatLng? = null

    var userProfile = MutableLiveData<ProfileResponse2.OData>()

    @JvmField
    val locationAddress = ObservableField<String>()


    fun updateProfile(user: ProfileResponse2.OData) {
        locationAddress.set("${user.building}, ${user.street}, ${user.landmark}")
    }

    fun moveCameraAt(location: LatLng, map: GoogleMap) =
        locationPickerInterface.moveCameraAt(location, map)

    fun startMap() = locationPickerInterface.startMap()
    fun onAddAddressClicked() = locationPickerInterface.onAddAddressClicked()

    fun onCancelClicked() {
        locationPickerInterface.cancelClicked()
    }

}