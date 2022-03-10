package com.shoparty.android.ui.contactus

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.shoparty.android.R
import com.shoparty.android.app.MyApp
import com.shoparty.android.databinding.ActivityContactUsBinding
import com.shoparty.android.ui.main.myaccount.termandcondition.TermAndConditionActivity
import com.shoparty.android.utils.Constants

import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import java.lang.ProcessBuilder.Redirect.to


class ContactUsActivity : AppCompatActivity(){
    private var facebookurl: String?=""
    private var twitter_url: String?=""
    private var youtube_url: String?=""
    private var instagram_url: String?=""
    private lateinit var binding: ActivityContactUsBinding
    private lateinit var viewModel: ContactUsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_contact_us)
        viewModel = ViewModelProvider(this, ViewModalFactory(MyApp.application))[ContactUsViewModel::class.java]
        initialise()
        setObserver()
    }

    private fun initialise() {
        viewModel.getContactus()                                    //api call
        binding.infoTool.tvTitle.text = getString(R.string.contact_us)
        binding.infoTool.ivDrawerBack.setOnClickListener {
            onBackPressed()
        }
        binding.txtNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+binding.txtNumber.text.toString())
            startActivity(intent)
        }

        binding.txtWhatsappNo.setOnClickListener {
            Utils.showShortToast(this,"Coming Soon")
        }

        binding.ivFacebook.setOnClickListener {
            val intent = Intent (this, WebViewActivity::class.java)
            intent.putExtra(Constants.LINKSTATUS,"1")
            intent.putExtra(Constants.FACEBOOKLINK,facebookurl)
            startActivity(intent)
        }


        binding.ivTwitter.setOnClickListener {
            val intent = Intent (this, WebViewActivity::class.java)
            intent.putExtra(Constants.LINKSTATUS,"2")
            intent.putExtra(Constants.TWITTERLINK,twitter_url)
            startActivity(intent)
        }

        binding.ivYoutube.setOnClickListener {
            val intent = Intent (this, WebViewActivity::class.java)
            intent.putExtra(Constants.LINKSTATUS,"3")
            intent.putExtra(Constants.YOUTUBELINK,youtube_url)
            startActivity(intent)
        }

        binding.ivInsta.setOnClickListener {
            val intent = Intent (this, WebViewActivity::class.java)
            intent.putExtra(Constants.LINKSTATUS,"4")
            intent.putExtra(Constants.INSTAGRAMELINK,instagram_url)
            startActivity(intent)
        }


    }



    @SuppressLint("SetTextI18n")
    private fun setObserver()
    {
        viewModel.contactus.observe(this, { response ->
            when (response)
            {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    binding.txtNumber.text=response.data?.contact_no
                    binding.txtWhatsappNo.text=response.data?.whatsapp_no
                   facebookurl=response.data?.facebook_url
                    twitter_url=response.data?.twitter_url
                    youtube_url=response.data?.youtube_url
                    instagram_url=response.data?.instagram_url

                    binding.txtPhone.text=response.data?.contact_time_start+" "+getString(R.string.txtto)+" "+
                            response.data?.contact_time_end+" " +
                            getString(R.string.ksatime)+" "+response.data?.contact_day_start+" "+getString(R.string.txtto)+" "+
                            response.data?.contact_day_end


                    binding.txtWhatsapp.text=response.data?.whatsapp_time_start+" "+getString(R.string.txtto)+" "+
                            response.data?.whatsapp_time_end+" " +
                            getString(R.string.ksatime)+" "+response.data?.whatsapp_day_start+" "+getString(R.string.txtto)+" "+
                            response.data?.whatsapp_day_end
                }
                is Resource.Loading -> {
                    com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                }
                else -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                }
            }
        })



    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}