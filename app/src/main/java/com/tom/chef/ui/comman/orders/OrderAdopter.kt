package com.tom.chef.ui.comman.orders

import android.annotation.SuppressLint
import com.tom.chef.R
import com.tom.chef.ui.comman.DataBindingRecyclerViewAdapter
import com.tom.chef.ui.comman.ViewModel

class OrderAdopter(viewModels: MutableList<ViewModel>) : DataBindingRecyclerViewAdapter(viewModels) {

    private val mViewModelMap = HashMap<Class<*>, Int>()



    init {
        mViewModelMap[OrderViewModel::class.java] = R.layout.recycle_order
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(itemList: ArrayList<ViewModel>) {
        mViewModels = itemList
        notifyDataSetChanged()
    }


    override val viewModelLayoutMap: Map<Class<*>, Int>
        get() = mViewModelMap

}