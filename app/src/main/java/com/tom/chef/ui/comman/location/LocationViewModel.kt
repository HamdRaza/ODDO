package com.tom.chef.ui.comman.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.LOCATION_SERVICE
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import java.lang.Exception
import java.util.HashMap

class LocationViewModel(private val registry : ActivityResultRegistry,val context: Activity) :DefaultLifecycleObserver {

    lateinit var locationInterface: LocationInterface

    var dialogShowing=false
    var settingsOpened=false

    lateinit var locationPermissionRequest : ActivityResultLauncher<Array<String>>

    var userLocationUpdate    = MutableLiveData<LatLng>(null)

    lateinit var launcher :ActivityResultLauncher<IntentSenderRequest>


    lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    lateinit var locationManager: LocationManager

    lateinit var owner: LifecycleOwner
    override fun onCreate(owner: LifecycleOwner) {
        this.owner=owner

        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0.locations.firstOrNull()?.let {
                    userLocationUpdate.value=LatLng(it.latitude,it.longitude)
                    stopLocationUpdate()
                }
            }
        }


        locationPermissionRequest = registry.register("permission", owner, ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if ((ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)){
                checkForLocationService()
                return@register
            }
            permissionMissing()
        }

        launcher = registry.register("key", owner, ActivityResultContracts.StartIntentSenderForResult()){
            checkForLocationPermission()
        }

        userLocationUpdate.observe(owner){
            it?.let {
                locationInterface.locationProvided()
                userLocationUpdate.removeObservers(owner)
                stopLocationUpdate()
            }
        }
        checkForLocationPermission()
    }


    fun checkForLocationPermission() {
        if ((ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)&&(ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)){
            locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
            return
        }
        checkForLocationService()
    }




    fun checkForLocationService() {


        val locationRequest = LocationRequest.create()
        locationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 30 * 1000.toLong()
            fastestInterval = 5 * 1000.toLong()
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result= LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())
        result.addOnCompleteListener {
            try {
                val response: LocationSettingsResponse = it.getResult(ApiException::class.java)
                getLastKnownLocation()
            }catch (e: ApiException){
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val intentSenderRequest = IntentSenderRequest.Builder(e.status.resolution!!).build()
                        launcher.launch(intentSenderRequest)
                    } catch (e: IntentSender.SendIntentException) {
                    }
                }
            }
        }

    }


    fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionMissing()
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            location?.let {
                userLocationUpdate.value= LatLng(it.latitude,it.longitude)
            }
            startLocationUpdate()
        }
    }

    fun permissionMissing() {
       locationInterface.showTurnOnLocation()
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdate() {

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        stopLocationUpdate()
    }

    fun getAddressOnBack(latLng: LatLng,callback:(String)->Unit){
        Thread{
            kotlin.run {
               val address= getCurrentAddress(latLng)
                callback.invoke(address)
            }
        }.start()
    }


    fun getCurrentAddress(latLng: LatLng): String {
        var addresses: List<Address>
        var addressline = ""
        val geocoder = Geocoder(context)
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)!!
            if (addresses == null || addresses.isEmpty()) {
                // Mygeocoder is a class with a http request to google server, that replaces Geocoder, if not work
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)!!
            }
            var itemAddress: HashMap<*, *>
            val itemList: java.util.ArrayList<*> = java.util.ArrayList<HashMap<String, String>>()
            Log.d("Addresses", "" + "Start to print the ArrayList")
            for (i in addresses.indices) {
                itemAddress = HashMap<String, String>()
                val address = addresses[i]
                //               addressline = "Addresses from getAddressLine(): ";
                for (n in 0..address.maxAddressLineIndex) {
                    addressline += address.getAddressLine(n) + ", "
                }

            }
            if (addresses.size > 0) {
            }
        } catch (e: Exception) {
        }
        return addressline
    }


}