package com.shoparty.android.ui.shoppingbag


import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.bag_pickup_item_layout.view.*

class ShopingBagPickupAdapter(private val itemList: ArrayList<StoreListResponse.Result>,
                              var recyclerviewclicklistener:RecyclerViewClickListener):
    RecyclerView.Adapter<ShopingBagPickupAdapter.ShopingBagPickupViewHolder>(){
    private var checkedRadioButton: CompoundButton? = null
    inner class ShopingBagPickupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopingBagPickupViewHolder {
        return ShopingBagPickupViewHolder(parent.inflate(R.layout.bag_pickup_item_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: ShopingBagPickupViewHolder,position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            tv_title.text = items.store_name
            tv_subTitle.text = items.address
            Glide.with(context).asBitmap().load(items.image).into(bag_pickup_item_img!!)
        }
        holder.itemView.radiobutton.setOnCheckedChangeListener{compoundButton, isChecked ->
            checkedRadioButton?.apply { setChecked(!isChecked) }
            checkedRadioButton = compoundButton.apply {
                setChecked(isChecked)
                recyclerviewclicklistener.click(position.toString())

            }
        }
        if(holder.itemView.radiobutton.isChecked)
        {
            checkedRadioButton =  holder.itemView.radiobutton
        }
    }




}



