package com.example.shoparty_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.model.ReturnPolicyModel


class ReturnPolicyAdapter(private val mList: List<ReturnPolicyModel>) : RecyclerView.Adapter<ReturnPolicyAdapter.ViewHolder>() {

    // create new views

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.return_policy_item_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.ivNO.setText(ItemsViewModel.serialno)
        holder.tvTitle.setText(ItemsViewModel.title)
        // sets the text to the textview from our itemHolder class

       /* holder.cv_Carditem.setOnClickListener {
            itemclick?.itemclick(ItemsViewModel.id)
        }
*/



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivNO: TextView = itemView.findViewById(R.id.ivNO)
        val tvTitle:TextView=itemView.findViewById(R.id.tvTitle)

    }
}