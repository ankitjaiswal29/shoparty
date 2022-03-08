package com.shoparty.android.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.ui.main.topselling.TopSellingActivity
import com.shoparty.android.utils.inflate

import kotlinx.android.synthetic.main.home_categories_item.view.*
import kotlinx.android.synthetic.main.home_categories_item.view.home_categories_item_name_tv
import kotlinx.android.synthetic.main.home_categories_layout_item.view.*

class HomeCategoriesAdapter(
    private val itemList: List<HomeCategoriesModel>,
    val requireContext: Context
): RecyclerView.Adapter<HomeCategoriesAdapter.HomeCategoriesViewHolder>() {

    inner class HomeCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoriesViewHolder {
        return HomeCategoriesViewHolder(parent.inflate(R.layout.home_categories_layout_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeCategoriesViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            home_categories_item_name_tv.text = items.name
        }
        holder.itemView.cl_category_item_root.setOnClickListener {
            val intent = Intent(requireContext, TopSellingActivity::class.java)
           requireContext. startActivity(intent)
        }
    }
}