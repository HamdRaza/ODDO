package com.tom.chef.ui.allBottomSheets.arabicDetails

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tom.chef.databinding.BottomSheetArabicDetailsBinding
import com.tom.chef.ui.comman.menuItems.packageMenu.PackageViewModel

class ArabicBottomSheet {

    fun addArabicDetails(context: Activity,packageViewModel: PackageViewModel) {
        val dialog = BottomSheetDialog(context)
        val binding= BottomSheetArabicDetailsBinding.inflate(context.layoutInflater)
        dialog.setContentView(binding.root)
        binding.viewModel=packageViewModel
        dialog.setCanceledOnTouchOutside(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.nextButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}