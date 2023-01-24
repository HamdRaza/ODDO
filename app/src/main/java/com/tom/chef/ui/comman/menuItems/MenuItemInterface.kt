package com.tom.chef.ui.comman.menuItems

import com.tom.chef.models.DishListResponse

interface MenuItemInterface {
    fun onMenuItemClicked()
    fun onToggleStatus(id: String)
}