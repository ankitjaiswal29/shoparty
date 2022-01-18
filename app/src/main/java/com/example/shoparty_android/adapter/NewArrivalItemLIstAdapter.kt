package com.example.shoparty_android.adapter

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.helper.inflate
import com.example.shoparty_android.model.TopSellingHomeModel
import kotlinx.android.synthetic.main.top_selling_item2.view.*

class NewArrivalItemLIstAdapter(private val itemList: List<TopSellingHomeModel>): RecyclerView.Adapter<NewArrivalItemLIstAdapter.NewArrivalItemLIstViewHolder>() {

    inner class NewArrivalItemLIstViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewArrivalItemLIstViewHolder {
        return NewArrivalItemLIstViewHolder(parent.inflate(R.layout.top_selling_item2))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: NewArrivalItemLIstAdapter.NewArrivalItemLIstViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            tsi_item_name.text = items.name
            tsi_item_price_tv.text = items.price

            top_selling_item_root.setOnClickListener {
                findNavController().navigate(R.id.newArrivalItemDetailsFragment)
            }
        }


    }
}