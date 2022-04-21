package com.shoparty.android.ui.myorders.myorderlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.util.Util

import com.shoparty.android.R
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsActivity
import com.shoparty.android.utils.Utils


class MyOrderAdapters(var context: Context, private val mList: List<MyOrderResponse.OrderHistory>) : RecyclerView.Adapter<MyOrderAdapters.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.myorder_item_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = mList[position]
        holder.tv_ItemName.text = items.order_title+" "+context.getString(R.string.on)+" "+items.order_date
        holder.tv_productdetailsData.text = items.order_name
        Glide.with(context).asBitmap().load(items.order_image).into(holder.iv_Productimg)

        holder.cl_myorder_root_item.setOnClickListener {
            val intent = Intent(context, OrderDetailsActivity::class.java)
            intent.putExtra("order_id",items.order_id.toString())
            context.startActivity(intent)
         //   Utils.showLongToast(context,context.getString(R.string.comingsoon))
        }
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cl_myorder_root_item: ConstraintLayout = itemView.findViewById(R.id.cl_myorder_root_item)
        val tv_ItemName: TextView = itemView.findViewById(R.id.tv_ItemName)
        val tv_productdetailsData: TextView = itemView.findViewById(R.id.tv_productdetailsData)
        val iv_Productimg: ImageView = itemView.findViewById(R.id.iv_Productimg)
    }
}