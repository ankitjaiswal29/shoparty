package com.shoparty.android.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.ui.main.topselling.TopSellingActivity
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.home_season_layout_item.view.*

class HomeSeasonsAdapter(private val itemList: List<HomeCategoriesModel>, val requireContext: Context): RecyclerView.Adapter<HomeSeasonsAdapter.HomeSeasonsViewHolder>() {
    inner class HomeSeasonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSeasonsViewHolder {
        return HomeSeasonsViewHolder(parent.inflate(R.layout.home_season_layout_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeSeasonsViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            home_categories_item_name_tv.text = items.name
        }
        holder.itemView.cl_seasonroot_item.setOnClickListener {
            val intent = Intent(requireContext, TopSellingActivity::class.java)
            requireContext. startActivity(intent)
        }
    }
}