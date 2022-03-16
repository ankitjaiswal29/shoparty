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
import com.shoparty.android.interfaces.RecyclerViewItemClickListener

class ThemeAdapter(
    private val list: ArrayList<HomeResponse.Home.Theme>,
    val context: Context
) : RecyclerView.Adapter<ThemeAdapter.ViewHolder>() {

    var listener: RecyclerViewItemClickListener? = null

    fun onItemClick(listener: RecyclerViewItemClickListener) {
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
        val listener: RecyclerViewItemClickListener?,
        val context: Context
    ) :
        RecyclerView.ViewHolder(view) {

        private val binding: HomeCategoriesLayoutItemBinding? = DataBindingUtil.bind(view)

        init {
            view.setOnClickListener { listener?.onClick(adapterPosition.toString()) }
        }

        fun bind(modal: HomeResponse.Home.Theme) {
            binding?.homeCategoriesItemNameTv?.text = modal.theame_name
            Glide.with(context).asBitmap().load(modal.theme_image).into(binding?.imgBanner!!)
        }
    }

}