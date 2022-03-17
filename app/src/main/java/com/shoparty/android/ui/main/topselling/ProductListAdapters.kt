package com.shoparty.android.ui.main.topselling

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
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import kotlinx.android.synthetic.main.deals_item_layout.view.*


class ProductListAdapters(val  context: Context,private val mList: List<ProductListResponse.ProductList>,
                          var recyclerViewClickListener: RecyclerViewClickListener,var recyclerViewFavouriteListener: RecyclerViewFavouriteListener) : RecyclerView.Adapter<ProductListAdapters.ViewHolder>() {

var fav_select=false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.deals_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.tv_productname.text = ItemsViewModel.product_name
        holder.tv_price.text = ItemsViewModel.cost_price
        holder.tv_offer.text = ItemsViewModel.sale_price
        //holder.tv_product_subtitle.text = ItemsViewModel.
        Glide.with(context).asBitmap().load(ItemsViewModel.product_image).into(holder.iv_dealsimg!!)
        holder.cl_productlist_root_item.setOnClickListener {
            recyclerViewClickListener.click(ItemsViewModel.product_id.toString())
        }


        holder.itemView.iv_background.setOnClickListener {
            if (fav_select){
                holder.itemView.iv_unselect.visibility=View.VISIBLE
                holder.itemView.iv_select.visibility=View.GONE
                fav_select=false
                recyclerViewFavouriteListener.favourite(ItemsViewModel.product_id,"0")
            }
            else{
                holder.itemView.iv_select.visibility=View.VISIBLE
                holder.itemView.iv_unselect.visibility=View.GONE
                fav_select=true
                recyclerViewFavouriteListener.favourite(ItemsViewModel.product_id,"1")
            }
        }

       /* holder.itemView.iv_background.setOnClickListener {
            if (ItemsViewModel.fav_status.equals("1")){
                holder.itemView.iv_unselect.visibility=View.GONE
                holder.itemView.iv_select.visibility=View.VISIBLE

            }else{
                holder.itemView.iv_select.visibility=View.GONE
                holder.itemView.iv_unselect.visibility=View.VISIBLE

            }
        }*/
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cl_productlist_root_item: ConstraintLayout = itemView.findViewById(R.id.cl_productlist_root_item)
        val tv_productname: TextView = itemView.findViewById(R.id.tv_productname)
        val iv_dealsimg: ImageView = itemView.findViewById(R.id.iv_dealsimg)
       val  iv_background: ConstraintLayout = itemView.findViewById(R.id.iv_background)
        val  iv_unselect: ImageView = itemView.findViewById(R.id.iv_unselect)
        val  iv_select: ImageView = itemView.findViewById(R.id.iv_select)
        val tv_product_subtitle: TextView = itemView.findViewById(R.id.tv_product_subtitle)
       val tv_price:TextView = itemView.findViewById(R.id.tv_price)
        val tv_offer:TextView = itemView.findViewById(R.id.tv_offer)
    }
}