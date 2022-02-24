package com.shoparty.android.ui.shoppingbag

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate
import com.shoparty.android.ui.main.home.HomeCategoriesModel


class ShopingBagPickupAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<ShopingBagPickupAdapter.ShopingBagPickupViewHolder>() {

    inner class ShopingBagPickupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopingBagPickupViewHolder {
        return ShopingBagPickupViewHolder(parent.inflate(R.layout.bag_pickup_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: ShopingBagPickupViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            //new_arrival_item_name_tv.text = items.name
        }
    }
}