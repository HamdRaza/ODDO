package com.tom.chef.ui.dashboard.fragments.menu.subFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tom.chef.R
import com.tom.chef.databinding.FragmentAllMenuBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menuItems.MenuItemInterface
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllMenuFragment :  BaseFragment(),MenuItemInterface{
    private lateinit var binding: FragmentAllMenuBinding

    lateinit var allMenuViewModel: AllMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
     var isThisVariant:Boolean=false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAllMenuBinding.bind(inflater.inflate(R.layout.fragment_all_menu, container, false))
        arguments?.getBoolean("variant",false)?.let {
            isThisVariant=it
        }
        allMenuViewModel= AllMenuViewModel()
        allMenuViewModel.fillMenuItems(menuItemInterface = this, isVariant = isThisVariant)
        binding.viewModel=allMenuViewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onMenuItemClicked() {

    }

}


