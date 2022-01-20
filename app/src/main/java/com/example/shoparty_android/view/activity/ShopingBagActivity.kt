package com.example.shoparty_android.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.HomeSeasonsAdapter
import com.example.shoparty_android.adapter.ShopingBagItemAdapter
import com.example.shoparty_android.adapter.ShopingBagPickupAdapter
import com.example.shoparty_android.model.HomeCategoriesModel
import kotlinx.android.synthetic.main.activity_shoping_bag.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.seasons_recycler

class ShopingBagActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoping_bag)

        fillBagItemRecyclerView(bagItemList)
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