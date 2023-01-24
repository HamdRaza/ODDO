package com.tom.chef.ui.dashboard.fragments.account.subFragments.faq

import com.tom.chef.models.FaqResponse
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.faq.FAQItemAdapter
import com.tom.chef.ui.comman.faq.FAQItemViewModel
import com.tom.chef.ui.comman.faq.FAQtemInterface

class FAQViewModel : ViewModel, FAQtemInterface {

    var faqItemAdapter = FAQItemAdapter(ArrayList())

    fun fillFAQ(faqResponse: FaqResponse) {
        val viewModels = ArrayList<ViewModel>()
        faqResponse.oData.forEachIndexed { index, data ->
            val viewModel = FAQItemViewModel(data)
            if (index == (faqResponse.oData.size - 1)) {
                viewModels.add(viewModel.also {
                    it.isLast.set(true)
                })
            } else {
                viewModels.add(viewModel)
            }
        }
        faqItemAdapter.setList(viewModels)
    }
}