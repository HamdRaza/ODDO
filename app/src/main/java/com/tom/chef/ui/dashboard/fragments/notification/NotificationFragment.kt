package com.tom.chef.ui.dashboard.fragments.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.chef.R
import com.tom.chef.data.notifications.NotificationModel
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
class NotificationFragment : BaseFragment(),NotificationsItemInterface,NotificationInterface {
    private lateinit var binding: FragmentHomeNotificationBinding
    private val mNotificationAdapter: NotificationAdapter = NotificationAdapter(ArrayList())

    val notificationViewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentHomeNotificationBinding.bind(inflater.inflate(R.layout.fragment_home_notification, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(showToolbar = true, showBackButton = true, showClearButton = true, backButtonText = "Notification")
        mainActivity.toolbarVM.makeBackRound(isRound = true)
        binding.viewModel=notificationViewModel
        notificationViewModel.notificationInterface=this

        notificationListSetup()

    }

    override fun onResume() {
        super.onResume()
    }

    private fun notificationListSetup(){
        with(binding) {
            recycleView.layoutManager = LinearLayoutManager(requireContext())
            recycleView.isNestedScrollingEnabled = false
            recycleView.adapter = mNotificationAdapter
        }
        loadDummyNotifications()
        updateNotificationDummy()
        //getAllNotifications()
    }

    private fun getAllNotifications(){
        mainActivity.vm.userAlNotifications.value?.let {
            if (it is UiState.Success){
                stopAnim()
                val viewModels = ArrayList<ViewModel>()
                it.data.forEach{
                    val model=NotificationItemViewModel(mActivity,it)
                    model.notificationsItemInterface=this
                    viewModels.add(model)
                    notificationViewModel.notificationRepository.markAsReadNotification(it.nodeKey)
                }
                mNotificationAdapter.setList(viewModels)
                notificationViewModel.showNoResult.set(viewModels.isEmpty())
            }
        }
        mainActivity.vm.userAlNotifications.observe(viewLifecycleOwner){
            when(it){
                is UiState.Failure->{
                    stopAnim()
                    mainActivity.myToast(it.error.toString())
                }
                is UiState.Loading->{
                    startAnim()
                }
                is UiState.Success->{
                    stopAnim()
                    val viewModels = ArrayList<ViewModel>()
                    it.data.forEach{
                        val model=NotificationItemViewModel(mActivity,it)
                        model.notificationsItemInterface=this
                        viewModels.add(model)
                    }
                    mNotificationAdapter.setList(viewModels)
                    notificationViewModel.showNoResult.set(viewModels.isEmpty())
                }

            }
        }
    }
    val allNotifications=ArrayList<NotificationModel>()
    fun loadDummyNotifications(){
        for (i in 0 until 20){
            allNotifications.add(NotificationModel())
        }
    }
    fun updateNotificationDummy(){
        val viewModels = ArrayList<ViewModel>()
        allNotifications.forEach{
            val model=NotificationItemViewModel(mActivity,it)
            model.notificationsItemInterface=this
            viewModels.add(model)
        }
        mNotificationAdapter.setList(viewModels)
        notificationViewModel.showNoResult.set(viewModels.isEmpty())
    }

    override fun deleteNotification(notificationModel: NotificationModel) {
        allNotifications.removeFirstOrNull()
        updateNotificationDummy()
        //notificationViewModel.deleteThisNotification(notificationModel)
    }



    override fun confirmDeleteAll() {
        allNotifications.clear()
        updateNotificationDummy()
        /*
        val viewModel= ConfirmDialogViewModel()
        viewModel.showDeleteNotificationAll()
        ConfirmationDialog(viewModel = viewModel,object : ConfirmDialogInterface {
            override fun onYesClicked() {
                 notificationViewModel.deleteConfirmed()
            }
        }).show(mActivity.supportFragmentManager,"limitDialog")*/
    }


}