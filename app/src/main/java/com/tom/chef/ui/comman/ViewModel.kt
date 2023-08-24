package com.tom.chef.ui.comman

import android.view.View

interface ViewModel {
    fun close() {}
    fun onItemClicked(view: View) {}
    fun isThis(): Boolean {
        return false
    }
}