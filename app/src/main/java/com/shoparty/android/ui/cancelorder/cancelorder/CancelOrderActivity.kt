package com.shoparty.android.ui.cancelorder.cancelorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityCancelOrderBinding

import com.shoparty.android.ui.cancelorder.cancelconfirm.CancelConfirmActivity


class CancelOrderActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCancelOrderBinding
    var message=""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_cancel_order)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_cancel_order)
        initialise()
    }

    private fun initialise() {

         message = intent.getStringExtra("key").toString()
        binding.infoTool.tvTitle.setText(R.string.cancel_order)
        binding.infoTool.back.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnCancel -> {
                val intent = Intent(this, CancelConfirmActivity::class.java)
                intent.putExtra("key",message)
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