package com.shoparty.android.ui.productdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityCancelConfirmBinding
import com.shoparty.android.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_product_details)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.ivBagBtn.visibility=View.VISIBLE
        binding.infoTool.tvTitle.setText("Tesla Toys")

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_drawer_back->{
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}