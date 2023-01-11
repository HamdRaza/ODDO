package com.tom.chef.ui.dashboard.fragments.home.subFragments.currentOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.tom.chef.R
import com.tom.chef.databinding.FragmentDashboardHomeBinding
import com.tom.chef.databinding.FragmentDashboardHomeCurrentorderBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.tabsAdopter.ViewStateAdapter
import com.tom.chef.ui.dashboard.fragments.home.subFragments.allOrders.AllOrdersFragment
import com.tom.chef.utils.handleClick
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrentFragment : BaseFragment(), CurrentOrderInterface {
    private lateinit var binding: FragmentDashboardHomeCurrentorderBinding

    lateinit var currentOrderViewModel: CurrentOrderViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardHomeCurrentorderBinding.bind(
            inflater.inflate(
                R.layout.fragment_dashboard_home_currentorder,
                container,
                false
            )
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentOrderViewModel = CurrentOrderViewModel()
        currentOrderViewModel.currentOrderInterface = this
        binding.viewModel = currentOrderViewModel
        loadAllTabs()
    }

    private fun loadAllTabs() {
        val fragments = ArrayList<Fragment>()
        listOf("PENDING", "ACCEPTED", "REJECTED", "COMPLETED").forEach {
            binding.tab.addTab(binding.tab.newTab().setText(it))
            val newFragment = AllOrdersFragment()
            newFragment.arguments = bundleOf(Pair("tabName", it), Pair("type", "current"))
            fragments.add(newFragment)
        }
        binding.pageView.adapter = ViewStateAdapter(fragments, childFragmentManager, lifecycle)
        binding.tab.handleClick(binding.pageView)
    }

}


