package com.shoparty.android.ui.vouchers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R


class VouchersAdapter(private val mList: ArrayList<String>) : RecyclerView.Adapter<VouchersAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.voucher_item_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
       // holder.ivIcon.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.tv_MinimulPurchase.text = ItemsViewModel;



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tv_MinimulPurchase: TextView = itemView.findViewById(R.id.tvMinimulPurchase)

    }
}