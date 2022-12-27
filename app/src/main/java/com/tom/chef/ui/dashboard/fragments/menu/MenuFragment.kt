package com.tom.chef.ui.dashboard.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.tom.chef.R
import com.tom.chef.databinding.FragmentDashboardHomeBinding
import com.tom.chef.databinding.FragmentMenuBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.tabsAdopter.ViewStateAdapter
import com.tom.chef.ui.dashboard.fragments.home.subFragments.currentOrders.CurrentFragment
import com.tom.chef.ui.dashboard.fragments.home.subFragments.scheduledOrders.ScheduledFragment
import com.tom.chef.ui.dashboard.fragments.menu.subFragments.AllMenuFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuFragment :  BaseFragment(),MenuInterface{
    private lateinit var binding: FragmentMenuBinding
    lateinit var menuViewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMenuBinding.bind(inflater.inflate(R.layout.fragment_menu, container, false))

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuViewModel= MenuViewModel()
        binding.viewModel=menuViewModel
        menuViewModel.menuInterface=this

        mActivity.toolbarVM.manageToolBar(showToolbar = true, showBackButton = true, backButtonText = "Menu Setup", showAdButton = true)
        mainActivity.toolbarVM.makeBackRound(isRound = true)

        setupTabs()
    }

    private fun setupTabs() {
        val fragments=ArrayList<Fragment>()
        fragments.add(AllMenuFragment())
        fragments.add(AllMenuFragment().also { it.arguments= bundleOf(Pair("variant",true)) })
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


