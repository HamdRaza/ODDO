package com.tom.chef.ui.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.databinding.DataBindingUtil
import com.tom.chef.R
import com.tom.chef.databinding.ActivityWebViewBinding
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.toolBar.ToolBarInterface
import com.tom.chef.ui.profile.ProfileActivity
import com.tom.chef.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val TAG = "RssNewsDetailsFragment"
private const val URL="URL"

@AndroidEntryPoint
class WebViewActivity : BaseActivity(), WebViewContracts ,ToolBarInterface{

    companion object {
        fun getIntent(context: Context,urlToShow: String): Intent {
            return Intent(context, WebViewActivity::class.java).putExtra(URL,urlToShow)
        }
    }

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var binding: ActivityWebViewBinding
    lateinit var webViewVM: WebViewVM

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        webViewVM= WebViewVM()
        binding.viewModel = webViewVM
        webViewVM.viewInteractor = this
        webViewVM.setData()
        webViewVM.toolBarViewModel.toolBarInterface=this
    }




    override fun initiate() {
        CookieManager.getInstance().setAcceptCookie(true);
        window.setWhiteColor(this)
        window.makeTransparentStatusBarBlack()
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(binding.webview, true)
        intent?.extras?.getString(URL)?.let {
            binding.webview.settings.run {
                javaScriptEnabled=true
                useWideViewPort=false
                loadWithOverviewMode=true
                allowFileAccess=true
                setJavaScriptCanOpenWindowsAutomatically(true);
                setSavePassword(true);
                setSaveFormData(true);
                setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                setUserAgentString("Android");

                domStorageEnabled = true;
                databaseEnabled = true;

            }
            startAnim()
            binding.webview.apply {
                webChromeClient=object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        binding.pbTest.progress = newProgress
                        if (newProgress == 100) {
                           stopAnim()
                        }
                    }

                }
                binding.webview.webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView, url: String) {
                        super.onPageFinished(view, url)
                        cookieManager.flush()
                    }
                    override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                        return false
                    }

                }
                loadUrl(it)
            }
        }



        sharedPreferenceManager.getSavedUser()?.let {
            webViewVM.profileViewModel.updateUserDetails(it)
        }
        webViewVM.toolBarViewModel.manageToolBar(showToolbar = true, showUserProfile = true, showMinimizer = true)
        binding.llProgressBlogDetail.makeGone()
    }

    override fun ShowLoading() {
        startAnim()
    }

    override fun HideLoading() {
       stopAnim()
    }

    override fun reloadWebPage() {
        binding.webview.reload()
        startAnim()

    }

    override fun checkForBackExit() {
        finish()
    }

    override fun onBackClicked() {
        checkForBackExit()
    }

    override fun moveToProfile() {
        startActivity(ProfileActivity.getIntent(this))
    }
}