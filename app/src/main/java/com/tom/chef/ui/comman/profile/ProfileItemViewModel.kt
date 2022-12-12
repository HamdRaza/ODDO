package com.tom.chef.ui.comman.profile

import android.view.View
import androidx.databinding.ObservableField
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.ViewModel

class ProfileItemViewModel(val mActivity: BaseActivity, val item: String) : ViewModel {

    lateinit var profileInterface: ProfileInterface

   @JvmField
   val title = ObservableField<String>()

    init {
        title.set(item)
    }

    override fun close() {
    }

    fun onMenuClicked(view: View){
        when(item){

        }
    }

    override fun onItemClicked(view: View) {

    }

}