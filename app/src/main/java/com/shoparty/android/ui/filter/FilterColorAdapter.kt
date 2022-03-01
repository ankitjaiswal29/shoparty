package com.shoparty.android.ui.filter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.filter_color_item_layout.view.*

import kotlinx.android.synthetic.main.ts_subcategories_item.view.*
import kotlinx.android.synthetic.main.ts_subcategories_item.view.ts_subcategories_item_name_tv

class FilterColorAdapter(private val itemList: List<String>): RecyclerView.Adapter<FilterColorAdapter.TopSellingSubcategoriesViewHolder>() {
    inner class TopSellingSubcategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingSubcategoriesViewHolder {
        return TopSellingSubcategoriesViewHolder(parent.inflate(R.layout.filter_color_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: TopSellingSubcategoriesViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            view_circle.setBackgroundColor(Color.parseColor(items));
        }
    }
}