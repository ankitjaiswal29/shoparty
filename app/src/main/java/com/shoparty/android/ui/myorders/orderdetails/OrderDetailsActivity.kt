package com.shoparty.android.ui.myorders.orderdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityOrderDetailsBinding
import com.shoparty.android.ui.cancelorder.cancelorder.CancelOrderActivity

class OrderDetailsActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityOrderDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_order_details)
        initialise()

    }
    private fun initialise() {

        val message = intent.getStringExtra("data")

        if (message.equals("1")){

        }else{
            binding.group1.visibility=View.GONE
            binding.group2.visibility=View.GONE
            binding.ivOrderDelivered.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_order_cancelled_pink));



        }

       /* var activitydata: String? = intent.getStringExtra("data")
        if(activitydata.equals("ordersuccess")){
            binding.group1.visibility=View.VISIBLE
        }else{
            binding.group1.visibility=View.GONE
        }*/
    binding.infoTool.tvTitle.setText(R.string.orderdetails)
        binding.infoTool.back.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
             R.id.btnCancel -> {
                  val intent = Intent(this, CancelOrderActivity::class.java)
                 intent.putExtra("key","OrderDetails")
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