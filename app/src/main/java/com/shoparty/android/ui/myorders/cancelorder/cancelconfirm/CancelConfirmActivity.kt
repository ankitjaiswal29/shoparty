package com.shoparty.android.ui.myorders.cancelorder.cancelconfirm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityCancelConfirmBinding
import com.shoparty.android.ui.myorders.cancelorder.cancelorder.OrderCancelActivity


class CancelConfirmActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityCancelConfirmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_cancel_confirm)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_cancel_confirm)
        initialise()
    }

    private fun initialise() {

       var  message = intent.getStringExtra("key").toString();

        if (message.equals("Ongoeing")){

            binding.tvViewordertitle.visibility=View.GONE
        }else{

        }
        binding.btnDone.setOnClickListener(this)
        binding.tvViewordertitle.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnDone -> {
                val intent = Intent(this, OrderCancelActivity::class.java)
                startActivity(intent)
            }
           /* R.id.tvViewordertitle -> {
                val intent = Intent(this, OrderCancelActivity::class.java)
                startActivity(intent)
            }*/
        }
    }
}