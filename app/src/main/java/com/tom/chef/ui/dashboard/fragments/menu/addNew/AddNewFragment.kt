package com.tom.chef.ui.dashboard.fragments.menu.addNew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.core.os.bundleOf
import com.tom.chef.R
import com.tom.chef.databinding.FragmentAddNewBinding
import com.tom.chef.databinding.FragmentAllOrdersBinding
import com.tom.chef.databinding.FragmentDashboardHomeBinding
import com.tom.chef.databinding.FragmentDashboardHomeCurrentorderBinding
import com.tom.chef.databinding.FragmentFaqBinding
import com.tom.chef.databinding.FragmentFinanceBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.menu.HomeMenuInterface
import com.tom.chef.ui.comman.menu.HomeMenuViewModel
import com.tom.chef.ui.comman.menuItems.files.FileViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.dashboard.fragments.home.subFragments.orderDetails.OrderDetailsFragment
import com.tom.chef.ui.dashboard.fragments.menu.subFragments.AllMenuInterface
import com.tom.chef.ui.dashboard.fragments.notification.NotificationFragment
import com.tom.chef.ui.dashboard.toolBar.VariantToggle
import com.tom.chef.utils.FileManager
import com.tom.chef.utils.ReduceImageSize
import com.tom.chef.utils.handleHull
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class AddNewFragment : BaseFragment(), VariantToggle, AllMenuInterface {
    private lateinit var binding: FragmentAddNewBinding

    lateinit var addNewViewModel: AddNewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewBinding.bind(
            inflater.inflate(
                R.layout.fragment_add_new,
                container,
                false
            )
        )
        mActivity.toolbarVM.manageToolBar(
            showToolbar = true,
            showBackButton = true,
            backButtonText = "Add  Item",
            showDishVariety = true
        )
        mainActivity.toolbarVM.makeBackRound(isRound = true)
        mainActivity.toolbarVM.variantToggle = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewViewModel = AddNewViewModel()
        addNewViewModel.allMenuInterface = this
        binding.viewModel = addNewViewModel
    }

    override fun updated(boolean: Boolean) {
        addNewViewModel.haveVariant.set(boolean)
        addNewViewModel.showPackage(mainActivity)
    }

    override fun onSubmitClicked() {
        mainActivity.vm.onBackButtonClicked()
    }

    override fun pickFileClicked() {
        pickFile.launch("image/*")
    }

    private val pickFile = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            addNewViewModel.addFile(it, activity = requireActivity())
        }
    }
}


