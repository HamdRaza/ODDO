package com.tom.chef.ui.comman.faq

import android.view.View
import androidx.databinding.ObservableField
import com.tom.chef.data.notifications.NotificationModel
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.dialogs.ConfirmDialogInterface
import com.tom.chef.ui.dialogs.ConfirmDialogViewModel
import com.tom.chef.ui.dialogs.ConfirmationDialog

class FAQItemViewModel() : ViewModel {

    lateinit var faqItemViewModel: FAQItemViewModel

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mDescription = ObservableField<String>()

    @JvmField
    val isExpanded=ObservableField<Boolean>(false)
    @JvmField
    val isLast=ObservableField<Boolean>(false)

    init {
        mTitle.set("Amet minim mollit non deserunt ?")
        mDescription.set("Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint")
    }

    fun toggleExpanded(){
        isExpanded.get()?.let {
            isExpanded.set(!it)
        }
    }

}