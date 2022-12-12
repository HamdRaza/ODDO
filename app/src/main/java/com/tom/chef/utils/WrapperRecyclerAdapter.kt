package com.tom.chef.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

abstract class WrapperRecyclerAdapter<T,K,>(
    private val layoutId: Int,
    private var data: List<T>,
    private val itemClickListener: (K) -> Unit
): RecyclerView.Adapter<WrapperRecyclerAdapter<T,K>.ViewHolder>() , Filterable {
    var newList: List<T> = ArrayList()

    fun updateItems(newItems: List<T>) {
        val oldItems = ArrayList(this.data)
        this.data=newItems
        newList=newItems
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return data.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId as Int, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: T) {
            setDataToView(itemView, item,adapterPosition ,itemClickListener)
        }
    }

    abstract fun setDataToView(itemView: View, item: T,position: Int, itemClickListener: (K) -> Unit)


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    newList = data
                } else {
                    val resultList = ArrayList<T>()
                    for (row in data) {
                        if(row is String){
                            if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                                resultList.add(row)
                            }
                        }
                    }
                    newList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = newList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                newList = results?.values as ArrayList<T>
                notifyDataSetChanged()
            }
        }
    }
}