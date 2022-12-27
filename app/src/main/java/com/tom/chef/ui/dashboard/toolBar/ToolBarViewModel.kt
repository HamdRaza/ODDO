package com.tom.chef.ui.dashboard.toolBar

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.newBase.BaseActivity

class ToolBarViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var toolBarInterface: ToolBarInterface
    var variantToggle:VariantToggle?=null

    @JvmField
    var backCorner=ObservableField<Boolean>(false)
    @JvmField
    var userOnline=ObservableField<Boolean>(true)
    @JvmField
    var showUserProfile=ObservableField<Boolean>(false)

    @JvmField
    var showNotificationIcon=ObservableField<Boolean>(false)
    @JvmField
    var showBackButton=ObservableField<Boolean>(false)
    @JvmField
    var showToolBar=ObservableField<Boolean>(false)
    @JvmField
    var showCenterText=ObservableField<Boolean>(false)
    @JvmField
    var showAddButton=ObservableField<Boolean>(false)
    @JvmField
    var showDishVariety=ObservableField<Boolean>(false)
    @JvmField
    var showClearButton=ObservableField<Boolean>(false)
    @JvmField
    var backButtonText=ObservableField<String>()
    @JvmField
    var centerButtonText=ObservableField<String>()


    fun manageToolBar(showToolbar:Boolean=false,showBackButton:Boolean=false,backButtonText:String="",showCenterText:Boolean=false,centerText:String="",showAdButton:Boolean=false,showDishVariety:Boolean=false,showUserProfile:Boolean=false,showNotificationIcon:Boolean=false,showClearButton:Boolean=false){
      this.showToolBar.set(showToolbar)
      this.showBackButton.set(showBackButton)
      this.backButtonText.set(backButtonText)
      this.showCenterText.set(showCenterText)
      this.centerButtonText.set(centerText)
      this.showAddButton.set(showAdButton)
      this.showDishVariety.set(showDishVariety)
      this.showUserProfile.set(showUserProfile)
      this.showNotificationIcon.set(showNotificationIcon)
      this.showClearButton.set(showClearButton)
    }
    fun makeBackRound(isRound:Boolean){
        backCorner.set(isRound)
    }

    fun onBackClicked(){
        toolBarInterface.onBackClicked()
    }

    fun showUpdateStatus()=toolBarInterface.showUpdateStatus()
    fun moveToProfile()=toolBarInterface.moveToProfile()
    fun moveToNotifications()=toolBarInterface.moveToNotifications()

    fun deleteAllNotification()=toolBarInterface.deleteAllNotification()

    fun addNewMenuItem(){
        toolBarInterface.addNewMenuItem()
    }
    fun onSwitched(view: View,boolean: Boolean){
        variantToggle?.updated(boolean = boolean)
    }
}