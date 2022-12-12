package com.tom.chef.ui.auth.inPutDocuments

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.newBase.BaseActivity

class InputDocumentsViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var inputDocumentInterface: InputDocumentInterface

    fun submitDocuments()=inputDocumentInterface.submitDocuments()
    fun inputTradeLicense()=inputDocumentInterface.inputTradeLicense()
    fun inputEmiratesID()=inputDocumentInterface.inputEmiratesID()
    fun inputPassportCopy()=inputDocumentInterface.inputPassportCopy()
    fun inputVisa()=inputDocumentInterface.inputVisa()
    fun inputBankAccount()=inputDocumentInterface.inputBankAccount()


    @JvmField
    var tradeLicense=ObservableField<String>()
    @JvmField
    var emiratesID=ObservableField<String>()
    @JvmField
    var passportCopy=ObservableField<String>()
    @JvmField
    var visa=ObservableField<String>()
    @JvmField
    var bankAccount=ObservableField<String>()


}