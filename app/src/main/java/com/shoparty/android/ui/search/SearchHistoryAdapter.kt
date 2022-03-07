package com.shoparty.android.ui.search

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate

import kotlinx.android.synthetic.main.search_item_lay.view.*

class SearchHistoryAdapter(private var itemList: List<SearchHistoryModel>): RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {
    inner class SearchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder(parent.inflate(R.layout.search_item_lay))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            search_item_name_tv.text = items.name
        }
    }

    fun setDataList(itemList: List<SearchHistoryModel>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}