package com.shoparty.android.ui.productdetails

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.utils.inflate

import kotlinx.android.synthetic.main.top_selling_layout_item.view.*

class ProductdetailsAdapter(var context:Context,
    private val itemList: List<ProducatDetailsResponse.ProductDetailList>

): RecyclerView.Adapter<ProductdetailsAdapter.TopSellingHomeViewHolder>() {

    inner class TopSellingHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingHomeViewHolder {
        return TopSellingHomeViewHolder(parent.inflate(R.layout.top_selling_layout_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TopSellingHomeViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            tv_princesdresses.text = items.product_name
            tv_dressprise.text = context.getString(R.string.dollor)+items.sale_price
            Glide.with(context).asBitmap().load(items.image).into(imgProduct!!)

        }

    }
}