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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.address_item_recyclar_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        holder.tv_Shippinglabel.setText(ItemsViewModel.title)
        holder.tvEdit.setOnClickListener {
            recyclerViewClickListener.click(ItemsViewModel.title)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tv_Shippinglabel: TextView = itemView.findViewById(R.id.tv_Shippinglabel)
        val tvEdit: TextView = itemView.findViewById(R.id.tvEdit)


    }
}