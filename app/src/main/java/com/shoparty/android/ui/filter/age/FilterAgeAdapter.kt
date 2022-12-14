package com.shoparty.android.ui.filter.age

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.ui.filter.QuantityListner
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.*
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.cl_rootitem
import kotlinx.android.synthetic.main.filter_recyclar_item_layout.view.tv_text

class FilterAgeAdapter(private val ageList: List<AgeResponse.Result>,
                       var context: Context,var quantityListner: QuantityListner):
    RecyclerView.Adapter<FilterAgeAdapter.TopSellingSubcategoriesViewHolder>() {
    inner class TopSellingSubcategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private var lastCheckedPosition = -1
    var checkedItems:ArrayList<AgeRequest> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingSubcategoriesViewHolder {
        return TopSellingSubcategoriesViewHolder(parent.inflate(R.layout.filter_recyclar_item_layout))
    }
    override fun getItemCount(): Int {
        return ageList.size
    }
    override fun onBindViewHolder(holder: TopSellingSubcategoriesViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val items = ageList[position]
        holder.itemView.tv_text.text = items.age_from+"-"+items.age_to
        holder.itemView.cl_rootitem.setOnClickListener {
            if (holder.itemView.imgChecked.visibility == View.VISIBLE)
            {
                holder.itemView.imgChecked.visibility = View.GONE
                holder.itemView.imgUnchecked.visibility = View.VISIBLE
                holder.itemView.cl_rootitem.background = ContextCompat.getDrawable(context, R.drawable.background_unsellected_filter)
                //ageList[position].let { checkedItems.remove(it) }
              //  ageList[position].isChecked = false
                var age = AgeRequest(ageList[position].age_from,ageList[position].age_to)
                checkedItems.remove(age)


            }
            else
            {
                holder.itemView.imgChecked.visibility = View.VISIBLE
                holder.itemView.imgUnchecked.visibility = View.GONE
                holder.itemView.cl_rootitem.background = ContextCompat.getDrawable(context, R.drawable.background_sellected_filter);
                //ageList[position].let { checkedItems.add(it) }
                var age = AgeRequest(ageList[position].age_from,ageList[position].age_to)
                checkedItems.add(age)
             //   ageList[position].isChecked = true
            }
            quantityListner.onAgeQuantitychanged(checkedItems)
        }

       /* if(position == lastCheckedPosition)
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
*/

    }
}