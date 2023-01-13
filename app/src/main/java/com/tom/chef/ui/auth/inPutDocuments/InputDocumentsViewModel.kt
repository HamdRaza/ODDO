package com.tom.chef.ui.auth.inPutDocuments

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.newBase.BaseActivity

class InputDocumentsViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var inputDocumentInterface: InputDocumentInterface

    fun submitDocuments() = inputDocumentInterface.submitDocuments()

}