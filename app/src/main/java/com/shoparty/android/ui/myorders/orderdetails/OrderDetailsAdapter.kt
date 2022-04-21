package com.shoparty.android.ui.myorders.orderdetails

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.HomeCategoriesLayoutItemBinding
import com.shoparty.android.databinding.ItemOrderListBinding
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.ui.main.product_list.ProductListActivity
import com.shoparty.android.utils.Constants

class OrderDetailsAdapter(
    private val list: List<OrderDetailsResponse.ProductResponse>,
    val context: OrderDetailsActivity
) : RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_list, parent, false) as View
        return ViewHolder(
            view = view,
            context = context
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let {
            holder.bind(it)
        }



    }

    class ViewHolder(
        val view: View,
        val context: Context) : RecyclerView.ViewHolder(view) {
        private val binding: ItemOrderListBinding = DataBindingUtil.bind(view)!!
        init
        { }
        fun bind(modal: OrderDetailsResponse.ProductResponse)
        {
            binding?.productName?.text = modal.product_name
            Glide.with(context).asBitmap().load(modal.image).into(binding?.ivProductImg!!)
        }
    }

}