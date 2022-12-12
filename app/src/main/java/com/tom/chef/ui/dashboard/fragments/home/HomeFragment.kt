package com.tom.chef.ui.dashboard.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tom.chef.R
import com.tom.chef.databinding.FragmentDashboardHomeBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.tabsAdopter.ViewStateAdapter
import com.tom.chef.ui.dashboard.fragments.home.subFragments.currentOrders.CurrentFragment
import com.tom.chef.ui.dashboard.fragments.home.subFragments.scheduledOrders.ScheduledFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment :  BaseFragment(),HomePageInterface,HomeMenuInterface{
    private lateinit var binding: FragmentDashboardHomeBinding
    lateinit var homePageViewModel: HomePageViewModel
    lateinit var homeMenuViewModel: HomeMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardHomeBinding.bind(inflater.inflate(R.layout.fragment_dashboard_home, container, false))

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePageViewModel= HomePageViewModel()
        binding.viewModel=homePageViewModel
        homePageViewModel.homePageInterface=this

        homeMenuViewModel= HomeMenuViewModel()
        homeMenuViewModel.homeMenuInterface=this

        mActivity.toolbarVM.manageToolBar(showToolbar = true, showUserProfile = true, showNotificationIcon = true)
        mainActivity.toolbarVM.makeBackRound(isRound = false)

        setupTabs()
    }

    private fun setupTabs() {
        val fragments=ArrayList<Fragment>()
        fragments.add(CurrentFragment())
        fragments.add(ScheduledFragment())
        binding.pageView.adapter= ViewStateAdapter(fragments,childFragmentManager,lifecycle)
        binding.tabSelector.setOnCheckedChangeListener { radioGroup, i ->
            if (binding.tabSelector.checkedRadioButtonId==binding.currentOrder.id){
                binding.pageView.currentItem = 0
            }else{
                binding.pageView.currentItem = 1
            }
        }
        binding.pageView.isUserInputEnabled=false
    }

}


