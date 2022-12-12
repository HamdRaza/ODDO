package com.tom.chef.ui.dashboard.fragments.notification

import com.tom.chef.data.notifications.NotificationModel

interface NotificationsItemInterface {
    fun deleteNotification(notificationModel: NotificationModel)
}