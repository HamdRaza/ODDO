package com.tom.chef.ui.comman.menuItems.packageMenu

import android.app.Activity
import androidx.databinding.ObservableField
import com.tom.chef.ui.allBottomSheets.arabicDetails.ArabicBottomSheet
import com.tom.chef.ui.comman.ViewModel

class PackageViewModel(val activity: Activity):ViewModel {
    lateinit var packageInterface: PackageInterface

    fun onArabicClicked(){
        ArabicBottomSheet().addArabicDetails(context = activity, packageViewModel = this)
    }
    fun onAddNewClicked(){
        isAdded.set(true)
        if (!inEditMode){
            packageInterface.onAddNewClicked(activity = activity)
        }
    }
    fun onEditClicked(){
        buttonText.set("Update")
        isAdded.set(false)
    }
    fun onDeleteClicked(){
        deleteThis=true
        packageInterface.onDeleteClicked()
    }

    @JvmField
    var title=ObservableField<String>("")
    @JvmField
    var buttonText=ObservableField<String>("Add New Package")
    @JvmField
    var price=ObservableField<String>("")
    @JvmField
    var sufficient=ObservableField<String>("")
    @JvmField
    var Quantity=ObservableField<String>("")

    @JvmField
    var arabicTitle=ObservableField<String>("")
    @JvmField
    var arabicPrice=ObservableField<String>("")
    @JvmField
    var arabicSufficient=ObservableField<String>("")
    @JvmField
    var arabicQuantity=ObservableField<String>("")

    @JvmField
    var isAdded=ObservableField<Boolean>(false)

    var deleteThis=false
    var inEditMode=false

    override fun isThis(): Boolean {
        return deleteThis
    }
}