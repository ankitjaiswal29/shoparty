package com.shoparty.android.ui.myorders.cancelorder.cancelorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityCancelConfirmBinding
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsActivity
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsAdapter
import com.shoparty.android.utils.PrefManager


class CancelConfirmActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityCancelConfirmBinding
    private var order_id: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_cancel_confirm)
        initialise()
    }

    private fun initialise() {
        binding.tvViewordertitle.visibility=View.GONE
        binding.btnDone.setOnClickListener(this)
        binding.tvViewordertitle.setOnClickListener(this)
        order_id=intent.getStringExtra("order_id").toString()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnDone -> {
                val intent = Intent(this, OrderDetailsActivity::class.java)
                intent.putExtra("order_id",order_id)
                intent.putExtra("page_status","1")
                startActivity(intent)
                finish()
            }
        }
    }
}