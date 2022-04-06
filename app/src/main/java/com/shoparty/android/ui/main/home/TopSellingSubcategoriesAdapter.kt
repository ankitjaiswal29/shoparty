package com.shoparty.android.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.ui.main.product_list.ProductListActivity
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.top_selling_layout_item.view.*

import kotlinx.android.synthetic.main.ts_subcategories_item.view.*

class TopSellingSubcategoriesAdapter(var context:Context,private val itemList: List<HomeResponse.TopSubCategory>): RecyclerView.Adapter<TopSellingSubcategoriesAdapter.TopSellingSubcategoriesViewHolder>() {
    inner class TopSellingSubcategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingSubcategoriesViewHolder {
        return TopSellingSubcategoriesViewHolder(parent.inflate(R.layout.ts_subcategories_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: TopSellingSubcategoriesViewHolder, position: Int) {
        val items = itemList[position]

        holder.itemView.apply {
            ts_subcategories_item_name_tv.text = items.category_name
            Glide.with(context).asBitmap().load(items.category_image).into(ts_subcategories_item_img)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductListActivity::class.java)
            intent.putExtra(Constants.TOPSELLINGSUBCATEGORY,"9")
            intent.putExtra(Constants.PRODUCTID,items.category_id)  // categoryid
            intent.putExtra(Constants.CATEGORYNAME,items.category_name)
            context.startActivity(intent)
        }
    }
}