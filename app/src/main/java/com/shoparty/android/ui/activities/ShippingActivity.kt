package com.shoparty.android.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityShippingBinding


class ShippingActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityShippingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_shipping)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_shipping)
        initialise()
    }
    private fun initialise() {
        binding.btnContinewToPayment.setOnClickListener(this)
        binding.infoTool.back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnContinewToPayment -> {
                val intent = Intent(this, PaymentActivity::class.java)
                startActivity(intent)
            }
            R.id.back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}