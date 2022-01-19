package com.example.shoparty_android.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.helper.inflate
import com.example.shoparty_android.model.HomeCategoriesModel
import kotlinx.android.synthetic.main.ts_subcategories_item.view.*

class TopSellingSubcategoriesAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<TopSellingSubcategoriesAdapter.TopSellingSubcategoriesViewHolder>() {
    inner class TopSellingSubcategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingSubcategoriesViewHolder {
        return TopSellingSubcategoriesViewHolder(parent.inflate(R.layout.ts_subcategories_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: TopSellingSubcategoriesAdapter.TopSellingSubcategoriesViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            ts_subcategories_item_name_tv.text = items.name
        }
    }
}