package com.shoparty.android.ui.myorders.ordersuccess

import abhishekti7.unicorn.filepicker.utils.Utils
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityOrderSuccessfulyBinding
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsActivity
class OrderSuccessfulyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOrderSuccessfulyBinding
    private var orderid: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_successfuly)
        initialise()
    }

    private fun initialise() {
        binding.tvViewordertitle.setOnClickListener(this)
        binding.btnDone.setOnClickListener(this)
        binding.tvtotalquantity.text=getString(R.string.total_price_for_2_item)+""+" "+getString(R.string.items)
        orderid=intent.getStringExtra("order_id").toString()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvViewordertitle -> {
                val intent = Intent(this, OrderDetailsActivity::class.java)
                //  intent.putExtra("data", "1")
                  intent.putExtra("order_id",orderid)
                  startActivity(intent)
              //  com.shoparty.android.utils.Utils.showLongToast(this,getString(R.string.comingsoon))
            }
            R.id.btnDone -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish();
            }
        }
    }
}