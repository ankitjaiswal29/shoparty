package com.shoparty.android.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.HomeCategoriesLayoutItemBinding
import com.shoparty.android.interfaces.RVItemClickListener

class BrandsAdapter(
    private val list: ArrayList<HomeResponse.Home.Brand>,
    val context: Context
) : RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_categories_layout_item, parent, false) as View
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

        private val binding: HomeCategoriesLayoutItemBinding? = DataBindingUtil.bind(view)

        init {
            view.setOnClickListener { listener?.onClick(adapterPosition.toString()) }
        }

        fun bind(modal: HomeResponse.Home.Brand) {
            binding?.homeCategoriesItemNameTv?.text = modal.brand_name
            Glide.with(context).asBitmap().load(modal.brand_image).into(binding?.imgBanner!!)
        }
    }

}