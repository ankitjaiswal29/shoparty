package com.shoparty.android.ui.returnpolicy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityReturnPolicyBinding


class ReturnPolicyActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityReturnPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_return_policy)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_return_policy)
        initialise()

    }

    private fun initialise() {

        // val recyclerview = findViewById<RecyclerView>(R.id.myorder_recyclerview)

        // this creates a vertical layout Manager
        binding.infoTool.tvTitle.setText(getString(R.string.return_policy))
        binding.infoTool.back.setOnClickListener(this)
        binding.myorderRecyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ReturnPolicyModel>()
        data.add(ReturnPolicyModel("1","Lorem Ipsum is simply"))
        data.add(ReturnPolicyModel("2","Lorem Ipsum is simply"))
        data.add(ReturnPolicyModel("3","Lorem Ipsum is simply dummy"))
        data.add(ReturnPolicyModel("4","Lorem Ipsum is simply dummy"))
        data.add(ReturnPolicyModel("5","Lorem Ipsum is simply dummy"))
        val adapter = ReturnPolicyAdapter(data)
        binding.myorderRecyclerview.adapter = adapter
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