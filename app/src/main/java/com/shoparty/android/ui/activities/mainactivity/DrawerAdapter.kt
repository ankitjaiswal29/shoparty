package com.shoparty.android.ui.activities.mainactivity

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.ui.mainactivity.home.HomeCategoriesModel
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.drawer_list_item_layout.view.*
import kotlinx.android.synthetic.main.home_season_layout_item.view.*

import kotlinx.android.synthetic.main.home_seasons_item.view.*

class DrawerAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<DrawerAdapter.HomeSeasonsViewHolder>() {
    inner class HomeSeasonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSeasonsViewHolder {
        return HomeSeasonsViewHolder(parent.inflate(R.layout.drawer_list_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeSeasonsViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            tv_title.text = items.name
        }
    }
}