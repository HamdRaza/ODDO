package com.tom.chef.ui.comman.menuItems.packageMenu

import android.annotation.SuppressLint
import com.tom.chef.R
import com.tom.chef.ui.comman.DataBindingRecyclerViewAdapter
import com.tom.chef.ui.comman.ViewModel

class PackageAdopter(viewModels: MutableList<ViewModel>) : DataBindingRecyclerViewAdapter(viewModels) {

    private val mViewModelMap = HashMap<Class<*>, Int>()



    init {
        mViewModelMap[PackageViewModel::class.java] = R.layout.recycle_create_menu_variant
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(itemList: ArrayList<ViewModel>) {
        mViewModels = itemList
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addNewItem(item: ViewModel){
        mViewModels.add(item)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(item: ViewModel){
        mViewModels.remove(item)
        notifyDataSetChanged()
    }

    fun getList():List<ViewModel>{
        return mViewModels
    }

    override val viewModelLayoutMap: Map<Class<*>, Int>
        get() = mViewModelMap

}