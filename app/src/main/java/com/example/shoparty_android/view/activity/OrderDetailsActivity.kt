package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
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