package com.example.shoparty_android.view.activity

import android.content.Intent
import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityAddCardBinding


class AddCardActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_card)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_add_card)
   initialise()
    }
    private fun initialise() {
        binding.btnSave.setOnClickListener(this)
        binding.infoTool.tvTitle.setText("Add Card")
        binding.infoTool.back.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
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