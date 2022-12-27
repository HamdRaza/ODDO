package com.tom.chef.ui.comman.menuItems.files

import android.net.Uri
import androidx.databinding.ObservableField
import com.tom.chef.R
import com.tom.chef.ui.comman.ViewModel
import java.io.File

class FileViewModel(val file: File):ViewModel {
    lateinit var filesInterface: FilesInterface
    @JvmField
    var titleText=ObservableField<String>()

    init {
        titleText.set(file.name)
    }

    var delete=false

    fun onDeleteClicked(){
        delete=true
        filesInterface.onDeleteFillClicked()
    }

    override fun isThis(): Boolean {
        return delete
    }
}