package com.shoparty.android.ui.vouchers

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.shoparty.android.R
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.utils.Utils


class VoucherAdapter(context:Context,private val mList: List<VoucherListResponse.Data>,
                     var recyclerViewClickListener: RecyclerViewClickListener
) : RecyclerView.Adapter<VoucherAdapter.ViewHolder>()
{
    private val context: Context= context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.voucher_item_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.txtDiscount.text=ItemsViewModel.discount.toString()+context.getString(R.string._54_off)
        holder.tvMinimulPurchase.text=context.getString(R.string.on_minimum_purchase_of_200)+" "+
                ItemsViewModel.min_purchase
        holder.tvCode.text=ItemsViewModel.coupon_code
        holder.txtExpiry.text=context.getString(R.string.strexpiry)+" "+ Utils.convertToCustomFormat(ItemsViewModel.end_date)

        holder.tvCopy.setOnClickListener {
            recyclerViewClickListener.click(holder.tvCode.text.toString())
        }

    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtDiscount: TextView = itemView.findViewById(R.id.txtDiscount)
        val tvMinimulPurchase: TextView = itemView.findViewById(R.id.tvMinimulPurchase)
        val tvCode: TextView = itemView.findViewById(R.id.tvCode)
        val txtExpiry: TextView = itemView.findViewById(R.id.txtExpiry)
        val tvCopy: TextView = itemView.findViewById(R.id.tv_Copy)
    }
}