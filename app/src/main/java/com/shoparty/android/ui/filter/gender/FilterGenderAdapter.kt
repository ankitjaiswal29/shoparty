package com.shoparty.android.ui.filter.gender

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.Transliterator
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.ui.filter.QuantityListner
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.filter_recyclar_gender_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.tv_text

class FilterGenderAdapter(private val itemList: List<String>,
                          var context: Context,var quantityListner: QuantityListner):
    RecyclerView.Adapter<FilterGenderAdapter.FilterGenderViewHolder>()
{
    var checkedItems = ArrayList<String>()
    inner class FilterGenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterGenderViewHolder {
        return FilterGenderViewHolder(parent.inflate(R.layout.filter_recyclar_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: FilterGenderViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            tv_text.text = items
        }
        holder.itemView.cl_rootitem.setOnClickListener {
            if(holder.itemView.imgChecked.visibility == View.VISIBLE)
            {
                holder.itemView.imgChecked.visibility = View.GONE
                holder.itemView.imgUnchecked.visibility = View.VISIBLE
                holder.itemView.cl_rootitem.background = ContextCompat.getDrawable(context, R.drawable.background_unsellected_filter)
                itemList[position].let { checkedItems.remove(it) }
            }
            else
            {
                holder.itemView.imgChecked.visibility = View.VISIBLE
                holder.itemView.imgUnchecked.visibility = View.GONE
                holder.itemView.cl_rootitem.background = ContextCompat.getDrawable(context, R.drawable.background_sellected_filter);
                itemList[position].let { checkedItems.add(it) }
            }
            quantityListner.onGenderQuantitychanged(checkedItems)
        }






    }
}