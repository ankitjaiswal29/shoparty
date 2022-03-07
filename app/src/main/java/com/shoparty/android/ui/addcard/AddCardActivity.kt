package com.shoparty.android.ui.addcard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityAddCardBinding
import com.shoparty.android.ui.payment.PaymentActivity


class AddCardActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_card)
        initialise()
    }

    private fun initialise() {
        binding.btnSave.setOnClickListener(this)
        binding.infoTool.tvTitle.setText(getString(R.string.add_card))
        binding.infoTool.back.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSave -> {

                val intent = Intent(this, PaymentActivity::class.java)
                startActivity(intent)
                finish()

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