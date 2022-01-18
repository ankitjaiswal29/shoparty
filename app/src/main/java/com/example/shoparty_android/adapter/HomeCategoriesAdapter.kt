package com.example.shoparty_android.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.helper.inflate
import com.example.shoparty_android.model.HomeCategoriesModel
import kotlinx.android.synthetic.main.home_categories_item.view.*

class HomeCategoriesAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<HomeCategoriesAdapter.HomeCategoriesViewHolder>() {

    inner class HomeCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoriesViewHolder {
        return HomeCategoriesViewHolder(parent.inflate(R.layout.home_categories_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeCategoriesAdapter.HomeCategoriesViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            home_categories_item_name_tv.text = items.name
        }
    }
}