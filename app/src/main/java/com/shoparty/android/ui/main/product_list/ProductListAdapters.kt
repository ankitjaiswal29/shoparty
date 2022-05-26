package com.shoparty.android.ui.main.product_list

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
import com.shoparty.android.common_modal.Product
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import kotlinx.android.synthetic.main.deals_item_layout.view.*
class ProductListAdapters(
    val context: Context,
    private val mList: ArrayList<Product>,
    var recyclerViewFavouriteListener: RecyclerViewFavouriteListener) :
    RecyclerView.Adapter<ProductListAdapters.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.deals_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            holder.cl_productlist_root_item.layoutDirection = View.LAYOUT_DIRECTION_RTL
            holder.tv_productname.ellipsize = TextUtils.TruncateAt.START
            holder.tv_product_subtitle.ellipsize = TextUtils.TruncateAt.START
        }else {
            holder.cl_productlist_root_item.layoutDirection = View.LAYOUT_DIRECTION_LTR
            holder.tv_productname.ellipsize = TextUtils.TruncateAt.END
            holder.tv_product_subtitle.ellipsize = TextUtils.TruncateAt.END
        }

        holder.tv_productname.text = ItemsViewModel.product_name
        holder.tv_product_subtitle.text = ItemsViewModel.product_descripion
        holder.tv_price.text = context.getString(R.string.dollor)+ItemsViewModel.sale_price
        Glide.with(context).asBitmap().load(ItemsViewModel.image).into(holder.iv_dealsimg)

        holder.cl_productlist_root_item.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra(Constants.IDPRODUCT,ItemsViewModel.product_id.toString())
            intent.putExtra(Constants.PRODUCATDETAILSID,ItemsViewModel.product_detail_id.toString())
            intent.putExtra(Constants.PRODUCATNAME,ItemsViewModel.product_name)
            intent.putExtra(Constants.PRODUCTSIZEID,ItemsViewModel.product_size_id.toString())
            intent.putExtra(Constants.PRODUCTCOLORID,ItemsViewModel.product_color_id.toString())
            context.startActivity(intent)
        }

        if(ItemsViewModel.fav_status==1)  //selected
        {
            holder.itemView.iv_select.visibility=View.VISIBLE
            holder.itemView.iv_unselect.visibility=View.GONE
        }
        else
        {
            holder.itemView.iv_unselect.visibility=View.VISIBLE
            holder.itemView.iv_select.visibility=View.GONE
        }


        holder.itemView.iv_background.setOnClickListener {
            if (ItemsViewModel.fav_status==1)
            {
                holder.itemView.iv_unselect.visibility=View.VISIBLE
                holder.itemView.iv_select.visibility=View.GONE
                recyclerViewFavouriteListener.favourite(position,ItemsViewModel.product_id.toString(),"0",ItemsViewModel.product_detail_id.toString(),ItemsViewModel.product_size_id.toString(),ItemsViewModel.product_color_id.toString())
            }
            else{
                holder.itemView.iv_select.visibility=View.VISIBLE
                holder.itemView.iv_unselect.visibility=View.GONE
                recyclerViewFavouriteListener.favourite(position,ItemsViewModel.product_id.toString(),"1",ItemsViewModel.product_detail_id.toString(),ItemsViewModel.product_size_id.toString(),ItemsViewModel.product_color_id.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
    {
        val cl_productlist_root_item: ConstraintLayout = itemView.findViewById(R.id.cl_productlist_root_item)
        val tv_productname: TextView = itemView.findViewById(R.id.tv_productname)
        val iv_dealsimg: ImageView = itemView.findViewById(R.id.iv_dealsimg)
        val tv_price:TextView = itemView.findViewById(R.id.tv_price)
        val tv_offer:TextView = itemView.findViewById(R.id.tv_offer)
        val tv_product_subtitle:TextView = itemView.findViewById(R.id.tv_product_subtitle)
    }

    fun updateItems(newList: ArrayList<Product>)
    {
        mList.clear()
        mList.addAll(newList)
        notifyDataSetChanged()
    }
}