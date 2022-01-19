package com.example.shoparty_android.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.helper.inflate
import com.example.shoparty_android.model.HomeCategoriesModel

class HomeBondsAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<HomeBondsAdapter.HomeBondsViewHolder>() {
    inner class HomeBondsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBondsViewHolder {
        return HomeBondsViewHolder(parent.inflate(R.layout.home_bonds_item_lay))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeBondsAdapter.HomeBondsViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            //new_arrival_item_name_tv.text = items.name
        }
    }
}