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
import com.shoparty.android.ui.filter.QuantityListner
import com.shoparty.android.ui.filter.age.FilterAgeAdapter
import com.shoparty.android.utils.inflate

import kotlinx.android.synthetic.main.filter_color_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_gender_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.cl_rootitem
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.tv_text


class SizeAdapters(val context: Context,private val sizeList: List<String>,
                   var quantityListner: QuantityListner) :
    RecyclerView.Adapter<SizeAdapters.TopSellingSubcategoriesViewHolder>()
{
    inner class TopSellingSubcategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    var checkedItems = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            SizeAdapters.TopSellingSubcategoriesViewHolder
    {
        return TopSellingSubcategoriesViewHolder(parent.inflate(R.layout.filter_recyclar_item_layout))
    }


    override fun onBindViewHolder(holder: SizeAdapters.TopSellingSubcategoriesViewHolder,
                                  @SuppressLint("RecyclerView") position: Int) {
        val items = sizeList[position]
        holder.itemView.tv_text.text = items

        holder.itemView.cl_rootitem.setOnClickListener {
            if(holder.itemView.imgChecked.visibility == View.VISIBLE)
            {
                holder.itemView.imgChecked.visibility = View.GONE
                holder.itemView.imgUnchecked.visibility = View.VISIBLE
                holder.itemView.cl_rootitem.background = ContextCompat.getDrawable(context, R.drawable.background_unsellected_filter)
                sizeList[position].let { checkedItems.remove(it) }
            }
            else
            {
                holder.itemView.imgChecked.visibility = View.VISIBLE
                holder.itemView.imgUnchecked.visibility = View.GONE
                holder.itemView.cl_rootitem.background = ContextCompat.getDrawable(context, R.drawable.background_sellected_filter);
                sizeList[position].let { checkedItems.add(it) }
            }
            quantityListner.onSizeQuantitychanged(checkedItems)
        }

    }
    override fun getItemCount(): Int {
        return sizeList.size
    }
}