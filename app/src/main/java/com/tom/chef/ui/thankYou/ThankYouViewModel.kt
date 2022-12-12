package com.tom.chef.ui.thankYou

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class ThankYouViewModel :ViewModel() {
    lateinit var thankYouInterface: ThankYouInterface

    @JvmField
    var orderId=ObservableField<String>()


    fun updateOrderId(bundle: Bundle){
        bundle.getString("invoiceId")?.let {
            orderId.set(it)
        }
    }

    fun onGotoHomeClicked(){
        thankYouInterface.onGoToHomeClicked()
    }
}