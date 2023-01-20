package com.tom.chef.ui.comman.financial

import android.view.View
import androidx.databinding.ObservableField
import com.tom.chef.data.notifications.NotificationModel
import com.tom.chef.models.FinancialResponse
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.dialogs.ConfirmDialogInterface
import com.tom.chef.ui.dialogs.ConfirmDialogViewModel
import com.tom.chef.ui.dialogs.ConfirmationDialog

class FinancialItemViewModel() : ViewModel {

    lateinit var financialItemInterface: FinancialItemInterface

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mDescription = ObservableField<String>()

    @JvmField
    val mDate = ObservableField<String>()

    fun update(financeData: FinancialResponse.OData.Label) {
        mTitle.set(financeData.amount.toString())
        mDescription.set(financeData.title.toString())
        mDate.set("28/04/2022")
    }
}