package com.shoparty.android.ui.verificationotp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityLoginBinding
import com.shoparty.android.databinding.ActivityVerificationBinding
import com.shoparty.android.ui.shipping.ShippingActivity
import com.shoparty.android.utils.Constants

import kotlinx.android.synthetic.main.activity_verification.*

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_verification)

        binding.tvMobileno.text= intent.getStringExtra(Constants.MOBILE)

        signin_btn.setOnClickListener {
            val intent = Intent(this, ShippingActivity::class.java)
            startActivity(intent)
        }
    }
}