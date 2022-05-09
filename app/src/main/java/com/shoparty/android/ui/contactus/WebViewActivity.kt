package com.shoparty.android.ui.contactus

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityWebViewBinding
import com.shoparty.android.utils.ProgressDialog

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        binding.infoTool.tvTitle.text =""
        binding.infoTool.ivDrawerBack.setOnClickListener {
            onBackPressed()
        }

        binding.webView?.settings?.javaScriptEnabled = true // enable javascript
        val activity: Activity = this
        ProgressDialog.showProgressBar(this)
        binding.webView?.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
                ProgressDialog.hideProgressBar()
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
                ProgressDialog.hideProgressBar()
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                ProgressDialog.hideProgressBar()
            }
        }

        if(intent.getStringExtra(com.shoparty.android.utils.Constants.LINKSTATUS).equals("1"))
        {
            binding.webView?.loadUrl(intent.getStringExtra(com.shoparty.android.utils.Constants.FACEBOOKLINK)
                .toString())
        }
        else if(intent.getStringExtra(com.shoparty.android.utils.Constants.LINKSTATUS).equals("2"))
        {
            binding.webView?.loadUrl(intent.getStringExtra(com.shoparty.android.utils.Constants.TWITTERLINK)!!)
        }
        else if(intent.getStringExtra(com.shoparty.android.utils.Constants.LINKSTATUS).equals("3"))
        {
            binding.webView?.loadUrl(intent.getStringExtra(com.shoparty.android.utils.Constants.YOUTUBELINK)!!)
        }
        else if(intent.getStringExtra(com.shoparty.android.utils.Constants.LINKSTATUS).equals("4"))
        {
            binding.webView?.loadUrl(intent.getStringExtra(com.shoparty.android.utils.Constants.INSTAGRAMELINK)!!)
        }


    }
}