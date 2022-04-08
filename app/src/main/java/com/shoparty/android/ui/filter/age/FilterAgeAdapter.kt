package com.shoparty.android.ui.filter.age

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.*

class FilterAgeAdapter(private val itemList: List<AgeResponse.Result>,var context: Context): RecyclerView.Adapter<FilterAgeAdapter.TopSellingSubcategoriesViewHolder>() {
    inner class TopSellingSubcategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingSubcategoriesViewHolder {
        return TopSellingSubcategoriesViewHolder(parent.inflate(R.layout.filter_recyclar_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: TopSellingSubcategoriesViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val items = itemList[position]
        holder.itemView.tv_text.text = items.age_from+"-"+items.age_to

        if(position == lastCheckedPosition)
        {
            holder.itemView.cl_rootitem.background = ContextCompat.getDrawable(context, R.drawable.background_sellected_filter);
            holder.itemView.tv_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_24, 0)
        }
        else
        {
            holder.itemView.tv_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check_black, 0)
            holder.itemView.cl_rootitem.background = ContextCompat.getDrawable(context, R.drawable.background_unsellected_filter)
        }

        holder.itemView.tv_text.setOnClickListener(View.OnClickListener {
            val copyOfLastCheckedPosition = lastCheckedPosition
            lastCheckedPosition = position
            notifyItemChanged(copyOfLastCheckedPosition)
            notifyItemChanged(lastCheckedPosition)
        })
    }
}