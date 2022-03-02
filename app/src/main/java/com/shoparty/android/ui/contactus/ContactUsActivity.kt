package com.shoparty.android.ui.contactus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityContactUsBinding


class ContactUsActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityContactUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_contact_us)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.text = getString(R.string.contact_us)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_drawer_back -> {
                onBackPressed()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}