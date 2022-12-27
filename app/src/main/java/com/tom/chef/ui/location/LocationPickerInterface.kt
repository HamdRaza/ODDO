package com.tom.chef.ui.location

import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng


interface LocationPickerInterface {
    fun initWindow()
    fun moveCameraAt(location: LatLng, map: GoogleMap)
    fun startMap()
    fun onAddAddressClicked()
    fun cancelClicked()
}