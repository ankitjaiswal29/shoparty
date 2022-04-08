package com.shoparty.android.ui.filter.size

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener
import kotlinx.android.synthetic.main.filter_color_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_gender_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.tv_text


class SizeAdapters(val context: Context,private val sizeList: List<String>,
                   var recyclerViewClickListener: RecyclerViewClickListener) : RecyclerView.Adapter<SizeAdapters.ViewHolder>() {

    private var lastCheckedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_recyclar_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val items = sizeList[position]
        holder.tv_text.text = items

        if(position == lastCheckedPosition)
        {
            holder.itemView.tv_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_24, 0);
            holder.itemView.cl_rootitem.background = ContextCompat.getDrawable(context, R.drawable.background_sellected_filter);
        }
        else
        {
            holder.itemView.tv_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check_black, 0);
            holder.itemView.cl_rootitem.background = ContextCompat.getDrawable(context, R.drawable.background_unsellected_filter);
        }

        holder.itemView.tv_text.setOnClickListener(View.OnClickListener {
            val copyOfLastCheckedPosition = lastCheckedPosition
            lastCheckedPosition = position
            notifyItemChanged(copyOfLastCheckedPosition)
            notifyItemChanged(lastCheckedPosition)
            recyclerViewClickListener.click(items)
        })

    }
    override fun getItemCount(): Int {
        return sizeList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
           val tv_text: TextView = itemView.findViewById(R.id.tv_text)


    }
}