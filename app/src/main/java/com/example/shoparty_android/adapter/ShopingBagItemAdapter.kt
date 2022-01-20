package com.example.shoparty_android.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.helper.inflate
import com.example.shoparty_android.model.HomeCategoriesModel

class ShopingBagItemAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<ShopingBagItemAdapter.ShopingBagItemViewHolder>() {

    inner class ShopingBagItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopingBagItemViewHolder {
        return ShopingBagItemViewHolder(parent.inflate(R.layout.bag_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: ShopingBagItemAdapter.ShopingBagItemViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            //new_arrival_item_name_tv.text = items.name
        }
    }
}