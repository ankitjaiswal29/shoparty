package com.shoparty.android.ui.main.wishlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.utils.Utils
class WishListAdapters(
    var context: Context,
    private val mList: List<WishListResponse.Result>,
    var recyclerViewFavouriteListener: RecyclerViewFavouriteListener
) : RecyclerView.Adapter<WishListAdapters.ViewHolder>()
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
        holder.tvItemSubtitle.text = ItemsViewModel.product_descripion
        holder.tvPrice.text = context.getString(R.string.dollor)+ItemsViewModel.sale_price
        Glide.with(context).asBitmap().load(ItemsViewModel.image).into(holder.iv_Productimg!!)

        holder.txtAdd.setOnClickListener {
            Utils.showLongToast(context,context.getString(R.string.comingsoon))
        }

        holder.relativeLike.setOnClickListener {
            recyclerViewFavouriteListener.favourite(
                position, ItemsViewModel.product_id.toString(),
                "0",
                ItemsViewModel.product_detail_id.toString()
            )
        }
    }
    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val iv_Productimg: ImageView = itemView.findViewById(R.id.iv_Productimg)
        val tv_ItemName :TextView = itemView.findViewById(R.id.tv_ItemName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvOffer: TextView = itemView.findViewById(R.id.tv_Offer)
        val txtAdd: TextView = itemView.findViewById(R.id.txtAdd)
        val tvItemSubtitle: TextView = itemView.findViewById(R.id.tv_Item_subtitle)
        val relativeLike: RelativeLayout = itemView.findViewById(R.id.relative_like)
    }
}