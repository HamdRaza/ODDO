package com.tom.chef.ui.allBottomSheets.carManBottomSheet

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tom.chef.databinding.BottomSheetCarManBinding

class CarManBottomSheet {

    fun carManBottomSheet(context: Activity) {
        val dialog = BottomSheetDialog(context)
        val binding= BottomSheetCarManBinding.inflate(context.layoutInflater)
        dialog.setContentView(binding.root)
        val carManBottomSheetViewModel=CarManBottomSheetViewModel()
        binding.viewModel=carManBottomSheetViewModel
        carManBottomSheetViewModel.fillAllTime()
        dialog.setCanceledOnTouchOutside(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}