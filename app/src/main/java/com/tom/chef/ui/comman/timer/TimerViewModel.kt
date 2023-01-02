package com.tom.chef.ui.comman.timer

import androidx.databinding.ObservableField
import com.tom.chef.ui.comman.ViewModel

class TimerViewModel(var timer:String) : ViewModel {

   lateinit var timerInterface: TimerInterface

    @JvmField
    val mTitle = ObservableField<String>()


    init {
        mTitle.set(timer)
    }



}