package com.shoparty.android.ui.filter.gender

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.Transliterator
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.filter_recyclar_gender_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.tv_text

class FilterGenderAdapter(private val itemList: List<String>,
                          var context: Context):
    RecyclerView.Adapter<FilterGenderAdapter.FilterGenderViewHolder>()
{
    private var lastCheckedPosition = -1
    inner class FilterGenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterGenderViewHolder {
        return FilterGenderViewHolder(parent.inflate(R.layout.filter_recyclar_gender_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: FilterGenderViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            tv_text.text = items
        }
        if(position == lastCheckedPosition)
       {
           holder.itemView.con_gender.background = ContextCompat.getDrawable(context, R.drawable.background_sellected_filter)
           holder.itemView.img_rightblack.visibility=View.GONE
           holder.itemView.img_rightcolorful.visibility=View.VISIBLE
       }
        else
       {
           holder.itemView.img_rightblack.visibility=View.VISIBLE
           holder.itemView.img_rightcolorful.visibility=View.GONE
           holder.itemView.con_gender.background = ContextCompat.getDrawable(context, R.drawable.background_unsellected_filter);
       }

        holder.itemView.con_gender.setOnClickListener(View.OnClickListener {
            val copyOfLastCheckedPosition = lastCheckedPosition
            lastCheckedPosition = position
            notifyItemChanged(copyOfLastCheckedPosition)
            notifyItemChanged(lastCheckedPosition)
        })
    }
}