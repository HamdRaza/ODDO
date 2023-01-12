package com.tom.chef.ui.dashboard.fragments.menu.subFragments

import com.tom.chef.models.DishListResponse
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.menuItems.MenuItemAdopter
import com.tom.chef.ui.comman.menuItems.MenuItemInterface
import com.tom.chef.ui.comman.menuItems.MenuItemViewModel

class AllMenuViewModel : ViewModel {

    var menuItemAdopter = MenuItemAdopter(ArrayList())

    fun fillMenuItems(
        menuItemInterface: MenuItemInterface,
        isVariant: Boolean,
        data: DishListResponse
    ) {
        val viewModels = ArrayList<ViewModel>()
        data.oData.forEach {
            val viewModel = MenuItemViewModel()
            viewModel.menuItemInterface = menuItemInterface
            viewModel.updateVariant(isVariant)
            viewModel.update(it)
            viewModels.add(viewModel)
        }
        menuItemAdopter.setList(viewModels)
    }
}