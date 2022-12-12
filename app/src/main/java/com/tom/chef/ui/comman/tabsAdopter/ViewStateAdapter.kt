package com.tom.chef.ui.comman.tabsAdopter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewStateAdapter(var fragments:ArrayList<Fragment>,fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}