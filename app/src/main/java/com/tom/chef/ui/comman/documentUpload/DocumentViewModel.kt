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

    private val position = documentItem.type - 1

    init {
        hintText.set(documentItem.hint)
    }

    fun onMenuClicked(view: View) {
        documentInterface.onDocumentClicked(position)
    }

    fun selectedPath(path: String) {
        documentPath.set(path)
        showClearButton.set(true)
    }

    fun clearText(view: View) {
        documentPath.set("")
        showClearButton.set(false)
        documentInterface.onCleared(position)
    }

}