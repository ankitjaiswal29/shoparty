package com.shoparty.android.ui.myorders.cancelorder.cancelorder

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.bag_pickup_item_layout.view.*

class CancelReasonAdapter(private val itemList: ArrayList<CancelReasonResponse.Result>,
                          var recyclerviewclicklistener:RecyclerViewClickListener):
    RecyclerView.Adapter<CancelReasonAdapter.ViewHolder>(){
    private var checkedRadioButton: CompoundButton? = null
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.cancel_reasonitem_layout))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: ViewHolder,position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            tv_title.text = items.reason
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



