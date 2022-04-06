package com.shoparty.android.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.ui.main.deals.TopSellingHomeModel
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.deals_item_layout.view.*
import kotlinx.android.synthetic.main.top_selling_layout_item.view.*
import kotlinx.android.synthetic.main.top_selling_layout_item.view.iv_background
import kotlinx.android.synthetic.main.top_selling_layout_item.view.iv_select
import kotlinx.android.synthetic.main.top_selling_layout_item.view.iv_unselect

class TopSellingHomeAdapter(var context: Context,private val itemList: List<HomeResponse.Top20Selling>,
                            var recyclerViewFavouriteListener: RecyclerViewFavouriteListener) :
    RecyclerView.Adapter<TopSellingHomeAdapter.TopSellingHomeViewHolder>() {
    inner class TopSellingHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingHomeViewHolder {
        return TopSellingHomeViewHolder(parent.inflate(R.layout.top_selling_home_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: TopSellingHomeViewHolder, position: Int) {
        val ItemsViewModel = itemList[position]

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

        holder.itemView.apply {
            tv_princesdresses.text = ItemsViewModel.product_name
            tv_dressprise.text = context.getString(R.string.dollor)+ItemsViewModel.sale_price
            Glide.with(context).asBitmap().load(ItemsViewModel.image).into(imgProduct)
        }

        holder.itemView.top_selling_home_item_root.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra(Constants.IDPRODUCT,ItemsViewModel.product_id.toString())
            intent.putExtra(Constants.PRODUCATDETAILSID,ItemsViewModel.product_detail_id.toString())
            intent.putExtra(Constants.PRODUCATNAME,ItemsViewModel.product_name)
            intent.putExtra(Constants.PRODUCTSIZEID,ItemsViewModel.product_size_id.toString())
            intent.putExtra(Constants.PRODUCTCOLORID,ItemsViewModel.product_color_id)
            context.startActivity(intent)
        }

           holder.itemView.relative_like.setOnClickListener {
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

    }
