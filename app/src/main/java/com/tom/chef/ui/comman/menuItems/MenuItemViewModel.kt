package com.tom.chef.ui.comman.menuItems

import android.os.Build
import android.text.Html
import android.view.View
import androidx.databinding.ObservableField
import com.tom.chef.R
import com.tom.chef.models.DishListResponse
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.menuItems.veriants.VariantItemAdopter
import com.tom.chef.ui.comman.menuItems.veriants.VariantsViewModel

class MenuItemViewModel : ViewModel {
    lateinit var menuItemInterface: MenuItemInterface

    @JvmField
    var itemImage = ObservableField<Any?>(R.drawable.menu_dummy_image)

    @JvmField
    var itemName = ObservableField<String>()

    @JvmField
    var itemDescription = ObservableField<String>()

    @JvmField
    var servingForPeople = ObservableField<String>()

    @JvmField
    var isInStock = ObservableField<Boolean>()

    @JvmField
    var itemPrice = ObservableField<String>()

    @JvmField
    var itemOldPrice = ObservableField<String>()

    @JvmField
    var makeCenterLine = ObservableField<Boolean>()

    @JvmField
    var isThisVariant = ObservableField<Boolean>(false)

    var variantItemAdopter = VariantItemAdopter(ArrayList())

    var data: DishListResponse.OData? = null

    init {

    }

    fun onClicked() {
        menuItemInterface.onMenuItemClicked()
    }

    fun onSwitched(view: View, bool: Boolean) {
        menuItemInterface.onToggleStatus(data?.id.toString())
    }

    private fun showVariants() {
        val viewModels = ArrayList<ViewModel>()
        listOf("Breakfast", "Lunch").forEach {
            val viewModel = VariantsViewModel(title = it)
            viewModels.add(viewModel)
        }
        variantItemAdopter.setList(viewModels)
    }

    fun updateVariant(boolean: Boolean) {
        isThisVariant.set(boolean)
        showVariants()
    }

    fun update(data: DishListResponse.OData) {
        this.data = data
        itemName.set(data.name)
        val description = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(data.description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(data.description)
        }
        itemDescription.set(description.toString())
        servingForPeople.set("${data.sufficientFor} People")
        isInStock.set(data.outOfStock == "0")
        itemOldPrice.set("AED ${data.regularPrice}")
        itemPrice.set("AED ${data.salePrice}")
        itemImage.set(data.image)
    }

}