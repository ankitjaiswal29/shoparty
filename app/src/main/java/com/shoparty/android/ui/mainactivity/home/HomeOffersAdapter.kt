package com.shoparty.android.ui.mainactivity.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.home_offers_item_layout.view.*


class HomeOffersAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<HomeOffersAdapter.HomeOffersViewHolder>()  {

    inner class HomeOffersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {
        return HomeOffersViewHolder(parent.inflate(R.layout.home_offers_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            home_offers_item_price_tv.text = items.name
        }
    }
}