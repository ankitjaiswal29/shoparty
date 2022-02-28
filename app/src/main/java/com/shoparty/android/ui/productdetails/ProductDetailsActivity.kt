package com.shoparty.android.ui.productdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityCancelConfirmBinding
import com.shoparty.android.databinding.ActivityProductDetailsBinding
import com.shoparty.android.ui.customize.CustomizeActivity
import com.shoparty.android.ui.main.deals.TopSellingHomeModel
import com.shoparty.android.ui.main.home.TopSellingHomeAdapter

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
        binding.btnCostomizeit.setOnClickListener(this)
        binding.infoTool.tvTitle.setText("Tesla Toys")
        recyclar1()
        recyclar2()


    }
    private fun recyclar1() {
        val topSellingItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
        )
        binding.rvProductdetailsRecyclarview.adapter = ProductdetailsAdapter(topSellingItemList)
    }

    private fun recyclar2() {
        val topSellingItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
        )
        binding.rvProductdetailsRecyclarview2.adapter = ProductdetailsAdapter(topSellingItemList)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_drawer_back->{
                onBackPressed()
            }
            R.id.btn_costomizeit->{
                val intent = Intent (this, CustomizeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}