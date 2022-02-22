package com.shoparty.android.ui.mainactivity.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate

import kotlinx.android.synthetic.main.ts_subcategories_item.view.*

class TopSellingSubcategoriesAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<TopSellingSubcategoriesAdapter.TopSellingSubcategoriesViewHolder>() {
    inner class TopSellingSubcategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingSubcategoriesViewHolder {
        return TopSellingSubcategoriesViewHolder(parent.inflate(R.layout.ts_subcategories_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: TopSellingSubcategoriesViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            ts_subcategories_item_name_tv.text = items.name
        }
    }
}