package com.shoparty.android.ui.productdetails

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.filter_color_item_layout.view.*

class ColorAdapter(var context: Context, private val itemList: List<ProducatDetailsResponse.Color>,
                   var recyclerViewClickListener: RecyclerViewClickListener): RecyclerView.Adapter<ColorAdapter.TopSellingSubcategoriesViewHolder>()
{
    inner class TopSellingSubcategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    var check=false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingSubcategoriesViewHolder {
        return TopSellingSubcategoriesViewHolder(parent.inflate(R.layout.filter_color_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: TopSellingSubcategoriesViewHolder, position: Int)
    {
        val item = itemList[position]
        holder.itemView.apply {
            view_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor(item.color_code))
        }

        if(item.ischecked)
        {
            holder.itemView.iv_check.visibility=View.VISIBLE
        }
        else
        {
            holder.itemView.iv_check.visibility=View.GONE
        }
        holder.itemView.view_circle.setOnClickListener{

            item.ischecked = !item.ischecked
            recyclerViewClickListener.click(item.color_id.toString())
            notifyDataSetChanged()
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}