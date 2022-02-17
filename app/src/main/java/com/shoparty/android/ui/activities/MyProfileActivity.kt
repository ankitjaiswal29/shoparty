package com.shoparty.android.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityMyProfileBinding


class MyProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMyProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_profile)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_my_profile)
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