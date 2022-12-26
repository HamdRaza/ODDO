package com.tom.chef.ui.dashboard.fragments.account.subFragments.changePassword

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.chef.R
import com.tom.chef.data.notifications.NotificationModel
import com.tom.chef.databinding.FragmentChangePasswordBinding
import com.tom.chef.databinding.FragmentHomeNotificationBinding
import com.tom.chef.newBase.BaseFragment
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.dialogs.ConfirmDialogInterface
import com.tom.chef.ui.dialogs.ConfirmDialogViewModel
import com.tom.chef.ui.dialogs.ConfirmationDialog
import com.tom.chef.utils.UiState
import com.tom.chef.utils.myToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment() {
    private lateinit var binding: FragmentChangePasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentChangePasswordBinding.bind(inflater.inflate(R.layout.fragment_change_password, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(showToolbar = true, showBackButton = true, backButtonText = "Change Password")
        mainActivity.toolbarVM.makeBackRound(isRound = true)



    }

    override fun onResume() {
        super.onResume()
    }

}