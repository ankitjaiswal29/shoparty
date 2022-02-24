package com.shoparty.android.ui.address.addaddress.addaddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityAddNewAddressBinding
import com.shoparty.android.ui.address.addaddress.getaddress.AddressActivity


class AddNewAddressActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddNewAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_add_new_address)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_add_new_address)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText("Add New Address")
        binding.btnCan.setOnClickListener(this)
        binding.btnSav.setOnClickListener(this)
        binding.infoTool.back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_can -> {
                finish()
            }
            R.id.btn_sav -> {
                /*val intent = Intent(this, MyAccountActivity::class.java)
                startActivity(intent)*/
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