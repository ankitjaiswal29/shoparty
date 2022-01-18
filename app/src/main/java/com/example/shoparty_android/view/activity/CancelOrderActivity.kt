package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityCancelOrderBinding
import com.example.shoparty_android.databinding.ActivityOrderDetailsBinding

class CancelOrderActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCancelOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_cancel_order)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_cancel_order)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText(R.string.cancel_order)
        binding.btnCancel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnCancel -> {
                val intent = Intent(this, CancelConfirmActivity::class.java)
                startActivity(intent)
            }
        }
    }
}