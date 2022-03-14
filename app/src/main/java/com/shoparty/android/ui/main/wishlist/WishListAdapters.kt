package com.shoparty.android.ui.main.wishlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener


class WishListAdapters(private val mList: List<WishListResponse.Data>,
                       var recyclerViewClickListener: RecyclerViewClickListener,var context: Context) : RecyclerView.Adapter<WishListAdapters.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wishlist_item_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.tv_ItemName.text = ItemsViewModel.product_name
        holder.tvPrice.text = ItemsViewModel.sale_price
        Glide.with(context).asBitmap().load(ItemsViewModel.product_image).into(holder.iv_Productimg!!)


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cl_wishlist_root_item: ConstraintLayout = itemView.findViewById(R.id.cl_wishlist_root_item)
        val iv_Productimg: ImageView = itemView.findViewById(R.id.iv_Productimg)
      //  val tv_Item_subtitle: TextView = itemView.findViewById(R.id.tv_Item_subtitle)
       val tv_ItemName :TextView = itemView.findViewById(R.id.tv_ItemName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    }
}