package com.tom.chef.ui.dashboard.fragments.account.subFragments.financial

import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.financial.FinancialItemAdapter
import com.tom.chef.ui.comman.financial.FinancialItemInterface
import com.tom.chef.ui.comman.financial.FinancialItemViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.comman.orders.OrderAdopter
import com.tom.chef.ui.comman.orders.OrderViewModel

class FinancialViewModel: ViewModel,FinancialItemInterface {

    var financialItemAdapter=FinancialItemAdapter(ArrayList())

    fun fillFinance(){
        val viewModels=ArrayList<ViewModel>()
        for (i in 0 until 10){
            val viewModel=FinancialItemViewModel()
            viewModel.financialItemInterface=this
            viewModels.add(viewModel)
        }
        financialItemAdapter.setList(viewModels)
    }
}