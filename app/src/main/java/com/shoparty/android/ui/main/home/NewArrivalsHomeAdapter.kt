package com.shoparty.android.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.NewArrivalItemBinding
import com.shoparty.android.interfaces.RVItemClickListener

class NewArrivalsHomeAdapter(
    private val list: ArrayList<HomeResponse.Home.Arrival>,
    val context: Context
) : RecyclerView.Adapter<NewArrivalsHomeAdapter.ViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_arrival_item, parent, false) as View
        return ViewHolder(
            view = view,
            listener = listener,
            context = context
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let { holder.bind(it) }
    }

    class ViewHolder(
        val view: View,
        val listener: RVItemClickListener?,
        val context: Context
    ) :
        RecyclerView.ViewHolder(view) {

        private val binding: NewArrivalItemBinding? = DataBindingUtil.bind(view)

        init {
            view.setOnClickListener { listener?.onClick(adapterPosition.toString()) }
        }

        fun bind(modal: HomeResponse.Home.Arrival) {
            binding?.newArrivalItemNameTv?.text = modal.arrival_name
            if (modal.price != null)
                binding?.tvPrice?.text = modal.price
            Glide.with(context).asBitmap().load(modal.arrival_image)
                .into(binding?.newArrivalItemImg!!)

        }
    }

}

