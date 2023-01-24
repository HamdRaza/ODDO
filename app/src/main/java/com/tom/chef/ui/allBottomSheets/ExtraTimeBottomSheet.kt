package com.tom.chef.ui.allBottomSheets

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tom.chef.databinding.BottomSheetRejectOfferBinding
import com.tom.chef.databinding.BottomSheetRequestOfferBinding

class ExtraTimeBottomSheet {

    fun showExtraTime(activity: Activity, callBack: (Boolean, String) -> Unit) {
        val dialog = BottomSheetDialog(activity)
        val binding = BottomSheetRequestOfferBinding.inflate(activity.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.nextButton.setOnClickListener {
            callBack.invoke(true, binding.txtDescribe.text.toString())
            dialog.dismiss()
        }

        dialog.show()
    }
}