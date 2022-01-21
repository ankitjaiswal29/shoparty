package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_payment)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_payment)
        initialise()
    }

    private fun initialise() {
        binding.tvAddCard.setOnClickListener(this)
        binding.btnContinewToPayment.setOnClickListener(this)
        binding.infoTool.tvTitle.setText("Payment")
        binding.infoTool.back.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
             R.id.tvAddCard -> {
                 val intent = Intent(this, AddCardActivity::class.java)
                 startActivity(intent)
             }
            R.id.btnContinewToPayment -> {

                val intent = Intent(this, OrderSuccessfulyActivity::class.java)
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