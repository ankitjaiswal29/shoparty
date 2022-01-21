package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityAddNewAddressBinding
import com.example.shoparty_android.databinding.ActivityOrderSuccessfulyBinding

class AddNewAddressActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddNewAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_add_new_address)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_new_address)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText("Add New Address")
        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.infoTool.back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSave -> {
                val intent = Intent(this, AddressActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btnCancel -> {
                /*val intent = Intent(this, MyAccountActivity::class.java)
                startActivity(intent)*/

                val intent = Intent(this, AddressActivity::class.java)
                startActivity(intent)
                finish()
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