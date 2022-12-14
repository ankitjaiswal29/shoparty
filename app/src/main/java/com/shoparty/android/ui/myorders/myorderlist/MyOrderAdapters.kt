package com.shoparty.android.ui.myorders.myorderlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsActivity
import com.shoparty.android.utils.PrefManager


class MyOrderAdapters(var context: Context, private val mList: List<MyOrderResponse.OrderHistory>) : RecyclerView.Adapter<MyOrderAdapters.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.myorder_item_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = mList[position]
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            holder.cl_myorder_root_item.layoutDirection = View.LAYOUT_DIRECTION_RTL
            holder.tv_productdetailsData.ellipsize = TextUtils.TruncateAt.START
            holder.imgNextarrow.rotation = 180F
        }else {
            holder.cl_myorder_root_item.layoutDirection = View.LAYOUT_DIRECTION_LTR
            holder.tv_productdetailsData.ellipsize = TextUtils.TruncateAt.END
            holder.imgNextarrow.rotation = 0F
        }
        holder.tv_ItemName.text = items.order_title+" "+context.getString(R.string.on)+" "+items.order_date
        holder.tv_productdetailsData.text = items.order_name
        Glide.with(context).asBitmap().load(items.order_image).into(holder.iv_Productimg)

        holder.cl_myorder_root_item.setOnClickListener {
            val intent = Intent(context, OrderDetailsActivity::class.java)
            intent.putExtra("order_id",items.order_id.toString())
            context.startActivity(intent)
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
        val imgNextarrow: ImageView = itemView.findViewById(R.id.imgNextarrow)
    }
}