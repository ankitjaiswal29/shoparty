package com.shoparty.android.ui.myorders.ordersuccess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityOrderSuccessfulyBinding
import com.shoparty.android.ui.mainactivity.MainActivity
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsActivity


class OrderSuccessfulyActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityOrderSuccessfulyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_order_successfuly)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_order_successfuly)
        initialise()
    }

    private fun initialise() {
binding.tvViewordertitle.setOnClickListener(this)
        binding.btnDone.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvViewordertitle -> {
                 val intent = Intent(this, OrderDetailsActivity::class.java)
                intent.putExtra("data", "1")
                 startActivity(intent)
            }
            R.id.btnDone -> {
                /*val intent = Intent(this, MyAccountActivity::class.java)
                startActivity(intent)*/

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish();
            }
        }
    }
}