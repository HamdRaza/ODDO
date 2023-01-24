package com.tom.chef.ui.comman.faq

import android.os.Build
import android.text.Html
import android.view.View
import androidx.databinding.ObservableField
import com.tom.chef.data.notifications.NotificationModel
import com.tom.chef.models.FaqResponse
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.dialogs.ConfirmDialogInterface
import com.tom.chef.ui.dialogs.ConfirmDialogViewModel
import com.tom.chef.ui.dialogs.ConfirmationDialog

class FAQItemViewModel(oData: FaqResponse.OData) : ViewModel {

    lateinit var faqItemViewModel: FAQItemViewModel

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mDescription = ObservableField<String>()

    @JvmField
    val isExpanded = ObservableField<Boolean>(false)

    @JvmField
    val isLast = ObservableField<Boolean>(false)

    init {
        mTitle.set(oData.titleEn)
        val description = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(oData.descEn, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(oData.descEn)
        }
        mDescription.set(description.toString())
    }

    fun toggleExpanded() {
        isExpanded.get()?.let {
            isExpanded.set(!it)
        }
    }

}