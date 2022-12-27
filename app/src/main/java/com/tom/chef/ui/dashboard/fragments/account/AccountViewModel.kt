package com.tom.chef.ui.dashboard.fragments.account

import android.app.Activity
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.R
import com.tom.chef.models.auth.User
import com.tom.chef.utils.makeNull
import com.tom.chef.utils.maskPhone

class AccountViewModel(val mActivity: Activity) :ViewModel() {

    lateinit var accountInterface: AccountInterface

    @JvmField
    val profilePath=ObservableField<Any?>()
    @JvmField
    val coverImage=ObservableField<Any?>()
    @JvmField
    val userName=ObservableField<String>()
    @JvmField
    val userGmail=ObservableField<String>()
    @JvmField
    val userAddress=ObservableField<String>()
    @JvmField
    val userPhone=ObservableField<String>()

    @JvmField
    var backTitle=ObservableField<String>()

    init {
        userName.set("Hamid Raza")
        userGmail.set("razahamid34@gmail.com")
        userPhone.set("+923441562554")
        userAddress.set("Al Quoz - Al Quoz 2 - Dubai - United Arab Emirates")
        backTitle.set("My Profile")
        profilePath.set(R.drawable.dummy_profile_icon)
        coverImage.set(R.drawable.dummy_profile_back)
    }

    fun updateProfile(user: User){
        this.userGmail.set(user.email)
        this.userPhone.set(user.maskPhone())
        user.name.makeNull().let {
            this.userName.set(it)
        }
    }


    fun onBackClicked(){
       accountInterface.onBackClicked()
    }

}