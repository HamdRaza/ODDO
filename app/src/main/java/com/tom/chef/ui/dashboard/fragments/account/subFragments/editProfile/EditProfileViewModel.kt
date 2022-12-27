package com.tom.chef.ui.dashboard.fragments.account.subFragments.editProfile

import androidx.lifecycle.ViewModel

class EditProfileViewModel : ViewModel(){

    lateinit var editProfileInterface: EditProfileInterface

    fun changeProfile(){
        editProfileInterface.changeProfile()
    }
    fun changeBackground(){
        editProfileInterface.changeBackground()
    }
    fun editLocation(){
        editProfileInterface.editLocation()
    }
    fun onSaveClicked(){
        editProfileInterface.onSaveClicked()
    }
}