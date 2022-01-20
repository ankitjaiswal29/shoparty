package com.example.shoparty_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.model.MyAccountModel


class MyAccountAdapter(private val mList: List<MyAccountModel>,private val recyclerViewClickListener: RecyclerViewClickListener) : RecyclerView.Adapter<MyAccountAdapter.ViewHolder>() {

    // create new views

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_account_item_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.ivIcon.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text
       /* holder.cv_Carditem.setOnClickListener {
            itemclick?.itemclick(ItemsViewModel.id)
        }
*/
        holder.itemView.setOnClickListener {
            recyclerViewClickListener.click(ItemsViewModel.id)
        }



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textview)
        val ivIcon:ImageView=itemView.findViewById(R.id.iv_icon)
       val cv_Carditem:CardView=itemView.findViewById(R.id.cv_Carditem)
    }
}