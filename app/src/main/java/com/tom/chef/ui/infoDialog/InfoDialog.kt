package com.tom.chef.ui.infoDialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.DialogFragment
import com.tom.chef.databinding.DialogInfoBinding


class InfoDialog(val infoDialogViewModel: InfoDialogViewModel, val callBack: ((Boolean) -> Unit)? =null) : DialogFragment() {
    lateinit var binding: DialogInfoBinding
    lateinit var myDialog: Dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        binding = DialogInfoBinding.inflate(requireActivity().layoutInflater)
        infoDialogViewModel.dialogDismissInterface=object : DialogDismissInterface {
            override fun onDismissClicked() {
                callBack?.invoke(true)
                dialog?.dismiss()
            }
        }
        binding.viewModel=infoDialogViewModel
        isCancelable=false
        builder.setView(binding.root)
         myDialog = builder.create()

         myDialog.adjustSize()

        return myDialog
    }


}
fun Context?.PxToDp(dpValue:Int):Int{
    try {
        this?.let {
            val dpRatio: Float = it.resources.displayMetrics.density
            return (dpValue * dpRatio).toInt()
        }
    }catch (e:Exception){
        e.printStackTrace()
    }
    return dpValue
}
fun Dialog.adjustSize(){
    window?.let {
        it.setLayout(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val margin = context.PxToDp(25)
        val inset = InsetDrawable(back, margin)
        it.setBackgroundDrawable(inset)
        it.requestFeature(Window.FEATURE_NO_TITLE)
    }
}

