package com.tom.chef.ui.allBottomSheets.adopter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tom.chef.R
import com.tom.chef.databinding.DailogSheetItemBinding
import com.tom.chef.models.auth.ResponseCountries
import com.tom.chef.utils.setShowCondition


class DailogitemsListAdapter(val context:Context,val itemList: List<ResponseCountries.OData> ,val mCallback: (ResponseCountries.OData)->Unit) : RecyclerView.Adapter<DailogitemsListAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding = DataBindingUtil.inflate<DailogSheetItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.dailog_sheet_item, parent, false
        )
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.mBinding.textname.text = itemList.get(position).name
        holder.mBinding.line.setShowCondition(position!=itemList.size.minus(1))
        holder.mBinding.textname.setOnClickListener {
            itemList.getOrNull(position)?.let {
             mCallback.invoke(it)
            }
        }

    }

    override fun getItemCount(): Int {
        return  itemList.size
    }



    inner class ViewHolder(val mBinding: DailogSheetItemBinding) :
        RecyclerView.ViewHolder(mBinding.root)

}