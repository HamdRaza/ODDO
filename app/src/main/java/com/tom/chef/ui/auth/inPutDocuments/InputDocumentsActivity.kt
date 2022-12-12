package com.tom.chef.ui.auth.inPutDocuments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.chef.R
import com.tom.chef.databinding.ActivityInputDocumentBinding
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.ui.comman.documentUpload.DocumentInterface
import com.tom.chef.ui.comman.documentUpload.DocumentItemAdapter
import com.tom.chef.ui.comman.documentUpload.DocumentViewModel
import com.tom.chef.ui.dashboard.MainActivity
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InputDocumentsActivity : BaseActivity(), InputDocumentInterface,DocumentInterface {
    private lateinit var binding: ActivityInputDocumentBinding
    private lateinit var vm: InputDocumentsViewModel

    private var documentItemAdapter=DocumentItemAdapter(ArrayList())

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, InputDocumentsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_input_document)
        vm=InputDocumentsViewModel(this)
        init()
        loadMenuRecycle()
    }
    private fun init(){
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        binding.viewModel = vm
        vm.inputDocumentInterface = this
    }

    private fun loadMenuRecycle() {
        with(binding) {
            recycleView.layoutManager = LinearLayoutManager(this@InputDocumentsActivity)
            recycleView.isNestedScrollingEnabled = false
            recycleView.adapter = documentItemAdapter
        }
        val viewModels = ArrayList<ViewModel>()
        getDocumentMenu().forEach {
            val model= DocumentViewModel(mActivity = this ,it)
            model.documentInterface=this
            viewModels.add(model)
        }
        documentItemAdapter.setList(viewModels)
    }

    override fun onDocumentClicked(documentType: Int) {

    }

    override fun submitDocuments() {
        startActivity(MainActivity.getIntent(this))
    }

}