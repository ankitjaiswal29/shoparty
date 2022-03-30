package com.shoparty.android.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.ItemBannerBinding
import com.shoparty.android.interfaces.RVItemClickListener

class BannerAdapter(
    private val list: ArrayList<HomeResponse.Home.Banner>,
    val context: Context
) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false) as View
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

        private val binding: ItemBannerBinding? = DataBindingUtil.bind(view)

        init {
            view.setOnClickListener { listener?.onClick(adapterPosition.toString()) }
        }

        fun bind(modal: HomeResponse.Home.Banner) {
            Glide.with(context).asBitmap().load(modal.image).into(binding?.imgBanner!!)
        }
    }

}