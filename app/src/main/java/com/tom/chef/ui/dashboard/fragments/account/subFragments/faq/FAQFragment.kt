package com.tom.chef.ui.dashboard.fragments.account.subFragments.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tom.chef.R
import com.tom.chef.databinding.FragmentFaqBinding
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.newBase.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FAQFragment : BaseFragment(), FAQInterface {
    private lateinit var binding: FragmentFaqBinding

    lateinit var faqViewModel: FAQViewModel
    val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFaqBinding.bind(inflater.inflate(R.layout.fragment_faq, container, false))
        mActivity.toolbarVM.manageToolBar(
            showToolbar = true,
            showBackButton = true,
            backButtonText = "FAQS"
        )
        mainActivity.toolbarVM.makeBackRound(isRound = true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel.getFaq()
        appViewModel.getFaqLive.observe(viewLifecycleOwner) {
            if (it.status == "1") {
                faqViewModel = FAQViewModel()
                faqViewModel.fillFAQ(it)
                binding.viewModel = faqViewModel
            }
        }
    }


}


