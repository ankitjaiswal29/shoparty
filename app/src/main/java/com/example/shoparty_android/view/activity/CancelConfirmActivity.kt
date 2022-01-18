package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityCancelConfirmBinding
import com.example.shoparty_android.databinding.ActivityCancelOrderBinding

class CancelConfirmActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityCancelConfirmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_cancel_confirm)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_cancel_confirm)
        initialise()
    }

    private fun initialise() {

        binding.btnDone.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnDone -> {
                val intent = Intent(this, OrderCancelActivity::class.java)
                startActivity(intent)
            }
        }
    }
}