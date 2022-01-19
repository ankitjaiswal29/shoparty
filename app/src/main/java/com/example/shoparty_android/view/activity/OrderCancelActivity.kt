package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityCancelOrderBinding
import com.example.shoparty_android.databinding.ActivityOrderCancelBinding

class OrderCancelActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOrderCancelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_cancel)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_order_cancel)
        initialise()

    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText(R.string.cancel_order)


    }

    override fun onClick(v: View?) {
        when(v?.id){

        }
    }
}