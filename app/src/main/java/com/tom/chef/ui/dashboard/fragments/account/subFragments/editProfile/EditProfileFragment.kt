package com.tom.chef.ui.dashboard.fragments.account.subFragments.editProfile

import android.Manifest
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.permissionx.guolindev.PermissionX
import com.tom.chef.R
import com.tom.chef.databinding.FragmentHomeEditProfileBinding
import com.tom.chef.models.CuisineResponse
import com.tom.chef.models.ProfileRequest
import com.tom.chef.models.ProfileResponse2
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.profile.ProfileInterface
import com.tom.chef.ui.dashboard.fragments.account.AccountInterface
import com.tom.chef.ui.dashboard.fragments.account.AccountViewModel
import com.tom.chef.ui.location.LocationPickerActivity
import com.tom.chef.ui.location.LocationPickerViewModel
import com.tom.chef.utils.ReduceImageSize
import com.tom.chef.utils.SharedPreferenceManager
import com.tom.chef.utils.Validation
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class EditProfileFragment : BaseFragment(), ProfileInterface, AccountInterface,
    EditProfileInterface, PickiTCallbacks {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private lateinit var binding: FragmentHomeEditProfileBinding
    private lateinit var accountVM: AccountViewModel
    lateinit var locationPickerVM: LocationPickerViewModel

    private lateinit var editProfileViewModel: EditProfileViewModel

    val appViewModel: AppViewModel by viewModels()

    var allGranted = false

    var pickiT: PickiT? = null

    var isCover = false

    var imagePath = ""
    var coverPath = ""
    var selectedUnit = "mins"
    var cuisineList = ArrayList<CuisineResponse.OData>()

    var orderType = -1

    var address = ""
    var latitude = ""
    var longitude = ""

    var profileData: ProfileResponse2.OData? = null

    private val c = Calendar.getInstance()

    private var startHour = c.get(Calendar.HOUR_OF_DAY)
    private var startMinute = c.get(Calendar.MINUTE)

    private var endHour = c.get(Calendar.HOUR_OF_DAY)
    private var endMinute = c.get(Calendar.MINUTE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeEditProfileBinding.bind(
            inflater.inflate(
                R.layout.fragment_home_edit_profile,
                container,
                false
            )
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar Setup
        mActivity.toolbarVM.manageToolBar(showToolbar = false)
        accountVM = AccountViewModel(mActivity)
        accountVM.accountInterface = this
        binding.viewModel = accountVM

        locationPickerVM = LocationPickerViewModel(mActivity)

        editProfileViewModel = EditProfileViewModel()
        editProfileViewModel.editProfileInterface = this
        binding.editProfile = editProfileViewModel

        pickiT = PickiT(requireActivity(), this, requireActivity())
        setupListeners()
        PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request { allGranted, grantedList, deniedList ->
                this.allGranted = allGranted
                if (!allGranted) {
                    Toast.makeText(
                        requireActivity(),
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        binding?.topBackgroundImage?.setOnClickListener {
            changeProfile()
        }
        binding?.shapeableImageView?.setOnClickListener {
            changeBackground()
        }
        binding?.startTime?.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                requireActivity(),
                { view, hourOfDay, minute ->
                    binding?.startTime?.setText(
                        String.format(
                            "%02d",
                            Integer.parseInt("$hourOfDay")
                        ) + ":" + String.format("%02d", Integer.parseInt("$minute"))
                    )
                },
                startHour,
                startMinute,
                false
            )
            timePickerDialog.show()
        }
        binding?.endTime?.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                requireActivity(),
                { view, hourOfDay, minute ->
                    binding?.endTime?.setText(
                        String.format(
                            "%02d",
                            Integer.parseInt("$hourOfDay")
                        ) + ":" + String.format("%02d", Integer.parseInt("$minute"))
                    )
                },
                endHour,
                endMinute,
                false
            )
            timePickerDialog.show()
        }
    }

    private fun getCuisines() {
        appViewModel.getCuisineList()
        appViewModel.getCuisineListLive.observe(viewLifecycleOwner) {
            if (it.status == "1") {
                cuisineList = it.oData
                val cuisines = cuisineList.map { it.cuisineName }

                binding.selectCousine.items = cuisines

                profileData?.chefCuisines?.forEach {
                    cuisineList.forEachIndexed { index, cuisine ->
                        if (it.cuisineId == cuisine.id) {
                            binding.selectCousine.setSelection(index)
                        }
                    }
                }

                val type = arrayOf("mins", "hrs", "days")

                val adapter2 = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_list_item_1,
                    type
                )
                selectedUnit = profileData?.preparationUnit!!
                binding.selectUnit.setAdapter(adapter2)
                binding.selectUnit.setOnItemClickListener { adapterView, view, position, l ->
                    if (position == 0) {
                        selectedUnit = "mins"
                    } else if (position == 1) {
                        selectedUnit = "hour"
                    } else if (position == 2) {
                        selectedUnit = "day"
                    }
                }
                val order = arrayOf("Both", "Current", "Scheduled")

                val adapter3 = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_list_item_1,
                    order
                )

                orderType = when (profileData?.allowOrdertype) {
                    "0" -> 0
                    "1" -> 1
                    "2" -> 2
                    else -> 0
                }

                binding.selectType.setAdapter(adapter3)
                binding.selectType.setOnItemClickListener { adapterView, view, position, l ->
                    orderType = position
                }

            } else {
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun setupListeners() {
        mainActivity.vm.userProfile.observe(viewLifecycleOwner) {
            it?.let {
                profileData = it
                getCuisines()
                getTime()
                locationPickerVM.userProfile.value = it
                accountVM.updateProfile(it)
                locationPickerVM.updateProfile(it)
            }
        }
    }

    private fun getTime() {
        startHour = Integer.parseInt(profileData?.startTime?.split(":")?.get(0)!!)
        startMinute = Integer.parseInt(profileData?.startTime?.split(":")?.get(1)!!)
        endHour = Integer.parseInt(profileData?.endTime?.split(":")?.get(0)!!)
        endMinute = Integer.parseInt(profileData?.endTime?.split(":")?.get(1)!!)
    }

    override fun onResume() {
        super.onResume()
        appViewModel.getProfile()
        appViewModel.getProfileLive.observe(this) {
            stopAnim()
            if (it.status == "1") {
                mainActivity.vm.userProfile.value = it.oData
                sharedPreferenceManager.saveUser(it.oData, null)
            } else {
//                Toast.makeText(this, "Session Expired", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackClicked() {
        mainActivity.vm.onBackButtonClicked()
    }

    override fun changeBackground() {
        backgroundImage.launch("image/*")
    }

    override fun editLocation() {
        var gson = Gson()
        getLocation.launch(
            LocationPickerActivity.getIntent(
                requireContext(),
                false,
                gson.toJson(profileData)
            )
        )
    }

    override fun changeProfile() {
        profileImage.launch("image/*")
    }


    private val profileImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                isCover = false
                val imageUri = ReduceImageSize.compressImage(it, requireContext())
                accountVM.profilePath.set(imageUri)
                pickiT!!.getPath(imageUri, Build.VERSION.SDK_INT)
            }
        }
    private val backgroundImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                isCover = true
                val imageUri = ReduceImageSize.compressImage(it, requireContext())
                accountVM.coverImage.set(imageUri)
                pickiT!!.getPath(imageUri, Build.VERSION.SDK_INT)
            }
        }

    val getLocation = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it?.let { it ->
            it.data?.extras?.let { bundle ->
                address = bundle.getString("address")!!
                latitude = bundle.getString("lat")!!
                longitude = bundle.getString("lng")!!
            }
        }
    }

    override fun onSaveClicked() {
        if (address.isEmpty()) {
            address = accountVM.userAddress.get() as String
        }
        if (latitude.isEmpty()) {
            latitude = accountVM.latitude.get() as String
        }
        if (longitude.isEmpty()) {
            longitude = accountVM.longitude.get() as String
        }
        val imageFile = File(imagePath)
        val imageBody: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), imageFile)

        val coverFile = File(coverPath)
        val coverBody: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), coverFile)

        val name = binding.fullName.text
        var lastName = ""
        var firstName = ""
        if (name.split("\\w+".toRegex()).toTypedArray().size > 1) {
            lastName = name.substring(name.lastIndexOf(" ") + 1)
            firstName = name.substring(0, name.lastIndexOf(' '))
        } else {
            firstName = name.toString()
        }

        var isWeekly = if (binding?.txtWeekly?.isChecked!!) "1" else "0"
        var listCuisines = ArrayList<String>()
        try {
            binding.selectCousine.selectedItemsPosition.forEach {
                listCuisines.add(cuisineList[it].id.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (validate()) {

            val requestProfile = ProfileRequest(
                access_token = sharedPreferenceManager.getAccessToken.toString()
                    .toRequestBody("text/plain".toMediaType()),
                first_name = firstName.toRequestBody("text/plain".toMediaType()),
                last_name = lastName.toRequestBody("text/plain".toMediaType()),
                address = address.toRequestBody("text/plain".toMediaType()),
                latitude = latitude.toRequestBody("text/plain".toMediaType()),
                longitude = longitude.toRequestBody("text/plain".toMediaType()),
                about_me = binding.yourStory.text.toString()
                    .toRequestBody("text/plain".toMediaType()),
                brand_name = binding.brandName.text.toString()
                    .toRequestBody("text/plain".toMediaType()),
                image = if (imagePath.isEmpty()) null else MultipartBody.Part.createFormData(
                    "image",
                    imageFile.name,
                    imageBody
                ),
                cover_image = if (coverPath.isEmpty()) null else MultipartBody.Part.createFormData(
                    "cover_image",
                    coverFile.name,
                    coverBody
                ),
                preparation_unit = selectedUnit.toRequestBody("text/plain".toMediaType()),
                preparation_time = binding.prepTime.text.toString()
                    .toRequestBody("text/plain".toMediaType()),
                cuisines = listCuisines,
                order_limit_per_hour = "".toRequestBody("text/plain".toMediaType()),
                weekly_mode = isWeekly.toRequestBody("text/plain".toMediaType()),
                allow_ordertype = orderType.toString().toRequestBody("text/plain".toMediaType()),
                start_time = binding?.startTime?.text.toString()
                    .toRequestBody("text/plain".toMediaType()),
                end_time = binding?.endTime?.text.toString()
                    .toRequestBody("text/plain".toMediaType())
            )

            appViewModel.updateProfileAPI(requestProfile)
            appViewModel.updateProfileAPILive.observe(viewLifecycleOwner) {
                if (it.status == "1") {
                    Toast.makeText(requireActivity(), "Profile Saved", Toast.LENGTH_SHORT).show()
                    mainActivity.vm.onBackButtonClicked()
                } else {
                    Log.i("tag", it.message)
                }
            }
        }

    }

    private fun validate(): Boolean {
        val valid = Validation
        if (valid.checkIsEmpty(binding.fullName)) {
            return false
        }
        if (valid.checkIsEmpty(binding.brandName)) {
            return false
        }
        if (valid.checkIsEmpty(binding.yourStory)) {
            return false
        }
        if (valid.checkIsEmpty(binding.startTime)) {
            return false
        }
        if (valid.checkIsEmpty(binding.endTime)) {
            return false
        }
        if (valid.checkIsEmpty(binding.selectCousine)) {
            return false
        }
        if (valid.checkIsEmpty(binding.selectType)) {
            return false
        }
        if (valid.checkIsEmpty(binding.prepTime)) {
            return false
        }
        if (valid.checkIsEmpty(binding.selectUnit)) {
            return false
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == SELECT_IMAGE_REQUEST || requestCode == SELECT_COVER_IMAGE_REQUEST) {
//            if (resultCode == AppCompatActivity.RESULT_OK) {
//                isCover = requestCode == SELECT_COVER_IMAGE_REQUEST
//                pickiT!!.getPath(data?.data, Build.VERSION.SDK_INT)
//            }
//        }
    }

    override fun PickiTonUriReturned() {

    }

    override fun PickiTonStartListener() {
    }

    override fun PickiTonProgressUpdate(progress: Int) {
    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        if (isCover) {
            coverPath = path!!
        } else {
            imagePath = path!!
        }
    }

    override fun PickiTonMultipleCompleteListener(
        paths: ArrayList<String>?,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
    }


}