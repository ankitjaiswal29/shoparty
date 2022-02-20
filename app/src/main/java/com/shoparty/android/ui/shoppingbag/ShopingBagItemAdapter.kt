package com.shoparty.android.ui.shoppingbag

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate
import com.shoparty.android.ui.mainactivity.home.HomeCategoriesModel


class ShopingBagItemAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<ShopingBagItemAdapter.ShopingBagItemViewHolder>() {

    inner class ShopingBagItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopingBagItemViewHolder {
        return ShopingBagItemViewHolder(parent.inflate(R.layout.bag_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: ShopingBagItemViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            //new_arrival_item_name_tv.text = items.name
        }
    }
}