package com.shoparty.android.ui.main.wishlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.TextUtils
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
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.interfaces.WishListAddBagClickListener
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager

class WishListAdapters(
    var context: Context,
    private val mList: List<WishListResponse.Result>,
    var recyclerViewFavouriteListener: RecyclerViewFavouriteListener,
    var wishListAddBagClickListener: WishListAddBagClickListener
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
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            holder.clWishListRootItem.layoutDirection = View.LAYOUT_DIRECTION_RTL
            holder.tv_ItemName.ellipsize = TextUtils.TruncateAt.START
            holder.tvItemSubtitle.ellipsize = TextUtils.TruncateAt.START
            holder.tvPrice.ellipsize = TextUtils.TruncateAt.START
        }else {
            holder.clWishListRootItem.layoutDirection = View.LAYOUT_DIRECTION_LTR
            holder.tv_ItemName.ellipsize = TextUtils.TruncateAt.END
            holder.tvItemSubtitle.ellipsize = TextUtils.TruncateAt.END
            holder.tvPrice.ellipsize = TextUtils.TruncateAt.END
        }

        holder.tv_ItemName.text = ItemsViewModel.product_name
        holder.tvItemSubtitle.text = ItemsViewModel.product_descripion
        holder.tvPrice.text = context.getString(R.string.dollor)+ItemsViewModel.sale_price

        if(ItemsViewModel.cart_quantity==0)
        {
            holder.txtAdd.visibility=View.VISIBLE
            holder.iv_minus.visibility=View.GONE
            holder.iv_plus.visibility=View.GONE
            holder.tv_count.visibility=View.GONE
        } else {
            holder.txtAdd.visibility=View.GONE
            holder.iv_minus.visibility=View.VISIBLE
            holder.iv_plus.visibility=View.VISIBLE
            holder.tv_count.visibility=View.VISIBLE
            holder.tv_count.text=ItemsViewModel.cart_quantity.toString()
        }

        if(!ItemsViewModel.discount.isNullOrEmpty())
        {
            holder.tvOffer.visibility=View.VISIBLE
            holder.tvOffer.text = ItemsViewModel.discount+context.getString(R.string._10_off)
        }
        else
        {
            holder.tvOffer.visibility=View.GONE
        }
        Glide.with(context).asBitmap().load(ItemsViewModel.image).into(holder.iv_Productimg!!)

        holder.txtAdd.setOnClickListener {
            wishListAddBagClickListener.twoParameterPassClick(position,Constants.CARTACTIONADDBUTTONTYPE)
        }

        holder.iv_plus.setOnClickListener {
            wishListAddBagClickListener.twoParameterPassClick(position,Constants.CARTACTIONPLUSTYPE)
        }

        holder.iv_minus.setOnClickListener {
            wishListAddBagClickListener.twoParameterPassClick(position,Constants.CARTACTIONMINUSTYPE)
        }

        holder.relativeLike.setOnClickListener {
            recyclerViewFavouriteListener.favourite(
                position, ItemsViewModel.product_id.toString(),
                "0",
                ItemsViewModel.product_detail_id.toString(),
                ItemsViewModel.product_size_id.toString(),
                ItemsViewModel.product_color_id.toString())
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra(Constants.IDPRODUCT,ItemsViewModel.product_id.toString())
            intent.putExtra(Constants.PRODUCATDETAILSID,ItemsViewModel.product_detail_id.toString())
            intent.putExtra(Constants.PRODUCATNAME,ItemsViewModel.product_name)
            intent.putExtra(Constants.PRODUCTSIZEID,ItemsViewModel.product_size_id.toString())
            intent.putExtra(Constants.PRODUCTCOLORID,ItemsViewModel.product_color_id.toString())
            context.startActivity(intent)
        }

    }

    public fun updateData(position: Int, quantity: Int)
    {
        mList[position].cart_quantity=quantity
        notifyItemChanged(position)
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
        val iv_minus: ImageView = itemView.findViewById(R.id.iv_minus)
        val iv_plus: ImageView = itemView.findViewById(R.id.iv_plus)
        val tv_count: TextView = itemView.findViewById(R.id.tv_count)
        val txtAdd: TextView = itemView.findViewById(R.id.txtAdd)
        val tvItemSubtitle: TextView = itemView.findViewById(R.id.tv_Item_subtitle)
        val clWishListRootItem: ConstraintLayout = itemView.findViewById(R.id.cl_wishlist_root_item)
        val relativeLike: RelativeLayout = itemView.findViewById(R.id.relative_like)
    }
}