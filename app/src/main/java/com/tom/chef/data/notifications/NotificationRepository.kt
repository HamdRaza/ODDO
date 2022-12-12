package com.tom.chef.data.notifications

import com.tom.chef.utils.UiState


interface NotificationRepository {
    fun getAllNotifications(loadStart:Boolean, result: (UiState<List<NotificationModel>>) -> Unit)
    fun deleteANotification(nodeName:String)
    fun markAsReadNotification(nodeName:String)
    fun deleteAll()

}