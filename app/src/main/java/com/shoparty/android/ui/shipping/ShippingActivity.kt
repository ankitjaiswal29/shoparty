package com.shoparty.android.ui.shipping

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityShippingBinding
import com.shoparty.android.ui.payment.PaymentActivity
import com.shoparty.android.utils.PrefManager


class ShippingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityShippingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping)
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.mainLayoutShip.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.infoTool.back.rotation = 0F
        }else {
            binding.mainLayoutShip.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.infoTool.back.rotation = 180F
        }
        initialise()
    }

    private fun initialise() {
        binding.btnContinewToPayment.setOnClickListener(this)
        binding.infoTool.back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
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