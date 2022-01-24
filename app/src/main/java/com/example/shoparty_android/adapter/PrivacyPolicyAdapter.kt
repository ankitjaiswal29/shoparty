package com.example.shoparty_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.model.MyAccountModel
import com.example.shoparty_android.model.PrivacyPolicyModel


class PrivacyPolicyAdapter(private val mList: List<PrivacyPolicyModel>) : RecyclerView.Adapter<PrivacyPolicyAdapter.ViewHolder>() {

    // create new views

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.privacy_policy_list_item_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
      //  holder.ivIcon.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.tv_title.text = ItemsViewModel.title
        holder.tv_subTitle.text = ItemsViewModel.subtitle
        holder.tv_title.setOnClickListener {
            if (holder.tv_subTitle.isVisible){
                holder.tv_subTitle.visibility=View.GONE
               // holder.tv_title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_plus_icon__2_, 0, 0, 0);
                holder.tv_title.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_plus_icon__2_, 0);

            }else{
                holder.tv_subTitle.visibility=View.VISIBLE
                holder.tv_title.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_minus_icon, 0);
            }

               }
       /* holder.cv_Carditem.setOnClickListener {
            itemclick?.itemclick(ItemsViewModel.id)
        }
*/
       /* holder.itemView.setOnClickListener {
            recyclerViewClickListener.click(ItemsViewModel.id)
        }
*/


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tv_title: TextView = itemView.findViewById(R.id.tv_title)
        val tv_subTitle:TextView=itemView.findViewById(R.id.tv_subTitle)

    }
}