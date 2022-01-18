package com.example.shoparty_android.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.helper.inflate
import com.example.shoparty_android.model.SearchHistoryModel
import kotlinx.android.synthetic.main.search_item_lay.view.*

class SearchHistoryAdapter(private val itemList: List<SearchHistoryModel>): RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {
    inner class SearchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder(parent.inflate(R.layout.search_item_lay))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: SearchHistoryAdapter.SearchHistoryViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            search_item_name_tv.text = items.name
        }
    }
}