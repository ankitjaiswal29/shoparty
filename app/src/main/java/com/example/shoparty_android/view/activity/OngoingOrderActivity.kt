package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityOngoingOrderBinding
import com.example.shoparty_android.databinding.ActivityOrderDetailsBinding

class OngoingOrderActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOngoingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_ongoing_order)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_ongoing_order)
        initialise()
    }

    private fun initialise() {


        /* var activitydata: String? = intent.getStringExtra("data")
         if(activitydata.equals("ordersuccess")){
             binding.group1.visibility=View.VISIBLE
         }else{
             binding.group1.visibility=View.GONE
         }*/
        binding.infoTool.tvTitle.setText("OngoeingOrder")
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