package com.tom.chef.ui.location

import android.location.Location
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.tom.chef.newBase.BaseActivity

class LocationPickerViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var locationPickerInterface: LocationPickerInterface

    fun init(){
        locationPickerInterface.initWindow()
    }

    var moveCamera=true
    var userLocation:LatLng?=null

    fun moveCameraAt(location: LatLng, map: GoogleMap)=locationPickerInterface.moveCameraAt(location,map)
    fun startMap()=locationPickerInterface.startMap()
    fun onAddAddressClicked()=locationPickerInterface.onAddAddressClicked()

    fun onCancelClicked(){
        locationPickerInterface.cancelClicked()
    }

}