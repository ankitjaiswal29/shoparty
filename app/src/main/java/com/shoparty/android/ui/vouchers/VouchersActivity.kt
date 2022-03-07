package com.shoparty.android.ui.vouchers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityVouchersBinding


class VouchersActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityVouchersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_vouchers)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText(getString(R.string.vouchers))
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.vouchersRecyclerview.layoutManager = LinearLayoutManager(this)
        voucherListing()
    }

    private fun voucherListing() {
        val data = ArrayList<String>()
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        val adapter = VouchersAdapter(data)
        binding.vouchersRecyclerview.adapter = adapter
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}