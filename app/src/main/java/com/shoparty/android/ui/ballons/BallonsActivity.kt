package com.shoparty.android.ui.ballons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityReturnPolicyBinding


class BallonsActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityReturnPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_ballons)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_ballons)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText(getString(R.string.return_policy))
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_drawer_back -> {
                onBackPressed()
            }

        }
    }
}