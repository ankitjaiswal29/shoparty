package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityMyProfileBinding
import com.example.shoparty_android.databinding.ActivityOrderSuccessfulyBinding

class MyProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMyProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_profile)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_profile)
        initialise()
    }

    private fun initialise() {
        binding.btnSave.setOnClickListener(this)
        binding.infoTool.back.visibility=View.VISIBLE
        binding.infoTool.tvTitle.setText(getString(R.string.my_account))
        binding.infoTool.back.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSave -> {
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