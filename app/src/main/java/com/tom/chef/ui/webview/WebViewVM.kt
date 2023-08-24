package com.tom.chef.ui.webview

import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.profile.ProfileViewModel
import com.tom.chef.ui.comman.toolBar.ToolBarViewModel


class WebViewVM : ViewModel {
    var viewInteractor: WebViewContracts?=null
    fun setData(){
        viewInteractor?.initiate()
    }
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()

    fun checkForBackExit()=viewInteractor?.checkForBackExit()


    var  toolBarViewModel=ToolBarViewModel()
    var  profileViewModel=ProfileViewModel()

}