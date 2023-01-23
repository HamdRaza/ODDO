package com.tom.chef.ui.location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.tom.chef.R
import com.tom.chef.databinding.FragmentProfilePickLocationBinding
import com.tom.chef.models.LocationRequest
import com.tom.chef.models.ProfileResponse2
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.location.LocationInterface
import com.tom.chef.ui.comman.location.LocationViewModel
import com.tom.chef.ui.dashboard.toolBar.ToolBarInterface
import com.tom.chef.ui.dialogs.ConfirmDialogInterface
import com.tom.chef.ui.dialogs.ConfirmDialogViewModel
import com.tom.chef.ui.dialogs.ConfirmationDialog
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LocationPickerActivity : BaseActivity(), LocationPickerInterface, ToolBarInterface {
    private lateinit var binding: FragmentProfilePickLocationBinding

    lateinit var vm: LocationPickerViewModel

    private var map: GoogleMap? = null

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var locationViewModel: LocationViewModel

    var isSignup = false

    val appViewModel: AppViewModel by viewModels()

    private fun implementLocation() {
        locationViewModel = LocationViewModel(registry = activityResultRegistry, context = this)
        locationViewModel.locationInterface = object : LocationInterface {
            override fun locationProvided() {
                vm.startMap()
            }

            override fun showTurnOnLocation() {
                if (locationViewModel.dialogShowing) {
                    return
                }
                locationViewModel.dialogShowing = true

                val confirmDialogViewModel = ConfirmDialogViewModel()
                confirmDialogViewModel.locationPermissionRequired()
                ConfirmationDialog(viewModel = confirmDialogViewModel, object :
                    ConfirmDialogInterface {
                    override fun onNoClicked() {
                        finishAffinity()
                    }

                    override fun onYesClicked() {
                        this@LocationPickerActivity.openAppSystemSettings()
                    }
                }, isDissmissAble = false).show(supportFragmentManager, "Location")
            }
        }
        lifecycle.addObserver(locationViewModel)
    }

    fun LatLng.UpdateAddress() {
        vm.userLocation = this
        locationViewModel.getAddressOnBack(latLng = this) { address ->
            runOnUiThread {
                binding.location.setText(address)
            }
        }
    }


    companion object {
//        fun getIntent(context: Context): Intent {
//            return Intent(context, LocationPickerActivity::class.java)
//        }

        fun getIntent(
            context: Context,
            isSignup: Boolean,
            profileData: String?
        ): Intent {
            val intent = Intent(context, LocationPickerActivity::class.java)
            intent.putExtra("isSignup", isSignup)
            intent.putExtra("profileData", profileData)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_profile_pick_location)
        isSignup = intent.getBooleanExtra("isSignup", false)
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        implementLocation()
        vm = LocationPickerViewModel(this)
        binding.viewModel = vm
        vm.locationPickerInterface = this
        vm.init()

        var gson = Gson()
        val json = intent.getStringExtra("profileData")
        val profileData = gson.fromJson(json, ProfileResponse2.OData::class.java)
        setupListeners(profileData)
    }


    override fun initWindow() {
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        binding.location.paint?.isUnderlineText = true
    }

    override fun onBackClicked() {
        finish()
    }

    private fun setupListeners(profileData: ProfileResponse2.OData) {
        vm.updateProfile(profileData)
    }

    override fun startMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        startAnim()
        mapFragment.getMapAsync { map ->
            this.map = map
            stopAnim()
            map.setOnCameraIdleListener {
                map.cameraPosition.target.UpdateAddress()
            }
            locationViewModel.userLocationUpdate.observe(this) {
                it?.let {
                    locationViewModel.userLocationUpdate.removeObservers(this)
                    vm.moveCameraAt(location = it, map = map)
                }
            }
        }

    }

    override fun moveCameraAt(location: LatLng, map: GoogleMap) {
        vm.userLocation = location
        if (vm.moveCamera) {
            vm.moveCamera = false
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onAddAddressClicked() {
        if (isSignup) {
            returnResult()
        } else {
            if (validate()) {
                var locationRequest = LocationRequest(
                    access_token = sharedPreferenceManager.getAccessToken.toString(),
                    building = binding.building.text.toString(),
                    street = binding.streetAddress.text.toString(),
                    landmark = binding.landmark.text.toString(),
                    latitude = vm.userLocation!!.latitude.toString(),
                    longitude = vm.userLocation!!.longitude.toString(),
                    location = binding.location.text.toString(),
                    nick_name = binding.nickname.text.toString(),
                    apartment_no = binding.apartment.text.toString()
                )
                appViewModel.updateLocation(locationRequest)
                appViewModel.updateLocationLive.observe(this) {
                    if (it.status == "1") {
                        Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show()
                        returnResult()
                    } else {
                        Toast.makeText(this, "Error while updating", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        val valid = Validation
        if (valid.checkIsEmpty(binding.apartment)) {
            return false
        }
        if (valid.checkIsEmpty(binding.building)) {
            return false
        }
        if (valid.checkIsEmpty(binding.streetAddress)) {
            return false
        }
        if (valid.checkIsEmpty(binding.landmark)) {
            return false
        }
        if (valid.checkIsEmpty(binding.location)) {
            return false
        }
        if (valid.checkIsEmpty(binding.nickname)) {
            return false
        }
        return true
    }

    fun returnResult() {
        val bundle = Bundle()
        bundle.putString("lat", vm.userLocation!!.latitude.toString())
        bundle.putString("lng", vm.userLocation!!.longitude.toString())
        bundle.putString("address", binding.location.getLocalText())
        val resultIntent = Intent()
        resultIntent.putExtras(bundle)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun cancelClicked() {
        finish()
    }


}