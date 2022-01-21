package com.example.shoparty_android.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.databinding.ActivityAboutUsBinding
import com.example.shoparty_android.databinding.ActivityTermAndConditionBinding

class TermAndConditionActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTermAndConditionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_term_and_condition)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_term_and_condition)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText("Terms And Conditions")
        binding.infoTool.back.setOnClickListener(this)
        // binding..setOnClickListener(this)
    }

    override fun onClick(v: View?) {
         when(v?.id){
             R.id.back -> {
                 onBackPressed()
             }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}