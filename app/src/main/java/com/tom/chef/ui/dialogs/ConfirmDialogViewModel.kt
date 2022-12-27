package com.tom.chef.ui.dialogs

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class ConfirmDialogViewModel :ViewModel() {
    lateinit var confirmDialogInterface: ConfirmDialogInterface
    lateinit var confirmDialogCurrentInterface: ConfirmDialogCurrentInterface

    @JvmField
    var heading=ObservableField<String>("")
    @JvmField
    var subHeading=ObservableField<String>("")

    @JvmField
    var yesButton=ObservableField<String>("Yes")
    @JvmField
    var noButton=ObservableField<String>("No")

    fun onYesButtonClicked(){
        confirmDialogInterface.onYesClicked()
        confirmDialogCurrentInterface.onDismissClicked()
    }
    fun onNoButtonClicked(){
        confirmDialogInterface.onNoClicked()
        confirmDialogCurrentInterface.onDismissClicked()
    }
    fun onDismissClicked(){
        confirmDialogCurrentInterface.onDismissClicked()
    }

    fun showMaxServiceLimit(){
        heading.set("Service cart limit")
        subHeading.set("Adding this service will clear your cart. Proceed with the current selection? ")
        yesButton.set("Yes")
        noButton.set("No")
    }

    fun showProductVendorLimit(message:String){
        heading.set("Different pharmacy")
        subHeading.set(message)
        yesButton.set("Yes")
        noButton.set("No")
    }

    fun showDeleteItemCart(){
        heading.set("Delete item")
        subHeading.set("Are you sure you want to remove this item from cart? ")
        yesButton.set("Yes")
        noButton.set("No")
    }

    fun showDeleteNotification(){
        heading.set("Delete notification")
        subHeading.set("Are you sure you want to remove this notification? ")
        yesButton.set("Yes")
        noButton.set("No")
    }
    fun showDeleteNotificationAll(){
        heading.set("Delete notification")
        subHeading.set("Are you sure you want to clear all notification? ")
        yesButton.set("Yes")
        noButton.set("No")
    }
    fun showDeleteAddress(){
        heading.set("Delete address")
        subHeading.set("Are you sure you want to remove this address? ")
        yesButton.set("Yes")
        noButton.set("No")
    }
    fun showOrderCancel(){
        heading.set("Order Cancel")
        subHeading.set("Are your sure you want to cancel this order? ")
        yesButton.set("Yes")
        noButton.set("No")
    }

    fun showRefund(){
        heading.set("Select refund pay type")
        subHeading.set("Please choose refund pay type")
        yesButton.set("Wallet")
        noButton.set("Bank")
    }

    fun showRemoveFromFav(){
        heading.set("Remove pharmacy")
        subHeading.set("Are you sure you want to remove this pharmacy from favourites ?")
        yesButton.set("Yes")
        noButton.set("No")
    }


    fun locationPermissionRequired(){
        heading.set("Location permission required")
        subHeading.set("Location permission is required. Please provide location permission from settings")
        noButton.set("Exit")
        yesButton.set("Settings")
    }
}