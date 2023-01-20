package com.tom.chef.ui.dashboard.fragments.account.subFragments.financial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tom.chef.R
import com.tom.chef.databinding.FragmentFinanceBinding
import com.tom.chef.network.app_view_model.AppViewModel
import com.tom.chef.newBase.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FinanceFragment : BaseFragment(), FinancialInterface {
    private lateinit var binding: FragmentFinanceBinding

    lateinit var financialViewModel: FinancialViewModel

    val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinanceBinding.bind(
            inflater.inflate(
                R.layout.fragment_finance,
                container,
                false
            )
        )
        mActivity.toolbarVM.manageToolBar(
            showToolbar = true,
            showBackButton = true,
            backButtonText = "Financial Dashboard"
        )
        mainActivity.toolbarVM.makeBackRound(isRound = true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFinanceData()

    }

    private fun getFinanceData() {
        appViewModel.getEarnings()
        appViewModel.getEarningsLive.observe(viewLifecycleOwner) {
            if (it.status == "1") {
                financialViewModel = FinancialViewModel()
                financialViewModel.fillFinance(it.oData)
                binding.viewModel = financialViewModel
            } else {

            }
        }
    }


}


