package com.shoparty.android.ui.main.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.ui.main.deals.TopSellingHomeModel
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.deals_item_layout.view.*
import kotlinx.android.synthetic.main.top_selling_layout_item.view.*
import kotlinx.android.synthetic.main.top_selling_layout_item.view.iv_background
import kotlinx.android.synthetic.main.top_selling_layout_item.view.iv_select
import kotlinx.android.synthetic.main.top_selling_layout_item.view.iv_unselect

class TopSellingHomeAdapter(private val itemList: List<TopSellingHomeModel>, var context: Context) :
    RecyclerView.Adapter<TopSellingHomeAdapter.TopSellingHomeViewHolder>() {

    var fav = false;

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

        }
        holder.itemView.top_selling_home_item_root.setOnClickListener {
            Utils.showLongToast(context,context.getString(R.string.comingsoon))
        }

        holder.itemView.iv_background.setOnClickListener {
            if (fav) {
                holder.itemView.iv_unselect.visibility = View.GONE
                holder.itemView.iv_select.visibility = View.VISIBLE
                fav = false
            } else {
                holder.itemView.iv_select.visibility = View.GONE
                holder.itemView.iv_unselect.visibility = View.VISIBLE
                fav = true
            }
        }
    }
}