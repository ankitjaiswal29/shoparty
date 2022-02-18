package com.shoparty.android.ui.mainactivity.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate

import kotlinx.android.synthetic.main.home_categories_item.view.*

class HomeCategoriesAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<HomeCategoriesAdapter.HomeCategoriesViewHolder>() {

    inner class HomeCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoriesViewHolder {
        return HomeCategoriesViewHolder(parent.inflate(R.layout.home_categories_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeCategoriesViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            home_categories_item_name_tv.text = items.name
        }
    }
}