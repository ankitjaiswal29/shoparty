package com.shoparty.android.ui.filter.color

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.filter.QuantityListner
import kotlinx.android.synthetic.main.filter_color_item_layout.view.*

class FilterColorsAdapters(private val colorList: List<ColorsResponse.Colors>,
                           var recyclerViewClickListener: RecyclerViewClickListener,
                           var quantityListner: QuantityListner
                           ) :
                           RecyclerView.Adapter<FilterColorsAdapters.ViewHolder>() {

    var checkedItems = ArrayList<ColorsResponse.Colors>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_color_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val items = colorList[position]
        holder.view_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor(items.color_code));

        holder.itemView.view_circle.setOnClickListener {
            if (holder.itemView.iv_check.visibility == View.VISIBLE) {
                holder.itemView.iv_check.visibility = View.INVISIBLE
                colorList[position].let { checkedItems.remove(it) }
                colorList[position].isChecked = false
            } else {
                holder.itemView.iv_check.visibility = View.VISIBLE
                colorList[position].let { checkedItems.add(it) }
                colorList[position].isChecked = true
            }
           // recyclerViewClickListener.click(items.color_name)
            quantityListner.onQuantitychanged(checkedItems)
        }
    }

    override fun getItemCount(): Int {
        return colorList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
           val view_circle: View = itemView.findViewById(R.id.view_circle)
        val iv_check: ImageView = itemView.findViewById(R.id.iv_check)

        fun setOnClickListener(onClickListener: View.OnClickListener?) {
            itemView.setOnClickListener(onClickListener)
        }

    }
}