package com.tom.chef.ui.comman.profile

import android.view.View
import androidx.databinding.ObservableField
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.dashboard.fragments.account.subFragments.changePassword.ChangePasswordFragment
import com.tom.chef.ui.dashboard.fragments.account.subFragments.editProfile.EditProfileFragment
import com.tom.chef.ui.dashboard.fragments.account.subFragments.faq.FAQFragment
import com.tom.chef.ui.dashboard.fragments.account.subFragments.financial.FinanceFragment
import com.tom.chef.ui.dashboard.fragments.account.subFragments.orderHistory.OrderHistoryFragment
import com.tom.chef.ui.dashboard.fragments.account.subFragments.support.SupportFragment
import com.tom.chef.ui.dashboard.fragments.menu.MenuFragment

class ProfileItemViewModel(val mActivity: BaseActivity, val item: String) : ViewModel {

    lateinit var profileInterface: ProfileInterface

    @JvmField
    val title = ObservableField<String>()

    init {
        title.set(item)
    }

    override fun close() {
    }

    fun onMenuClicked(view: View) {
        when (item) {
            "Change Password" -> {
                mActivity.replaceFragment(ChangePasswordFragment())
            }
            "Financial" -> {
                mActivity.replaceFragment(FinanceFragment())
            }
            "FAQ" -> {
                mActivity.replaceFragment(FAQFragment())
            }
            "Order History" -> {
                mActivity.replaceFragment(OrderHistoryFragment())
            }
            "Support" -> {
                mActivity.replaceFragment(SupportFragment())
            }
            "Edit Profile" -> {
                mActivity.replaceFragment(EditProfileFragment())
            }
            "Menu Setup" -> {
                mActivity.replaceFragment(MenuFragment())
            }

            else -> {

            }
        }
    }

    override fun onItemClicked(view: View) {

    }

}