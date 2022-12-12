package com.tom.chef.ui.comman.documentUpload

import android.view.View
import androidx.databinding.ObservableField
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.utils.DocumentItem

class DocumentViewModel(val mActivity: BaseActivity, val documentItem: DocumentItem) : ViewModel {

    lateinit var documentInterface: DocumentInterface

   @JvmField
   val hintText = ObservableField<String>()

    @JvmField
    val showClearButton = ObservableField<Boolean>()

    @JvmField
    val documentPath = ObservableField<String>()

    init {
        hintText.set(documentItem.hint)
    }

    override fun close() {
    }

    fun onMenuClicked(view: View){
        when(documentItem.type){

        }
    }

    override fun onItemClicked(view: View) {

    }
    fun clearText(){
        documentPath.set("")
        showClearButton.set(false)
    }

}