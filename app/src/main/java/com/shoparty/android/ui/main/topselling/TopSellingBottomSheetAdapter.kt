package com.shoparty.android.ui.main.topselling

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.top_selling_bottomsheet_recyclar_item_layout.view.*

import kotlinx.android.synthetic.main.ts_subcategories_item.view.*
import kotlinx.android.synthetic.main.ts_subcategories_item.view.ts_subcategories_item_name_tv

class TopSellingBottomSheetAdapter(private val itemList: List<String>,val recyclerViewClickListener: RecyclerViewClickListener): RecyclerView.Adapter<TopSellingBottomSheetAdapter.TopSellingSubcategoriesViewHolder>() {
    inner class TopSellingSubcategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingSubcategoriesViewHolder {
        return TopSellingSubcategoriesViewHolder(parent.inflate(R.layout.top_selling_bottomsheet_recyclar_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: TopSellingSubcategoriesViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            tv_sortItem.text = items

        }
        holder.itemView.cl_bottomitemlayout.setOnClickListener {
            recyclerViewClickListener.click(items)
        }

    }
}