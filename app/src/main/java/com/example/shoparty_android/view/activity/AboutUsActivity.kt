package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityAboutUsBinding
import com.example.shoparty_android.databinding.ActivityOrderSuccessfulyBinding

class AboutUsActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var binding: ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_about_us)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_about_us)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText("About US")
       // binding..setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       /* when(v?.id){
            R.id.tvViewordertitle -> {
                val intent = Intent(this, OrderDetailsActivity::class.java)
                intent.putExtra("data", "ordersuccess")
                startActivity(intent)
            }
            R.id.btnSave -> {
                *//*val intent = Intent(this, MyAccountActivity::class.java)
                startActivity(intent)*//*

                val intent = Intent(this, AddNewAddressActivity::class.java)
                startActivity(intent)
            }
        }*/
    }
}