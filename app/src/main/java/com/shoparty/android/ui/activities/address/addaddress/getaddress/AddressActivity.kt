package com.shoparty.android.ui.activities.address.addaddress.getaddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityAddressBinding
import com.shoparty.android.ui.activities.address.addaddress.addaddress.AddNewAddressActivity


class AddressActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_address)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_address)
        initialise()
    }


    private fun initialise() {
        binding.infoTool.tvTitle.setText("Address")
        binding.tvAddnewaddress.setOnClickListener(this)
        binding.infoTool.back.setOnClickListener(this)
        binding.tvEdit.setOnClickListener(this)

        // binding..setOnClickListener(this)
    }

    override fun onClick(v: View?) {
         when(v?.id){
             R.id.tvAddnewaddress -> {
                 val intent = Intent(this, AddNewAddressActivity::class.java)

                 startActivity(intent)
             }
             R.id.tvEdit -> {
                 val intent = Intent(this, AddNewAddressActivity::class.java)
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