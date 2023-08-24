package com.tom.chef.ui.infoDialog

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.ui.dialogs.infoDialog.InfoDialogInterface

class InfoDialogViewModel() :ViewModel() {

    lateinit var dialogDismissInterface: DialogDismissInterface
    var infoDialogInterface: InfoDialogInterface?=null


    fun onYesButtonClicked(){
        infoDialogInterface?.onYesClicked()
        dialogDismissInterface.onDismissClicked()
    }
    fun onNoButtonClicked(){
        infoDialogInterface?.onNoClicked()
        dialogDismissInterface.onDismissClicked()
    }

    var isCancelAble=true

    @JvmField
    var yesText=ObservableField<String>()
    @JvmField
    var noText=ObservableField<String>()

    @JvmField
    var showYes=ObservableField<Boolean>(false)
    @JvmField
    var showNo=ObservableField<Boolean>(false)

    @JvmField
    var showBlackHeading=ObservableField<Boolean>(true)

    @JvmField
    var showBoarderButton=ObservableField<Boolean>(false)

    @JvmField
    var showTitle=ObservableField<Boolean>(false)
    @JvmField
    var showLogo=ObservableField<Boolean>(false)
    @JvmField
    var showHeading=ObservableField<Boolean>(false)
    @JvmField
    var showSubHading=ObservableField<Boolean>(false)

    @JvmField
    var title=ObservableField<String>()
    @JvmField
    var heading=ObservableField<String>()
    @JvmField
    var subHeading=ObservableField<String>()
    @JvmField
    var logo=ObservableField<Int?>()

    fun permissionRequired(message: String){
        title.set("Permission required")
        heading.set(message)
        noText.set("Cancel")
        yesText.set("Settings")
        showHeading.set(true)
        showYes.set(true)
        showTitle.set(true)
        showNo.set(true)
    }
}