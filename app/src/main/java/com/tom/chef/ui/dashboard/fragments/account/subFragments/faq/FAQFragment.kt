package com.tom.chef.ui.dashboard.fragments.account.subFragments.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.tom.chef.R
import com.tom.chef.databinding.FragmentAllOrdersBinding
import com.tom.chef.databinding.FragmentDashboardHomeBinding
import com.tom.chef.databinding.FragmentDashboardHomeCurrentorderBinding
import com.tom.chef.databinding.FragmentFaqBinding
import com.tom.chef.databinding.FragmentFinanceBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails.OrderDetailsFragment
import com.tom.chef.ui.dashboard.fragments.notification.NotificationFragment
import com.tom.chef.utils.handleHull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FAQFragment :  BaseFragment(),FAQInterface{
    private lateinit var binding: FragmentFaqBinding

    lateinit var faqViewModel: FAQViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFaqBinding.bind(inflater.inflate(R.layout.fragment_faq, container, false))
        mActivity.toolbarVM.manageToolBar(showToolbar = true, showBackButton = true, backButtonText = "FAQS")
        mainActivity.toolbarVM.makeBackRound(isRound = true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        faqViewModel= FAQViewModel()
        faqViewModel.fillFAQ()
        binding.viewModel=faqViewModel
    }


}


