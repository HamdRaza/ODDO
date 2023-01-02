package com.tom.chef.ui.allBottomSheets.carManBottomSheet

import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.timer.TimerItemAdapter
import com.tom.chef.ui.comman.timer.TimerViewModel
import com.tom.chef.utils.getAllDate

class CarManBottomSheetViewModel: ViewModel {

    var timerItemAdapter:TimerItemAdapter= TimerItemAdapter(ArrayList())

    fun fillAllTime(){
        val viewModels=ArrayList<ViewModel>()
        getAllDate().forEach {
            val viewModel=TimerViewModel(timer = it)
            viewModels.add(viewModel)
        }
        timerItemAdapter.setList(viewModels)
    }
}