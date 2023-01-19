package com.tom.chef.ui.comman

import android.view.View
import com.tom.chef.models.PackageItem

interface ViewModel {
    fun close() {}
    fun onItemClicked(view: View) {}
    fun isThis(): Boolean {
        return false
    }

    fun getValues(): PackageItem? {
        return null
    }
}