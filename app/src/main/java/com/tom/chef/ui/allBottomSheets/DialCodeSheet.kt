package com.tom.chef.ui.allBottomSheets

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ContextThemeWrapper
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tom.chef.R
import com.tom.chef.models.auth.ResponseCountries
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.allBottomSheets.adopter.DailogitemsListAdapter

class DialCodeSheet {

    fun dailCodeSheet(
        baseActivity: BaseActivity,
        heading:String,
        list: List<ResponseCountries.OData>,
        mCallBack:(ResponseCountries.OData)->Unit)
    {
        val wrappedContext = ContextThemeWrapper(baseActivity, R.style.ThemeOverlay_Demo_BottomSheetDialog)
       val dialog = BottomSheetDialog(wrappedContext)
        dialog.requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        dialog.setContentView(R.layout.common_dailog_sheet)
        dialog.dismissWithAnimation = true
        val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        bottomSheet!!.setBackgroundColor(baseActivity.resources.getColor(android.R.color.transparent))
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED


        val itemList: RecyclerView = dialog.findViewById(R.id.itemList)!!
        val cancelImg: ImageView = dialog.findViewById(R.id.cancelImg)!!
        val dialog_heading: TextView = dialog.findViewById(R.id.dialog_heading)!!

        dialog_heading.text = heading
        cancelImg.setOnClickListener {
            dialog.dismiss()
        }

        val layoutManager = LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL , false)
        val dailogitemsListAdapter = DailogitemsListAdapter(baseActivity,list){
          mCallBack.invoke(it)
         dialog.dismiss()
        }
        itemList.layoutManager = layoutManager
        itemList.adapter = dailogitemsListAdapter
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }
}