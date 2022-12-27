package com.tom.chef.ui.comman.menuItems

import androidx.databinding.ObservableField
import com.tom.chef.R
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.menuItems.veriants.VeriantItemAdopter
import com.tom.chef.ui.comman.menuItems.veriants.VeriantsViewModel

class MenuItemViewModel:ViewModel {
    lateinit var menuItemInterface: MenuItemInterface

    @JvmField
    var itemImage=ObservableField<Any?>(R.drawable.menu_dummy_image)
    @JvmField
    var itemName=ObservableField<String>()
    @JvmField
    var itemDescription=ObservableField<String>()

    @JvmField
    var servingForPeople=ObservableField<String>()

    @JvmField
    var isInStock=ObservableField<Boolean>()

    @JvmField
    var itemPrice=ObservableField<String>()
    @JvmField
    var itemOldPrice=ObservableField<String>()

    @JvmField
    var makeCenterLine=ObservableField<Boolean>()
    @JvmField
    var isThisVariant=ObservableField<Boolean>(false)

    var variantItemAdopter=VeriantItemAdopter(ArrayList())

    init {
        itemName.set("Meat Samosa")
        itemDescription.set("Lorem ipsum dolor sit amet, consectetur adipiscing ")
        servingForPeople.set("4 People")
        isInStock.set(true)
        itemOldPrice.set("AED 20.3")
        itemPrice.set("AED 20.3")
    }
    fun onClicked(){
        menuItemInterface.onMenuItemClicked()
    }

    private fun showVariants(){
        val viewModels=ArrayList<ViewModel>()
        listOf("Breakfast","Lunch").forEach {
            val viewModel=VeriantsViewModel(title = it)
            viewModels.add(viewModel)
        }
        variantItemAdopter.setList(viewModels)
    }

    fun updateVariant(boolean: Boolean){
        isThisVariant.set(boolean)
        showVariants()
    }


}