package com.tom.chef.utils.buttonsVM

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class BaseGradientButton():ViewModel() {

    @JvmField
    val centerButtonText = ObservableField<String>()
    @JvmField
    val leftButtonText = ObservableField<String>()

    @JvmField
    val showCenter = ObservableField<Boolean>()
    @JvmField
    val showPopin = ObservableField<Boolean>()

    @JvmField
    val isSignUpButton = ObservableField<Boolean>()




    lateinit var buttonClick:BottomClickInterface


    fun onButtonClicked(view:View)=buttonClick.onButtonClicked()

    fun updateButton(centerText:String="",leftText:String="",showCenter:Boolean=true,showPopin:Boolean=true,isSignUpButton:Boolean=false){
        this.centerButtonText.set(centerText)
        this.leftButtonText.set(leftText)
        this.showCenter.set(showCenter)
        this.showPopin.set(showPopin)
        this.isSignUpButton.set(isSignUpButton)
    }
}