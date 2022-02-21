package com.shoparty.android.ui.shoppingbag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shoparty.android.R
import com.shoparty.android.ui.mainactivity.home.HomeCategoriesModel
import com.shoparty.android.ui.login.LoginActivity

import kotlinx.android.synthetic.main.activity_shoping_bag.*

class ShopingBagActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoping_bag)

        fillBagItemRecyclerView(bagItemList)
        bag_item_checkout_btn.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private val bagItemList = listOf<HomeCategoriesModel>(
        HomeCategoriesModel("Ballons"),
        HomeCategoriesModel("Ballons"),
        )

    private fun fillBagItemRecyclerView(seasons: List<HomeCategoriesModel>) {
        bag_item_recycler.adapter = ShopingBagItemAdapter(bagItemList)

        bag_item_pickup_recycler.adapter = ShopingBagPickupAdapter(bagItemList)

    }
}