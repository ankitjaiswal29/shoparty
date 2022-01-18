package com.example.shoparty_android.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.helper.inflate
import com.example.shoparty_android.model.HomeCategoriesModel
import kotlinx.android.synthetic.main.home_seasons_item.view.*

class HomeSeasonsAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<HomeSeasonsAdapter.HomeSeasonsViewHolder>() {
    inner class HomeSeasonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSeasonsViewHolder {
        return HomeSeasonsViewHolder(parent.inflate(R.layout.home_seasons_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeSeasonsAdapter.HomeSeasonsViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            home_seasons_item_name_tv.text = items.name
        }
    }
}