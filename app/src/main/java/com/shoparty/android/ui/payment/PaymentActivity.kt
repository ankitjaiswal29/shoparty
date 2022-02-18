package com.shoparty.android.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityPaymentBinding
import com.shoparty.android.ui.addcard.AddCardActivity
import com.shoparty.android.ui.myorders.ordersuccess.OrderSuccessfulyActivity


class PaymentActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_payment)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_payment)
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