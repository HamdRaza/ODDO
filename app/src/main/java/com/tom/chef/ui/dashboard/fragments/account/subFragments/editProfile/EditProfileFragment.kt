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
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.permissionx.guolindev.PermissionX
import com.tom.chef.R
import com.tom.chef.databinding.FragmentHomeEditProfileBinding
import com.tom.chef.models.ProfileRequest
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.profile.ProfileInterface
import com.tom.chef.ui.dashboard.fragments.account.AccountInterface
import com.tom.chef.ui.dashboard.fragments.account.AccountViewModel
import com.tom.chef.ui.location.LocationPickerActivity
import com.tom.chef.utils.ReduceImageSize
import com.tom.chef.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class EditProfileFragment : BaseFragment(), ProfileInterface, AccountInterface,
    EditProfileInterface, PickiTCallbacks {

//    private val SELECT_IMAGE_REQUEST = 102
//    private val SELECT_COVER_IMAGE_REQUEST = 103

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private lateinit var binding: FragmentHomeEditProfileBinding
    private lateinit var accountVM: AccountViewModel
    private lateinit var editProfileViewModel: EditProfileViewModel

    val appViewModel: AppViewModel by viewModels()

    var allGranted = false

    var pickiT: PickiT? = null

    var isCover = false

    var imagePath = ""
    var coverPath = ""
    var selectedUnit = "mins"
    var cuisinePosition = -1

    var orderType = -1

    var address = ""
    var latitude = ""
    var longitude = ""

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
            val c = Calendar.getInstance()

            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                requireActivity(),
                { view, hourOfDay, minute ->
                    binding?.startTime?.setText("$hourOfDay:$minute")
                },
                hour,
                minute,
                false
            )
            timePickerDialog.show()
        }
        binding?.endTime?.setOnClickListener {
            val c = Calendar.getInstance()

            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                requireActivity(),
                { view, hourOfDay, minute ->
                    binding?.endTime?.setText("$hourOfDay:$minute")
                },
                hour,
                minute,
                false
            )
            timePickerDialog.show()
        }
        val type = arrayOf("mins", "hrs", "days")

        val adapter2 = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            type
        )
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
        binding.selectType.setAdapter(adapter3)
        binding.selectType.setOnItemClickListener { adapterView, view, position, l ->
            orderType = position
        }
        getCuisines()

    }

    private fun getCuisines() {
        appViewModel.getCuisineList()
        appViewModel.getCuisineListLive.observe(viewLifecycleOwner) {
            if (it.status == "1") {
                val cuisines = it.oData.map { it.cuisineName }

                val adapter2 = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_list_item_1,
                    cuisines
                )
                binding.selectCousine.setAdapter(adapter2)
                binding.selectCousine.setOnItemClickListener { adapterView, view, position, l ->
                    cuisinePosition = position
                }
            } else {
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

//    private fun openGalleryImage() {
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        galleryIntent.type = "image/*"
//        startActivityForResult(galleryIntent, SELECT_IMAGE_REQUEST)
//    }
//
//    private fun openGalleryCoverImage() {
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        galleryIntent.type = "image/*"
//        startActivityForResult(galleryIntent, SELECT_COVER_IMAGE_REQUEST)
//    }

    private fun setupListeners() {
        mainActivity.vm.userProfile.observe(viewLifecycleOwner) {
            it?.let {
                accountVM.updateProfile(it)
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
        getLocation.launch(LocationPickerActivity.getIntent(requireContext()))
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
        listCuisines.clear()
        listCuisines.add(cuisinePosition.toString())

        val requestSignUp = ProfileRequest(
            access_token = sharedPreferenceManager.getAccessToken.toString()
                .toRequestBody("text/plain".toMediaType()),
            first_name = firstName.toRequestBody("text/plain".toMediaType()),
            last_name = lastName.toRequestBody("text/plain".toMediaType()),
            address = address.toRequestBody("text/plain".toMediaType()),
            latitude = latitude.toRequestBody("text/plain".toMediaType()),
            longitude = longitude.toRequestBody("text/plain".toMediaType()),
            about_me = binding.yourStory.text.toString().toRequestBody("text/plain".toMediaType()),
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
            end_time = binding?.endTime?.text.toString().toRequestBody("text/plain".toMediaType())
        )

        appViewModel.updateProfileAPI(requestSignUp)
        appViewModel.updateProfileAPILive.observe(viewLifecycleOwner) {
            if (it.status == "1") {
                Toast.makeText(requireActivity(), "Profile Saved", Toast.LENGTH_SHORT).show()
                mainActivity.vm.onBackButtonClicked()
            } else {
                Log.i("tag", it.message)
            }
        }
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