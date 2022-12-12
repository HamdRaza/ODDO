package com.tom.chef.ui.comman

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingRecyclerViewHolder<T : ViewDataBinding?>(val binding: T) : RecyclerView.ViewHolder(
    binding!!.root
)