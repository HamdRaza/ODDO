package com.tom.chef.ui.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.tom.chef.databinding.DialogServiceLimitReachedBinding

class ConfirmationDialog(val viewModel: ConfirmDialogViewModel,val confirmDialogInterface: ConfirmDialogInterface,var isDissmissAble:Boolean=true) : DialogFragment() {
    lateinit var binding: DialogServiceLimitReachedBinding
    lateinit var myDialog: Dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        binding = DialogServiceLimitReachedBinding.inflate(requireActivity().layoutInflater)
        binding.viewModel=viewModel
        isCancelable=isDissmissAble
        viewModel.confirmDialogInterface=confirmDialogInterface
        viewModel.confirmDialogCurrentInterface=object :ConfirmDialogCurrentInterface{
            override fun onDismissClicked() {
                dismiss()
            }
        }
        builder.setView(binding.root)
         myDialog = builder.create()
        if (myDialog.window != null) {
            myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return myDialog
    }



}