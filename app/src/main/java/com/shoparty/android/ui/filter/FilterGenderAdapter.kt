package com.shoparty.android.ui.filter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.filter_color_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.*

import kotlinx.android.synthetic.main.ts_subcategories_item.view.*
import kotlinx.android.synthetic.main.ts_subcategories_item.view.ts_subcategories_item_name_tv

class FilterGenderAdapter(private val itemList: List<String>,var context: Context): RecyclerView.Adapter<FilterGenderAdapter.TopSellingSubcategoriesViewHolder>() {
    inner class TopSellingSubcategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    var check=false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingSubcategoriesViewHolder {
        return TopSellingSubcategoriesViewHolder(parent.inflate(R.layout.filter_recyclar_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: TopSellingSubcategoriesViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
          //  view_circle.backgroundTintList(Color.parseColor(items));
            tv_text.setText(items)
        }

        holder.itemView.tv_text.setOnClickListener(View.OnClickListener {
            if(check){
                holder.itemView.tv_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check_black, 0);
                check=false
                holder.itemView.cl_rootitem.setBackground(ContextCompat.getDrawable(context, R.drawable.background_unsellected_filter));

                //Toast.makeText(context,item[position].toString(),Toast.LENGTH_LONG).show()
            }else{
                //Toast.makeText(context,item[position].toString(),Toast.LENGTH_LONG).show()
                check=true
                holder.itemView.cl_rootitem.setBackground(ContextCompat.getDrawable(context, R.drawable.background_sellected_filter));

                holder.itemView.tv_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_24, 0);

            }
        })
    }
}