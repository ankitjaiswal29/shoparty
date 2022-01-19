package com.example.shoparty_android.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.helper.inflate
import com.example.shoparty_android.model.HomeCategoriesModel

class HomeOffersAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<HomeOffersAdapter.HomeOffersViewHolder>()  {

    inner class HomeOffersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {
        return HomeOffersViewHolder(parent.inflate(R.layout.home_offers_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeOffersAdapter.HomeOffersViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            //new_arrival_item_name_tv.text = items.name
        }
    }
}