package com.shoparty.android.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.home_offers_item_layout.view.*
import kotlinx.android.synthetic.main.top_selling_layout_item.view.*
import kotlinx.android.synthetic.main.ts_subcategories_item.view.*


class HomeOffersAdapter(var context: Context, private val itemList: List<HomeResponse.OffersAndDiscountedItem>): RecyclerView.Adapter<HomeOffersAdapter.HomeOffersViewHolder>()  {

    inner class HomeOffersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {
        return HomeOffersViewHolder(parent.inflate(R.layout.home_offers_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            home_offers_item_price_tv.text = context.getString(R.string.up_to_10_off)+" "+items.offer_discount.toString()+context.getString(R.string._54_off)
            Glide.with(context).asBitmap().load(items.image).into(home_offers_item_img)
        }

        holder.itemView.setOnClickListener {
         //   Utils.showLongToast(context,context.getString(R.string.comingsoon))
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra(Constants.IDPRODUCT,items.product_id.toString())
                intent.putExtra(Constants.PRODUCATNAME,items.product_name)
                intent.putExtra(Constants.PRODUCATDETAILSID,items.product_detail_id.toString())
                intent.putExtra(Constants.PRODUCTSIZEID,items.product_size_id.toString())
                intent.putExtra(Constants.PRODUCTCOLORID,items.product_color_id)
                context.startActivity(intent)
        }
    }
}