package com.tom.chef.ui.dashboard.fragments.notification

import android.view.View
import androidx.databinding.ObservableField
import com.tom.chef.data.notifications.NotificationModel
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.dialogs.ConfirmDialogInterface
import com.tom.chef.ui.dialogs.ConfirmDialogViewModel
import com.tom.chef.ui.dialogs.ConfirmationDialog

class NotificationItemViewModel(val mActivity: BaseActivity,val notificationModel: NotificationModel) : ViewModel {

    lateinit var notificationsItemInterface: NotificationsItemInterface

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mDescription = ObservableField<String>()

    @JvmField
    val mDate = ObservableField<String>()

    init {
        mTitle.set(notificationModel.title)
        mDescription.set(notificationModel.description)
        if (notificationModel.createdAt.isNotEmpty()){
            mDate.set(notificationModel.createdAt)
        }
        if (notificationModel.createdDate.isNotEmpty()){
            mDate.set(notificationModel.createdDate)
        }

    }

    fun onClearNotificationClick(view: View) {
        val viewModel= ConfirmDialogViewModel()
        viewModel.showDeleteNotification()
        ConfirmationDialog(viewModel = viewModel,object : ConfirmDialogInterface {
            override fun onYesClicked() {
                notificationsItemInterface.deleteNotification(notificationModel = notificationModel)
            }
        }).show(mActivity.supportFragmentManager,"limitDialog")

    }
    override fun close() {
    }

    override fun onItemClicked(view: View) {

    }

    fun onNotificationClicked(view: View){
        when(notificationModel.localType()){

            else->{
            }
        }
    }
}