package com.shoparty.android.ui.main.deals

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.ui.main.topselling.TopSellingActivity
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.deals_item_layout.view.*

class DealsAdapter(private val itemList: List<TopSellingHomeModel>,val requireContext: Context): RecyclerView.Adapter<DealsAdapter.NewArrivalItemLIstViewHolder>() {

    var fav=false;
    inner class NewArrivalItemLIstViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewArrivalItemLIstViewHolder {
        return NewArrivalItemLIstViewHolder(parent.inflate(R.layout.deals_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: NewArrivalItemLIstViewHolder, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {

            tv_productname.text=items.name
           /* tsi_item_name.text = items.name
            tsi_item_price_tv.text = items.price

            top_selling_item_root.setOnClickListener {
                findNavController().navigate(R.id.newArrivalItemDetailsFragment)
            }*/
        }
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
        holder.itemView.top_selling_item_root.setOnClickListener {
            val intent = Intent(requireContext, ProductDetailsActivity::class.java)
           requireContext.startActivity(intent)
        }


    }
}