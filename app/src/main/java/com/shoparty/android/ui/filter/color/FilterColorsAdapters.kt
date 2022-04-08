package com.shoparty.android.ui.filter.color

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener
import kotlinx.android.synthetic.main.filter_color_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_gender_item_layout.view.*


class FilterColorsAdapters(private val colorList: List<ColorsResponse.Colors>,
                           var recyclerViewClickListener: RecyclerViewClickListener) :
                           RecyclerView.Adapter<FilterColorsAdapters.ViewHolder>() {
    private var lastCheckedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_color_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val items = colorList[position]
        holder.view_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor(items.color_code));

        if(position == lastCheckedPosition)
        {
            holder.itemView.iv_check.visibility=View.VISIBLE
        }
        else
        {
            holder.itemView.iv_check.visibility=View.GONE
        }

        holder.itemView.view_circle.setOnClickListener(View.OnClickListener {
            val copyOfLastCheckedPosition = lastCheckedPosition
            lastCheckedPosition = position
            notifyItemChanged(copyOfLastCheckedPosition)
            notifyItemChanged(lastCheckedPosition)
            recyclerViewClickListener.click(items.color_name)
        })
    }
    override fun getItemCount(): Int {
        return colorList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
           val view_circle: View = itemView.findViewById(R.id.view_circle)
        val iv_check: ImageView = itemView.findViewById(R.id.iv_check)

    }
}