package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityOrderDetailsBinding
import com.example.shoparty_android.databinding.ActivityOrderDetailsBindingImpl
import com.example.shoparty_android.databinding.ActivityOrderSuccessfulyBinding
import kotlinx.android.synthetic.main.activity_order_details.*

class OrderDetailsActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityOrderDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_order_details)
        initialise()

    }
    private fun initialise() {
    binding.infoTool.tvTitle.setText(R.string.orderdetails)
        binding.btnCancel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
             R.id.btnCancel -> {
                  val intent = Intent(this, CancelOrderActivity::class.java)
                 startActivity(intent)
            }
        }
    }
}