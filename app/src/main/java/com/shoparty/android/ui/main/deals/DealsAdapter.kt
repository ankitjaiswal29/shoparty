package com.shoparty.android.ui.main.deals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.DealsItemLayoutBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener

class DealsAdapter(
    private val list: List<DealsResponse.Deals>,
    val context: Context
) : RecyclerView.Adapter<DealsAdapter.ViewHolderDeals>() {

    var listener: RecyclerViewClickListener? = null

    fun onItemClick(listener: RecyclerViewClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDeals {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.deals_item_layout, parent, false) as View
        return ViewHolderDeals(
            view = view,
            listener = listener,
            context = context

        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolderDeals, position: Int) {
        list[position].let { holder.bind(it) }
    }

    class ViewHolderDeals(val view: View, val listener: RecyclerViewClickListener?, val context: Context) :
        RecyclerView.ViewHolder(view) {

        private val binding: DealsItemLayoutBinding? = DealsItemLayoutBinding.bind(view)

        init {
            view.setOnClickListener { listener?.click(adapterPosition.toString()) }
        }

        fun bind(modal: DealsResponse.Deals ) {
            binding?.tvProductname?.text = modal.language_id
            binding?.tvProductSubtitle?.text = modal.limit
            binding?.tvPrice?.text = modal.offset
            Glide.with(context).asBitmap().load(modal.language_id).into(binding?.ivDealsimg!!)
        }
    }
}
