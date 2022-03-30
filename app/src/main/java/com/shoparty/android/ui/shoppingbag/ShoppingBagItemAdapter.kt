package com.shoparty.android.ui.shoppingbag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.databinding.BagItemLayoutBinding
import com.shoparty.android.interfaces.RVCartItemClickListener

class ShoppingBagItemAdapter(val context: Context, val list: List<CartProduct>) :
    RecyclerView.Adapter<ShoppingBagItemAdapter.ViewHolder>() {

    var listener: RVCartItemClickListener? = null

    fun onItemClick(listener: RVCartItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingBagItemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bag_item_layout, parent, false) as View
        return ShoppingBagItemAdapter.ViewHolder(
            view = view,
            listener = listener,
            context = context
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ShoppingBagItemAdapter.ViewHolder, position: Int) {
        list[position].let { holder.bind(it) }
    }

    class ViewHolder(
        val view: View,
        val listener: RVCartItemClickListener?,
        val context: Context
    ) :
        RecyclerView.ViewHolder(view) {

        private val binding: BagItemLayoutBinding? = DataBindingUtil.bind(view)

        init {
            view.setOnClickListener { listener?.onClick(adapterPosition) }
        }

        fun bind(modal: CartProduct) {

            Glide.with(context).asBitmap().load(modal.image).into(binding?.ivItem!!)
            binding.tvCount.text = modal.quantity
            binding.tvName.text = modal.en_name

            binding.ivClose.setOnClickListener {
                listener?.onClear(adapterPosition)
            }

            binding.ivMinus.setOnClickListener {
                listener?.onMinus(adapterPosition, binding.tvCount)
            }

            binding.ivPlus.setOnClickListener {
                listener?.onPlus(adapterPosition, binding.tvCount)
            }
        }
    }

}