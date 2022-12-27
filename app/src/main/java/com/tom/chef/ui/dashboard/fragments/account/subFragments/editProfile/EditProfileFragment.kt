package com.tom.chef.ui.dashboard.fragments.account.subFragments.editProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.chef.R
import com.tom.chef.databinding.FragmentHomeEditProfileBinding
import com.tom.chef.databinding.FragmentHomeProfileBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.auth.logIn.LoginActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.profile.ProfileInterface
import com.tom.chef.ui.comman.profile.ProfileItemAdapter
import com.tom.chef.ui.comman.profile.ProfileItemViewModel
import com.tom.chef.ui.dashboard.fragments.account.AccountInterface
import com.tom.chef.ui.dashboard.fragments.account.AccountViewModel
import com.tom.chef.ui.location.LocationPickerActivity
import com.tom.chef.utils.ReduceImageSize
import com.tom.chef.utils.SharedPreferenceManager
import com.tom.chef.utils.getProfileMenuList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class EditProfileFragment : BaseFragment(), ProfileInterface, AccountInterface,EditProfileInterface {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private lateinit var binding: FragmentHomeEditProfileBinding
    private lateinit var accountVM: AccountViewModel
    private lateinit var editProfileViewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentHomeEditProfileBinding.bind(inflater.inflate(R.layout.fragment_home_edit_profile, container, false))

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar Setup
        mActivity.toolbarVM.manageToolBar(showToolbar = false)
        accountVM = AccountViewModel(mActivity)
        accountVM.accountInterface=this
        binding.viewModel = accountVM

        editProfileViewModel= EditProfileViewModel()
        editProfileViewModel.editProfileInterface=this
        binding.editProfile=editProfileViewModel


        setUpListners()
    }

    private fun setUpListners() {
        mainActivity.vm.userProfile.observe(viewLifecycleOwner){
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


    private val profileImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
           val imageUri = ReduceImageSize.compressImage(it,requireContext())
           accountVM.profilePath.set(imageUri)
        }
    }
    private val backgroundImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val imageUri = ReduceImageSize.compressImage(it,requireContext())
            accountVM.coverImage.set(imageUri)
        }
    }

    val getLocation=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it?.let {
            it.data?.extras?.let {

            }
        }
    }

    override fun onSaveClicked() {
        mainActivity.vm.onBackButtonClicked()
    }



}