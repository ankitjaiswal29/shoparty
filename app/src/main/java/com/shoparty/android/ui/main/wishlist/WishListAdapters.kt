package com.shoparty.android.ui.main.wishlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener


class WishListAdapters(
    var context: Context,
    private val mList: List<WishListResponse.Data>,
    var recyclerViewClickListener: RecyclerViewClickListener) : RecyclerView.Adapter<WishListAdapters.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wishlist_item_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.tv_ItemName.text = ItemsViewModel.product_name
        holder.tvPrice.text = context.getString(R.string.dollor)+ItemsViewModel.sale_price
        Glide.with(context).asBitmap().load(ItemsViewModel.product_image).into(holder.iv_Productimg!!)

        if(ItemsViewModel.discount.isNullOrEmpty())
        {
            holder.tvOffer.visibility=View.GONE
        }
        else
        {
            holder.tvOffer.visibility=View.VISIBLE
            holder.tvOffer.text=ItemsViewModel.discount+context.getString(R.string.percent)+" "+context.getString(R.string.off)
        }



        holder.relativeLike.setOnClickListener {
            recyclerViewClickListener.click(ItemsViewModel.id)
        }
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val iv_Productimg: ImageView = itemView.findViewById(R.id.iv_Productimg)
        val tv_ItemName :TextView = itemView.findViewById(R.id.tv_ItemName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvOffer: TextView = itemView.findViewById(R.id.tv_Offer)
        val relativeLike: RelativeLayout = itemView.findViewById(R.id.relative_like)
    }
}