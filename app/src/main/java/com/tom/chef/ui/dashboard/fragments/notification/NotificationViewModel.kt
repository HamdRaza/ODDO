package com.tom.chef.ui.dashboard.fragments.notification

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tom.chef.data.notifications.NotificationModel
import com.tom.chef.data.notifications.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(val notificationRepository: NotificationRepository ):ViewModel(){

lateinit var notificationInterface: NotificationInterface


    fun deleteThisNotification(notificationModel: NotificationModel){
        notificationRepository.deleteANotification(notificationModel.nodeKey)
    }

    fun deleteAllNotification(view: View){
        notificationInterface.confirmDeleteAll()
    }

    fun deleteConfirmed(){
        notificationRepository.deleteAll()
    }

    @JvmField
    var showNoResult = ObservableField<Boolean>()

    init {
        showNoResult.set(false)
    }




}