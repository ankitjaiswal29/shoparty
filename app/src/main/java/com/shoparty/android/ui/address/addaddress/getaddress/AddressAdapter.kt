package com.shoparty.android.ui.address.addaddress.getaddress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.returnpolicy.ReturnPolicyModel


class AddressAdapter(private val mList: List<ReturnPolicyModel>,var recyclerViewClickListener: RecyclerViewClickListener) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    // create new views

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.address_item_recyclar_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.tv_Shippinglabel.setText(ItemsViewModel.title)
        holder.tvEdit.setOnClickListener {

            recyclerViewClickListener.click(ItemsViewModel.title)
        }

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
        val tv_Shippinglabel: TextView = itemView.findViewById(R.id.tv_Shippinglabel)
        val tvEdit: TextView = itemView.findViewById(R.id.tvEdit)


    }
}