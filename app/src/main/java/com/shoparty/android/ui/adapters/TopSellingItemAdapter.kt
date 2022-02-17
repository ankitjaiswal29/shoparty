package com.shoparty.android.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.helper.inflate
import com.shoparty.android.models.custommodel.TopSellingHomeModel

import kotlinx.android.synthetic.main.top_selling_item2.view.*

class TopSellingItemAdapter(private val itemList: List<TopSellingHomeModel>): RecyclerView.Adapter<TopSellingItemAdapter.TopSellingItemViewModel>() {

    inner class TopSellingItemViewModel(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingItemViewModel {
        return TopSellingItemViewModel(parent.inflate(R.layout.top_selling_item2))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: TopSellingItemAdapter.TopSellingItemViewModel, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            tsi_item_name.text = items.name
            tsi_item_price_tv.text = items.price
        }
    }
}