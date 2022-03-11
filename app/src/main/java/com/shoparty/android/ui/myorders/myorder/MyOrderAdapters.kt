package com.shoparty.android.ui.myorders.myorder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener


class MyOrderAdapters(private val mList: List<MyOrderResponse.Data>,
                      var recyclerViewClickListener: RecyclerViewClickListener) : RecyclerView.Adapter<MyOrderAdapters.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.myorder_item_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.tv_ItemName.text = ItemsViewModel.order_title
        holder.tv_productdetailsData.text = ItemsViewModel.order_name

        holder.cl_myorder_root_item.setOnClickListener {
            recyclerViewClickListener.click(ItemsViewModel.order_id.toString())
        }


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cl_myorder_root_item: ConstraintLayout = itemView.findViewById(R.id.cl_myorder_root_item)
        val tvEdit: ImageView = itemView.findViewById(R.id.iv_Productimg)
        val tv_ItemName: TextView = itemView.findViewById(R.id.tv_ItemName)
        val tv_productdetailsData: TextView = itemView.findViewById(R.id.tv_productdetailsData)
    }
}