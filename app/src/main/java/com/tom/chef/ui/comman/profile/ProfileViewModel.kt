package com.tom.chef.ui.comman.profile

import androidx.databinding.ObservableField
import com.tom.chef.models.auth.LoginResponse
import com.tom.chef.ui.comman.ViewModel

class ProfileViewModel:ViewModel {
    @JvmField
    var name=ObservableField<String>()
    @JvmField
    var profile=ObservableField<String?>()


    fun updateUserDetails(loginResponse: LoginResponse.Result){
        name.set(loginResponse.name)
    }
}