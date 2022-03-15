package com.shoparty.android.ui.shoppingbag

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityShopingBagBinding
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.home.HomeCategoriesModel
import com.shoparty.android.ui.shipping.ShippingActivity
import com.shoparty.android.utils.PrefManager


class ShopingBagActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityShopingBagBinding
    private var pickup_branch = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shoping_bag)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText(getString(R.string.shippingbag))
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.btnProcessTocheckOut.setOnClickListener(this)
        binding.cbPickupBranch.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding.bagItemPickupRecycler.visibility = View.VISIBLE
            } else {
                binding.bagItemPickupRecycler.visibility = View.GONE
            }
        }
        shoppingBagList()
        shoppingBagPickup()
    }

    private fun shoppingBagPickup() {
        val bagItemList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Bend the Trend"),
            HomeCategoriesModel("Bend the Trend"),
            HomeCategoriesModel("Bend the Trend")
        )

        binding.bagItemPickupRecycler.adapter = ShopingBagPickupAdapter(bagItemList)
    }

    private fun shoppingBagList() {
        val bagItemList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("$7.02"),
            HomeCategoriesModel("$7.02"),
        )

        binding.rvShopingitem.adapter = ShopingBagItemAdapter(bagItemList)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
            R.id.btn_ProcessTocheckOut -> {
                if (PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty()) {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(applicationContext, ShippingActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}