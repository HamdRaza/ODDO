package com.tom.chef.ui.dashboard.fragments.account

import android.app.Activity
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.models.ProfileResponse2
import com.tom.chef.utils.makeNull

class AccountViewModel(val mActivity: Activity) : ViewModel() {

    lateinit var accountInterface: AccountInterface

    @JvmField
    val profilePath = ObservableField<Any?>()

    @JvmField
    val coverImage = ObservableField<Any?>()

    @JvmField
    val userName = ObservableField<String>()

    @JvmField
    val brandName = ObservableField<String>()

    @JvmField
    val description = ObservableField<String>()

    @JvmField
    val startTime = ObservableField<String>()

    @JvmField
    val endTime = ObservableField<String>()

    @JvmField
    val cuisine = ObservableField<String>()

    @JvmField
    val orderType = ObservableField<String>()

    @JvmField
    val preparationTime = ObservableField<String>()

    @JvmField
    val preparationUnits = ObservableField<String>()

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
        brandName.set(user.brandName)
        startTime.set(user.startTime)
        endTime.set(user.endTime)
        description.set(user.aboutMe)
        userAddress.set("${user.building}, ${user.street}, ${user.landmark}")
        profilePath.set(user.image)
        coverImage.set(user.coverImage)
        when (user.allowOrdertype) {
            "0" -> orderType.set("Both")
            "1" -> orderType.set("Current")
            "2" -> orderType.set("Scheduled")
        }
        cuisine.set(user.chefCuisines?.get(0)?.cuisineName)
        preparationTime.set(user.preparationTime)
        when (user.preparationUnit) {
            "mins" -> preparationUnits.set("mins")
            "hour" -> preparationUnits.set("hrs")
            "day" -> preparationUnits.set("days")
        }
    }


    fun onBackClicked() {
        accountInterface.onBackClicked()
    }

}