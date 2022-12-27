package com.tom.chef.ui.allBottomSheets.rejectOffer

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tom.chef.databinding.BottomSheetArabicDetailsBinding
import com.tom.chef.databinding.BottomSheetRejectOfferBinding
import com.tom.chef.ui.comman.menuItems.packageMenu.PackageViewModel

class RejectOfferBottomSheet {

    fun showRejectOffer(context: Activity,callBack:(Boolean)->Unit) {
        val dialog = BottomSheetDialog(context)
        val binding= BottomSheetRejectOfferBinding.inflate(context.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.nextButton.setOnClickListener {
            callBack.invoke(true)
            dialog.dismiss()
        }

        dialog.show()
    }
}