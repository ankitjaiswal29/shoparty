package com.shoparty.android.ui.shoppingbag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityShopingBagBinding
import com.shoparty.android.ui.main.home.HomeCategoriesModel

import kotlinx.android.synthetic.main.activity_shoping_bag.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import android.content.Intent
import com.shoparty.android.ui.login.LoginActivity


class ShopingBagActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityShopingBagBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    setContentView(R.layout.activity_shoping_bag)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_shoping_bag)
        initialise()
       /* bag_item_checkout_btn.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        }*/
    }

    private fun initialise() {

        binding.infoTool.tvTitle.setText(getString(R.string.shippingbag))
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.btnProcessTocheckOut.setOnClickListener(this)
        val bagItemList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("$7.02"),
            HomeCategoriesModel("$7.02"),
        )


            binding.rvShopingitem.adapter = ShopingBagItemAdapter(bagItemList)

            //bag_item_pickup_recycler.adapter = ShopingBagPickupAdapter(bagItemList)
    }
    override fun onClick(v: View?) {
        when(v?.id){
           /* R.id.btnCancel -> {
                val intent = Intent(this, CancelOrderActivity::class.java)
                intent.putExtra("key","Ongoeing")
                startActivity(intent)
            }*/
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
            R.id.btn_ProcessTocheckOut -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}