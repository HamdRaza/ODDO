package com.tom.chef.ui.profile

import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.profile.ProfileViewModel
import com.tom.chef.ui.comman.toolBar.ToolBarViewModel


class ProfileVM : ViewModel {
    var viewInteractor: ProfileContracts?=null
    fun setData(){
        viewInteractor?.initiate()
    }
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()

    fun checkForBackExit()=viewInteractor?.checkForBackExit()


    var  toolBarViewModel=ToolBarViewModel()
    var  profileViewModel=ProfileViewModel()

    fun logOutClicked()=viewInteractor?.logOutClicked()
}