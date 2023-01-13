package com.tom.chef.ui.auth.inPutDocuments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.permissionx.guolindev.PermissionX
import com.tom.chef.databinding.ActivityInputDocumentBinding
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.documentUpload.DocumentInterface
import com.tom.chef.ui.comman.documentUpload.DocumentItemAdapter
import com.tom.chef.ui.comman.documentUpload.DocumentViewModel
import com.tom.chef.utils.SharedPreferenceManager
import com.tom.chef.utils.getDocumentMenu
import com.tom.chef.utils.makeTransparentStatusBarBlack
import com.tom.chef.utils.setWhiteColor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class InputDocumentsActivity : BaseActivity(), InputDocumentInterface, DocumentInterface,
    PickiTCallbacks {
    private val SELECT_IMAGE_REQUEST = 101
    private lateinit var binding: ActivityInputDocumentBinding
    private lateinit var vm: InputDocumentsViewModel

    private var documentItemAdapter = DocumentItemAdapter(ArrayList())

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var tempId: String

    var pickiT: PickiT? = null

    var allGranted = false

    var currentPosition: Int = -1

    lateinit var viewModels: ArrayList<DocumentViewModel>

    var listPaths = ArrayList<String>()

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, InputDocumentsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, com.tom.chef.R.layout.activity_input_document)
        vm = InputDocumentsViewModel(this)
        tempId = sharedPreferenceManager.getTempId.toString()
        pickiT = PickiT(this, this, this)
        listPaths.add("")
        listPaths.add("")
        listPaths.add("")
        listPaths.add("")
        listPaths.add("")
        init()
        loadMenuRecycle()
        PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request { allGranted, grantedList, deniedList ->
                this.allGranted = allGranted
                if (allGranted) {
//                    openGallery()
                } else {
                    Toast.makeText(
                        this,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, SELECT_IMAGE_REQUEST)
    }

    private fun init() {
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        binding.viewModel = vm
        vm.inputDocumentInterface = this

        val items = listOf("EN", "AR")
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, items)
        binding.layoutChangeLanguage.languageInput.setAdapter(adapter)

        val type = arrayOf("mins", "hrs", "days")

        val adapter2 = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            type
        )
        binding.txtPrepUnit.setAdapter(adapter2)

    }

    private fun loadMenuRecycle() {
        with(binding) {
            recycleView.layoutManager = LinearLayoutManager(this@InputDocumentsActivity)
            recycleView.isNestedScrollingEnabled = false
            recycleView.adapter = documentItemAdapter
        }
        viewModels = ArrayList<DocumentViewModel>()
        getDocumentMenu().forEach {
            val model = DocumentViewModel(mActivity = this, it)
            model.documentInterface = this
            viewModels.add(model)
        }
        documentItemAdapter.setList(viewModels as ArrayList<ViewModel>)
    }

    override fun onDocumentClicked(pos: Int) {
        currentPosition = pos
        openGallery()
    }

    override fun onCleared(pos: Int) {
        if (pos < 5) {
            listPaths[pos] = ""
        }
    }

    override fun submitDocuments() {
        if (validate()) {

//            sharedPreferenceManager.isLogedIn=true
//            startActivity(MainActivity.getIntent(this))
//            finishAffinity()
        }
    }

    private fun validate(): Boolean {
        if (listPaths.size < 5) {
            Toast.makeText(this, "Attach all documents", Toast.LENGTH_SHORT).show()
            return false
        }
        listPaths.forEach {
            if (it.isBlank()) {
                Toast.makeText(this, "Attach all documents", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        if (binding.txtPrepTime.text.isEmpty()) {
            Toast.makeText(this, "Add Food Preparation time", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun PickiTonUriReturned() {
    }

    override fun PickiTonStartListener() {
    }

    override fun PickiTonProgressUpdate(progress: Int) {
    }

    //"Trade License (pdf/jpg only)", type = 1
//"Emirates ID (pdf/jpg only)", type = 2
//"Passport Copy (pdf/jpg only)", type = 3
//"Residency Visa (pdf/jpg only)", type = 4
//"Bank Account (pdf/jpg only)", type = 5
    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        if (currentPosition < 5) {
            listPaths[currentPosition] = path!!
            viewModels[currentPosition].selectedPath(path!!)
        }
    }

    override fun PickiTonMultipleCompleteListener(
        paths: java.util.ArrayList<String>?,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
    }

    override fun onBackPressed() {
        pickiT!!.deleteTemporaryFile(this)
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            pickiT!!.deleteTemporaryFile(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                pickiT!!.getPath(data?.data, Build.VERSION.SDK_INT)
            }
        }
    }

}