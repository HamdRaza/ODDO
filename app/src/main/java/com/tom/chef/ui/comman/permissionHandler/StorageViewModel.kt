package com.tom.chef.ui.comman.storage

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.infoDialog.InfoDialog
import com.tom.chef.ui.dialogs.infoDialog.InfoDialogInterface
import com.tom.chef.ui.infoDialog.InfoDialogViewModel
import com.tom.chef.utils.openAppSystemSettings
import com.tom.rider.ui.common.storage.StorageInterface

class StorageViewModel(private val registry : ActivityResultRegistry, val context: Activity) :DefaultLifecycleObserver {

     var storageInterface: StorageInterface?=null

    lateinit var storagePermissionRequest : ActivityResultLauncher<Array<String>>

    lateinit var owner: LifecycleOwner

    var permissionList=ArrayList<String>()

    override fun onCreate(owner: LifecycleOwner) {
        this.owner=owner
        storagePermissionRequest = registry.register("permission", owner, ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (!doesHavePermissions()){
                storageInterface?.showDialog()
                return@register
            }
            storageInterface?.storageProvided()
        }
    }
    fun checkForStoragePermission() {
        if (!doesHavePermissions()){
            storagePermissionRequest.launch(permissionList.toTypedArray())
            return
        }
        storageInterface?.storageProvided()
    }
    fun doesHavePermissions():Boolean{
        permissionList.forEach {
            if (ContextCompat.checkSelfPermission(context, it)!= PackageManager.PERMISSION_GRANTED){
                return true
            }
        }
       return true
    }

    fun waitForPermission(activity: BaseActivity,permission: String,callback: (Boolean) -> Unit){
        var message="Storage"

        storageInterface=object : StorageInterface {
            override fun showDialog() {
                val viewModel= InfoDialogViewModel()
                viewModel.infoDialogInterface=object : InfoDialogInterface {
                    override fun onYesClicked() {
                        activity.openAppSystemSettings()
                    }

                    override fun onNoClicked() {
                    }
                }
                viewModel.permissionRequired(message = "${message} permission is required for this action go to application settings -> permissions and enable " )
                InfoDialog(viewModel).show(activity.supportFragmentManager,"toast")
            }
            override fun storageProvided() {
                callback.invoke(true)
            }
        }
        checkForStoragePermission()
    }

}