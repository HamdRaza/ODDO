package com.tom.chef.ui.comman.toolBar

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.utils.checkForSuccess

class ToolBarViewModel() : ViewModel() {

     var toolBarInterface: ToolBarInterface?=null

    @JvmField
    var backCorner = ObservableField<Boolean>(false)

    @JvmField
    var userOnline = ObservableField<Boolean>(true)

    @JvmField
    var showUserProfile = ObservableField<Boolean>(false)



    @JvmField
    var showBackButton = ObservableField<Boolean>(false)

    @JvmField
    var showToolBar = ObservableField<Boolean>(false)



    @JvmField
    var showAddButton = ObservableField<Boolean>(false)

    var showDeleteMenu = ObservableField<Boolean>(false)

    @JvmField
    var backButtonText = ObservableField<String>()


    @JvmField
    var showMinimizer=ObservableField<Boolean>()
    @JvmField
    var minimize=ObservableField<Boolean>()

    fun manageToolBar(
        showToolbar: Boolean = false,
        showBackButton: Boolean = false,
        backButtonText: String = "",
        showAdButton: Boolean = false,
        showUserProfile: Boolean = false,
        showDeleteMenu:Boolean=false,
        showMinimizer:Boolean=false
    ) {
        this.showToolBar.set(showToolbar)
        this.showBackButton.set(showBackButton)
        this.backButtonText.set(backButtonText)
        this.showAddButton.set(showAdButton)
        this.showUserProfile.set(showUserProfile)
        this.showDeleteMenu.set(showDeleteMenu)
        this.showMinimizer.set(showMinimizer)
    }

    fun onMinimizerClicked(){
        minimize.set(!minimize.get().checkForSuccess())
        showUserProfile.set(!minimize.get().checkForSuccess())
    }
    fun makeBackRound(isRound: Boolean) {
        backCorner.set(isRound)
    }

    fun onBackClicked() {
        toolBarInterface?.onBackClicked()
    }

    fun moveToProfile() = toolBarInterface?.moveToProfile()


    fun reloadWebPage() = toolBarInterface?.reloadWebPage()

}