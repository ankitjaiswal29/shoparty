package com.shoparty.android.ui.mainactivity.home

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate
import com.shoparty.android.ui.mainactivity.deals.TopSellingHomeModel

import kotlinx.android.synthetic.main.top_selling_item.view.*
import kotlinx.android.synthetic.main.top_selling_item.view.item_name_tv
import kotlinx.android.synthetic.main.top_selling_item.view.top_selling_item_root
import kotlinx.android.synthetic.main.top_selling_layout_item.view.*

class TopSellingHomeAdapter(private val itemList: List<TopSellingHomeModel>): RecyclerView.Adapter<TopSellingHomeAdapter.TopSellingHomeViewHolder>() {

    inner class TopSellingHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingHomeViewHolder {
        return TopSellingHomeViewHolder(parent.inflate(R.layout.top_selling_layout_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: TopSellingHomeViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            tv_princesdresses.text = items.name
            tv_dressprise.text = items.price
          /*  top_selling_item_root.setOnClickListener {
                findNavController().navigate(R.id.topSellingItemListFragment)
            }*/
        }
    }
}