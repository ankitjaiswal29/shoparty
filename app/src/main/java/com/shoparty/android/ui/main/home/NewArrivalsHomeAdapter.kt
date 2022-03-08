package com.shoparty.android.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.ui.main.topselling.TopSellingActivity
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.home_season_layout_item.view.*

import kotlinx.android.synthetic.main.new_arrival_item.view.*

class NewArrivalsHomeAdapter(private val itemList: List<HomeCategoriesModel>,val requireContext: Context): RecyclerView.Adapter<NewArrivalsHomeAdapter.NewArrivalsHomeViewHolder>() {

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
        holder.itemView.cl_new_arrival_item_root.setOnClickListener {
            val intent = Intent(requireContext, TopSellingActivity::class.java)
            requireContext. startActivity(intent)
        }
    }
}