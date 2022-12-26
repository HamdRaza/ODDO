package com.tom.chef.ui.dashboard.fragments.account.subFragments.faq

import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.faq.FAQItemAdapter
import com.tom.chef.ui.comman.faq.FAQItemViewModel
import com.tom.chef.ui.comman.faq.FAQtemInterface
import com.tom.chef.ui.comman.financial.FinancialItemAdapter
import com.tom.chef.ui.comman.financial.FinancialItemInterface
import com.tom.chef.ui.comman.financial.FinancialItemViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.comman.orders.OrderAdopter
import com.tom.chef.ui.comman.orders.OrderViewModel

class FAQViewModel: ViewModel,FAQtemInterface {

    var faqItemAdapter=FAQItemAdapter(ArrayList())

    fun fillFAQ(){
        val viewModels=ArrayList<ViewModel>()
        for (i in 0 until 5){
            val viewModel=FAQItemViewModel()
            viewModels.add(viewModel)
        }
        viewModels.add(FAQItemViewModel().also {
            it.isLast.set(true)
        })
        faqItemAdapter.setList(viewModels)
    }
}