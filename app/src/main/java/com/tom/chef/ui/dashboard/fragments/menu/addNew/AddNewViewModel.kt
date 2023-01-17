package com.tom.chef.ui.dashboard.fragments.menu.addNew

import android.app.Activity
import android.net.Uri
import androidx.databinding.ObservableField
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.faq.FAQItemAdapter
import com.tom.chef.ui.comman.faq.FAQItemViewModel
import com.tom.chef.ui.comman.faq.FAQtemInterface
import com.tom.chef.ui.comman.financial.FinancialItemAdapter
import com.tom.chef.ui.comman.financial.FinancialItemInterface
import com.tom.chef.ui.comman.financial.FinancialItemViewModel
import com.tom.chef.ui.comman.menuItems.files.FileNamesAdopter
import com.tom.chef.ui.comman.menuItems.files.FileViewModel
import com.tom.chef.ui.comman.menuItems.files.FilesInterface
import com.tom.chef.ui.comman.menuItems.packageMenu.PackageAdopter
import com.tom.chef.ui.comman.menuItems.packageMenu.PackageInterface
import com.tom.chef.ui.comman.menuItems.packageMenu.PackageViewModel
import com.tom.chef.ui.comman.orders.OrderInterface
import com.tom.chef.ui.comman.orders.OrderAdopter
import com.tom.chef.ui.comman.orders.OrderViewModel
import com.tom.chef.ui.dashboard.fragments.menu.subFragments.AllMenuInterface
import com.tom.chef.utils.FileManager
import com.tom.chef.utils.ReduceImageSize
import java.io.File

class AddNewViewModel : ViewModel, PackageInterface, FilesInterface {

    lateinit var allMenuInterface: AllMenuInterface

    var fileNamesAdopter = FileNamesAdopter(ArrayList())

    fun addFile(uri: Uri, activity: Activity) {
        try {
            val imageUri = ReduceImageSize.compressImage(uri, activity)
            imageUri?.let { FileManager.getPath(activity, it) }?.let {
                val viewModel = FileViewModel(File(it))
                viewModel.filesInterface = this
                fileNamesAdopter.addNewItem(viewModel)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun onPickedFileClicked() {
        allMenuInterface.pickFileClicked()
    }

    @JvmField
    var haveVariant = ObservableField<Boolean>(false)

    fun onSubmitClicked() {
        allMenuInterface.onSubmitClicked()
    }

    var packageAdopter = PackageAdopter(ArrayList())

    fun showPackage(activity: Activity) {
        if (packageAdopter.itemCount == 0) {
            addNewBlock(activity = activity)
        }
    }

    fun addNewBlock(activity: Activity) {
        val viewModels = ArrayList<ViewModel>()
        val viewModel = PackageViewModel(activity = activity)
        viewModel.isAdded.set(false)
        viewModel.packageInterface = this
        viewModels.add(viewModel)
        packageAdopter.setList(viewModels)
    }

    override fun onDeleteClicked() {
        packageAdopter.getList().forEach {
            if (it.isThis()) {
                packageAdopter.removeItem(item = it)
                return
            }
        }
    }

    override fun onAddNewClicked(activity: Activity) {
        val viewModel = PackageViewModel(activity = activity)
        viewModel.isAdded.set(false)
        viewModel.packageInterface = this
        packageAdopter.addNewItem(viewModel)
    }


    override fun onDeleteFillClicked() {
        fileNamesAdopter.getList().forEach {
            if (it.isThis()) {
                fileNamesAdopter.removeItem(item = it)
                return
            }
        }
    }
}