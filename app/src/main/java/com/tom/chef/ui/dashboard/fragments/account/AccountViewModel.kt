package com.tom.chef.ui.dashboard.fragments.account

import android.app.Activity
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.R
import com.tom.chef.models.ProfileResponse
import com.tom.chef.models.ProfileResponse2
import com.tom.chef.models.auth.User
import com.tom.chef.utils.makeNull
import com.tom.chef.utils.maskPhone

class AccountViewModel(val mActivity: Activity) : ViewModel() {

    lateinit var accountInterface: AccountInterface

    @JvmField
    val profilePath = ObservableField<Any?>()

    @JvmField
    val coverImage = ObservableField<Any?>()

    @JvmField
    val userName = ObservableField<String>()

    @JvmField
    val userGmail = ObservableField<String>()

    @JvmField
    val userAddress = ObservableField<String>()

    @JvmField
    val userPhone = ObservableField<String>()

    @JvmField
    var backTitle = ObservableField<String>()

    init {
        backTitle.set("My Profile")
    }

    fun updateProfile(user: ProfileResponse2.OData) {
        this.userGmail.set(user.email)
        this.userPhone.set(user.phoneNumber)
        user.name.makeNull().let {
            this.userName.set(it)
        }
        userAddress.set("${user.building}, ${user.street}, ${user.landmark}")
        profilePath.set(user.image)
        coverImage.set(user.coverImage)
    }


    fun onBackClicked() {
        accountInterface.onBackClicked()
    }

}