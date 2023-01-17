package com.tom.chef.ui.dashboard.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.chef.R
import com.tom.chef.databinding.FragmentHomeProfileBinding
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.auth.logIn.LoginActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.location.LocationViewModel
import com.tom.chef.ui.comman.profile.ProfileInterface
import com.tom.chef.ui.comman.profile.ProfileItemAdapter
import com.tom.chef.ui.comman.profile.ProfileItemViewModel
import com.tom.chef.utils.SharedPreferenceManager
import com.tom.chef.utils.getProfileMenuList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : BaseFragment(), ProfileInterface, AccountInterface {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private lateinit var binding: FragmentHomeProfileBinding
    private lateinit var accountVM: AccountViewModel

    private val profileItemAdopter: ProfileItemAdapter = ProfileItemAdapter(ArrayList())

    val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeProfileBinding.bind(
            inflater.inflate(
                R.layout.fragment_home_profile,
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
        loadMenuRecycle()
        setupLogout()
        setupListeners()
    }

    private fun setupListeners() {
        mainActivity.vm.userProfile.observe(viewLifecycleOwner) {
            it?.let {
                accountVM.updateProfile(it)
            }
        }
    }

    private fun setupLogout() {
        binding.logOut.setOnClickListener {
            appViewModel.logout()
            appViewModel.logoutLive.observe(viewLifecycleOwner) {
                if (it.status == "1") {
                    sharedPreferenceManager.isLogedIn = false
                    sharedPreferenceManager.clear()
                    startActivity(LoginActivity.getIntent(requireContext()))
                    requireActivity().finishAffinity()

                    Toast.makeText(requireActivity(), "Logged out", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }

    private fun loadMenuRecycle() {
        with(binding) {
            recycleView.layoutManager = LinearLayoutManager(requireContext())
            recycleView.isNestedScrollingEnabled = false
            recycleView.adapter = profileItemAdopter
        }
        val viewModels = ArrayList<ViewModel>()
        requireActivity().getProfileMenuList().forEach {
            val model = ProfileItemViewModel(mActivity, it)
            model.profileInterface = this
            viewModels.add(model)
        }
        profileItemAdopter.setList(viewModels)

    }

    override fun onBackClicked() {
        mainActivity.vm.onBackButtonClicked()
    }


}