package com.tom.chef.ui.dashboard.fragments.menu.subFragments

import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.menuItems.MenuItemAdopter
import com.tom.chef.ui.comman.menuItems.MenuItemInterface
import com.tom.chef.ui.comman.menuItems.MenuItemViewModel

class AllMenuViewModel: ViewModel {

    var menuItemAdopter=MenuItemAdopter(ArrayList())

    fun fillMenuItems(menuItemInterface: MenuItemInterface, isVariant:Boolean){
        val viewModels=ArrayList<ViewModel>()
        for (i in 0 until 10){
            val viewModel=MenuItemViewModel()
            viewModel.menuItemInterface=menuItemInterface
            viewModel.updateVariant(isVariant)
            viewModels.add(viewModel)
        }
        menuItemAdopter.setList(viewModels)
    }
}