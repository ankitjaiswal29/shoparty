package com.shoparty.android.ui.returnpolicy

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityReturnPolicyBinding


class ReturnPolicyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityReturnPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_return_policy)
        initialise()

    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText(getString(R.string.return_policy))
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.myorderRecyclerview.layoutManager = LinearLayoutManager(this)
        returnPolicyListing()
    }

    private fun returnPolicyListing() {
        // ArrayList of class ItemsViewModel
        val data = ArrayList<ReturnPolicyModel>()
        data.add(ReturnPolicyModel("1", "Lorem Ipsum is simply"))
        data.add(ReturnPolicyModel("2", "Lorem Ipsum is simply"))
        data.add(ReturnPolicyModel("3", "Lorem Ipsum is simply dummy"))
        data.add(ReturnPolicyModel("4", "Lorem Ipsum is simply dummy"))
        data.add(ReturnPolicyModel("5", "Lorem Ipsum is simply dummy"))
        val adapter = ReturnPolicyAdapter(data)
        binding.myorderRecyclerview.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}