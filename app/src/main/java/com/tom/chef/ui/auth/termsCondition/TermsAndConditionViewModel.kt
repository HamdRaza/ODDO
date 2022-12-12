package com.tom.chef.ui.auth.termsCondition

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.models.profile.ResponseGetPage
import com.tom.chef.newBase.BaseActivity

class TermsAndConditionViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var termsAndConditionInterface: TermsAndConditionInterface

    fun init(){
        termsAndConditionInterface.initWindow()
    }

    @JvmField
    var enTitle= ObservableField<String>()
    @JvmField
    var enDescription= ObservableField<String>()

    @JvmField
    var arTitle= ObservableField<String>()
    @JvmField
    var arDescription= ObservableField<String>()

    fun updateText(responseGetPage: ResponseGetPage){
        responseGetPage.oData.let {
            enTitle.set(it.titleEn)
            enDescription.set(it.descEn)
            arTitle.set(it.titleAr)
            arDescription.set(it.descAr)
        }
    }




}