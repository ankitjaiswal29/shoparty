package com.example.shoparty_android.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.ReturnPolicyAdapter
import com.example.shoparty_android.databinding.ActivityContactUsBinding
import com.example.shoparty_android.databinding.ActivityReturnPolicyBinding
import com.example.shoparty_android.model.ReturnPolicyModel

class ContactUsActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityContactUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_contact_us)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_contact_us)
        initialise()
    }

    private fun initialise() {

        // val recyclerview = findViewById<RecyclerView>(R.id.myorder_recyclerview)

        // this creates a vertical layout Manager
        binding.infoTool.tvTitle.setText(getString(R.string.contact_us))
        binding.infoTool.back.setOnClickListener(this)

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