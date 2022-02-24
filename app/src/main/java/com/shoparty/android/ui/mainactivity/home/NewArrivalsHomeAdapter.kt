package com.shoparty.android.ui.mainactivity.home

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate

import kotlinx.android.synthetic.main.new_arrival_item.view.*

class NewArrivalsHomeAdapter(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<NewArrivalsHomeAdapter.NewArrivalsHomeViewHolder>() {

    inner class NewArrivalsHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewArrivalsHomeViewHolder {
        return NewArrivalsHomeViewHolder(parent.inflate(R.layout.new_arrival_item))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: NewArrivalsHomeViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            new_arrival_item_name_tv.text = items.name
           /* new_arrival_item_root.setOnClickListener {
                findNavController().navigate(R.id.newArrivalsItemFragment)
            }*/
        }
    }
}