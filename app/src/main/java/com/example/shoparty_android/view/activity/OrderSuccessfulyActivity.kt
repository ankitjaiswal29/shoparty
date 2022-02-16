package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import com.example.shoparty_android.MainActivity
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityAddCardBinding
import com.example.shoparty_android.databinding.ActivityOrderSuccessfulyBinding
import com.example.shoparty_android.view.fragment.MyAccountFragment

class OrderSuccessfulyActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityOrderSuccessfulyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_order_successfuly)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_order_successfuly)
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