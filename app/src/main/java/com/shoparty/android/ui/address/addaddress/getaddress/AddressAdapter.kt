package com.shoparty.android.ui.address.addaddress.getaddress

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewAddressClickListener


class AddressAdapter(private val mList: List<GetAddressListResponse.Data>,
                     var recyclerViewaddressClickListener: RecyclerViewAddressClickListener) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.address_item_recyclar_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.tvNamedata.text = ItemsViewModel.first_name+" "+ItemsViewModel.last_name
        holder.tv_HousenoData.text = ItemsViewModel.building_no
        holder.tv_AddressData.text = ItemsViewModel.building_no+","+
                ItemsViewModel.street_no+","+ItemsViewModel.city_name+","+ItemsViewModel.country_name
        holder.tv_PhonenoData.text = ItemsViewModel.mobile

        holder.tvEdit.setOnClickListener {
            recyclerViewaddressClickListener.editclick(ItemsViewModel.address_id,ItemsViewModel)
        }

        holder.tvRemove.setOnClickListener {
            recyclerViewaddressClickListener.removeclick(ItemsViewModel.address_id,position)
        }

    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvEdit: TextView = itemView.findViewById(R.id.tvEdit)
        val tvNamedata: TextView = itemView.findViewById(R.id.tv_Namedata)
        val tv_HousenoData: TextView = itemView.findViewById(R.id.tv_HousenoData)
        val tv_AddressData: TextView = itemView.findViewById(R.id.tv_AddressData)
        val tv_PhonenoData: TextView = itemView.findViewById(R.id.tv_PhonenoData)
        val tvRemove: TextView = itemView.findViewById(R.id.tvRemove)
    }
}