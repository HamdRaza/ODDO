package com.tom.chef.ui.dashboard.fragments.menu.addNew

import android.app.Activity
import android.net.Uri
import androidx.databinding.ObservableField
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.menuItems.files.FileNamesAdopter
import com.tom.chef.ui.comman.menuItems.files.FileViewModel
import com.tom.chef.ui.comman.menuItems.files.FilesInterface
import com.tom.chef.ui.comman.menuItems.packageMenu.PackageAdopter
import com.tom.chef.ui.comman.menuItems.packageMenu.PackageInterface
import com.tom.chef.ui.comman.menuItems.packageMenu.PackageViewModel
import com.tom.chef.ui.dashboard.fragments.menu.subFragments.AllMenuInterface
import com.tom.chef.utils.FileManager
import com.tom.chef.utils.ReduceImageSize
import java.io.File

class AddNewViewModel : ViewModel, PackageInterface, FilesInterface {

    lateinit var allMenuInterface: AllMenuInterface

    var fileNamesAdopter = FileNamesAdopter(ArrayList())

    @JvmField
    var haveVariant = ObservableField<Boolean>(false)

    var packageAdopter = PackageAdopter(ArrayList())

    /**When clicked on Dish photo*/
    fun onPickedFileClicked() {
        allMenuInterface.pickFileClicked()
    }

    /**When image added from gallery, on returned*/
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

    /**When image removed from list*/
    override fun onDeleteFillClicked() {
        fileNamesAdopter.getList().forEachIndexed {index, element ->
            if (element.isThis()) {
                allMenuInterface.fileDeleted(index)
                fileNamesAdopter.removeItem(item = element)
                return
            }
        }
    }

    /**When switch turned on/off for variety*/
    fun showPackage(activity: Activity) {
        if (packageAdopter.itemCount == 0) {
            addNewBlock(activity = activity)
        }
    }

    /**After showPackage, add new block*/
    fun addNewBlock(activity: Activity) {
        val viewModels = ArrayList<ViewModel>()
        val viewModel = PackageViewModel(activity = activity)
        viewModel.isAdded.set(false)
        viewModel.packageInterface = this
        viewModels.add(viewModel)
        packageAdopter.setList(viewModels)
    }

    /**When close pressed on Package Details*/
    override fun onDeleteClicked() {
        packageAdopter.getList().forEach {
            if (it.isThis()) {
                packageAdopter.removeItem(item = it)
                return
            }
        }
    }

    /**When clicked on Add New Package/Update*/
    override fun onAddNewClicked(activity: Activity) {
        val viewModel = PackageViewModel(activity = activity)
        viewModel.isAdded.set(false)
        viewModel.packageInterface = this
        packageAdopter.addNewItem(viewModel)
    }

    /**When clicked on Submit*/
    fun onSubmitClicked() {
        allMenuInterface.onSubmitClicked()
    }

}