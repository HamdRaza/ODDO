package com.tom.chef.ui.dashboard.fragments.menu.addNew

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.permissionx.guolindev.PermissionX
import com.tom.chef.R
import com.tom.chef.databinding.FragmentAddNewBinding
import com.tom.chef.databinding.FragmentAllOrdersBinding
import com.tom.chef.databinding.FragmentDashboardHomeBinding
import com.tom.chef.databinding.FragmentDashboardHomeCurrentorderBinding
import com.tom.chef.databinding.FragmentFaqBinding
import com.tom.chef.databinding.FragmentFinanceBinding
import com.tom.chef.models.AddDishRequest
import com.tom.chef.models.CuisineResponse
import com.tom.chef.models.MenuResponse
import com.tom.chef.models.ProfileRequest
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.menuItems.files.FileViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails.OrderDetailsFragment
import com.tom.chef.ui.dashboard.fragments.menu.subFragments.AllMenuInterface
import com.tom.chef.ui.dashboard.fragments.notification.NotificationFragment
import com.tom.chef.ui.dashboard.toolBar.VariantToggle
import com.tom.chef.utils.FileManager
import com.tom.chef.utils.ReduceImageSize
import com.tom.chef.utils.SharedPreferenceManager
import com.tom.chef.utils.handleHull
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class AddNewFragment : BaseFragment(), VariantToggle, AllMenuInterface, PickiTCallbacks {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private lateinit var binding: FragmentAddNewBinding

    lateinit var addNewViewModel: AddNewViewModel

    val appViewModel: AppViewModel by viewModels()

    var cuisineList = ArrayList<CuisineResponse.OData>()
    var menuList = ArrayList<MenuResponse.OData>()

    var pickiT: PickiT? = null
    var allGranted = false

    var listImages = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewBinding.bind(
            inflater.inflate(
                R.layout.fragment_add_new,
                container,
                false
            )
        )
        mActivity.toolbarVM.manageToolBar(
            showToolbar = true,
            showBackButton = true,
            backButtonText = "Add  Item",
            showDishVariety = true
        )
        mainActivity.toolbarVM.makeBackRound(isRound = true)
        mainActivity.toolbarVM.variantToggle = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewViewModel = AddNewViewModel()
        addNewViewModel.allMenuInterface = this
        binding.viewModel = addNewViewModel
        pickiT = PickiT(requireActivity(), this, requireActivity())

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
        getCuisinesAndMenu()
    }

    override fun updated(boolean: Boolean) {
        addNewViewModel.haveVariant.set(boolean)
        addNewViewModel.showPackage(mainActivity)
    }

    override fun onSubmitClicked() {
        //contain_package, package
        var imageFile: File? = null
        var imageBody: RequestBody? = null
        if (listImages.size > 0) {
            imageFile = File(listImages[0])
            imageBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), imageFile)
        }
        var listMultipartGallery = ArrayList<MultipartBody.Part>()
        if (listImages.size > 1) {
            listImages.forEachIndexed { index, element ->
                if (index != 0) {
                    var file = File(element)
                    var body: RequestBody =
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                    listMultipartGallery.add(
                        MultipartBody.Part.createFormData(
                            "gallery[]",
                            file.name,
                            body
                        )
                    )
                }
            }
        }

        var listMenus = ArrayList<String>()
        try {
            binding.selectMenu.selectedItemsPosition.forEach {
                listMenus.add(menuList[it].id.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var listCuisines = ArrayList<String>()
        try {
            binding.selectCousine.selectedItemsPosition.forEach {
                listCuisines.add(cuisineList[it].id.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val requestAddDish = AddDishRequest(
            access_token = sharedPreferenceManager.getAccessToken.toString()
                .toRequestBody("text/plain".toMediaType()),
            name = binding.dishName.text.toString().toRequestBody("text/plain".toMediaType()),
            name_ar = binding.dishNameAr.text.toString().toRequestBody("text/plain".toMediaType()),
            description = binding.dishDescription.text.toString()
                .toRequestBody("text/plain".toMediaType()),
            description_ar = binding.dishDescriptionAr.text.toString()
                .toRequestBody("text/plain".toMediaType()),
            regular_price = binding.dishPrice.text.toString()
                .toRequestBody("text/plain".toMediaType()),
            sale_price = binding.dishDiscountPrice.text.toString()
                .toRequestBody("text/plain".toMediaType()),
            sufficient_for = binding.dishSufficientFor.text.toString()
                .toRequestBody("text/plain".toMediaType()),
            quantity = binding.dishQuantity.text.toString()
                .toRequestBody("text/plain".toMediaType()),
            contain_package = "0".toRequestBody("text/plain".toMediaType()),
            menu_id = listMenus,
            cuisine_id = listCuisines,
            image = if (listImages.size == 0) null else MultipartBody.Part.createFormData(
                "image",
                imageFile?.name,
                imageBody!!
            ),
            gallery = if (listImages.size <= 1) null else listMultipartGallery,
            packageList = null
        )
        appViewModel.addDish(requestAddDish)
        appViewModel.addDishLive.observe(viewLifecycleOwner) {
            if (it.status == "1") {
                Toast.makeText(requireActivity(), "Item Added", Toast.LENGTH_SHORT).show()
                mainActivity.vm.onBackButtonClicked()
            } else {
                Log.i("tag", it.message)
            }
        }
    }

    override fun pickFileClicked() {
        pickFile.launch("image/*")
    }

    override fun fileDeleted(position: Int) {
        listImages.removeAt(position)
    }

    private val pickFile = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            addNewViewModel.addFile(it, activity = requireActivity())
            pickiT!!.getPath(it, Build.VERSION.SDK_INT)
        }
    }

    private fun getCuisinesAndMenu() {
        appViewModel.getCuisineList()
        appViewModel.getMenuList()
        appViewModel.getCuisineListLive.observe(viewLifecycleOwner) {
            if (it.status == "1") {
                cuisineList = it.oData
                val cuisines = cuisineList.map { it.cuisineName }
                binding.selectCousine.items = cuisines
            } else {
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        appViewModel.getMenuLive.observe(viewLifecycleOwner) {
            if (it.status == "1") {
                menuList = it.oData
                val menu = menuList.map { it.name }

                binding.selectMenu.items = menu
            } else {
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
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
        listImages.add(path!!)
    }

    override fun PickiTonMultipleCompleteListener(
        paths: java.util.ArrayList<String>?,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
    }
}


