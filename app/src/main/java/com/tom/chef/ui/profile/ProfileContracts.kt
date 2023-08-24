package com.tom.chef.ui.profile



interface ProfileContracts  {
    fun initiate()
    fun ShowLoading()
    fun HideLoading()
    fun checkForBackExit()
    fun logOutClicked()
}