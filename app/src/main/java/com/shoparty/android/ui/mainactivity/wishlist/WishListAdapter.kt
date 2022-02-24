package com.shoparty.android.ui.mainactivity.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import kotlinx.android.synthetic.main.deals_item_layout.view.*


class WishListAdapter(private val mList: ArrayList<String>) : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {

    // create new views
    var fav=false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wishlist_item_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
       // holder.ivIcon.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.tv_ItemName.text = ItemsViewModel;
        holder.itemView.iv_background.setOnClickListener {
            if (fav){
                holder.itemView.iv_unselect.visibility=View.GONE
                holder.itemView.iv_select.visibility=View.VISIBLE
                fav=false
            }else{
                holder.itemView.iv_select.visibility=View.GONE
                holder.itemView.iv_unselect.visibility=View.VISIBLE
                fav=true
            }
        }




    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tv_ItemName: TextView = itemView.findViewById(R.id.tv_ItemName)

    }
}