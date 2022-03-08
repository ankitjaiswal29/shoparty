package com.shoparty.android.ui.main.myaccount.termandcondition

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityTermAndConditionBinding


class TermAndConditionActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTermAndConditionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_term_and_condition)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.text = getString(R.string.terms_and_conditions)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)

        binding.webView?.settings?.javaScriptEnabled = true // enable javascript
        val activity: Activity = this
        com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
        binding.webView?.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
                com.shoparty.android.utils.ProgressDialog.hideProgressBar()
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView,
                req: WebResourceRequest,
                rerr: WebResourceError
            ) {
                onReceivedError(
                    view,
                    rerr.errorCode,
                    rerr.description.toString(),
                    req.url.toString()
                )
                com.shoparty.android.utils.ProgressDialog.hideProgressBar()
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                com.shoparty.android.utils.ProgressDialog.hideProgressBar()
            }
        }
        binding.webView?.loadUrl(com.shoparty.android.utils.Constants.TERMANDCONDITION)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}