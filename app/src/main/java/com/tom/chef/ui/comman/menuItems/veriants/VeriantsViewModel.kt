package com.tom.chef.ui.comman.menuItems.veriants

import androidx.databinding.ObservableField
import com.tom.chef.R
import com.tom.chef.ui.comman.ViewModel

class VeriantsViewModel(val title: String):ViewModel {
    @JvmField
    var titleText=ObservableField<String>()

    init {
        titleText.set(title)
    }

}