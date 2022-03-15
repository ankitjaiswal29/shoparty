package com.shoparty.android.ui.myorders.cancelorder.cancelorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityOrderCancelBinding


class OrderCancelActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOrderCancelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_order_cancel)
        initialise()

    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText(R.string.cancel_order)
        binding.infoTool.back.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back -> {
              onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}