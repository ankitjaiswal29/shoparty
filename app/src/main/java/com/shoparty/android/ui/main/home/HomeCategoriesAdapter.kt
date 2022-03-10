package com.shoparty.android.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.HomeCategoriesItemBinding
import com.shoparty.android.databinding.HomeCategoriesLayoutItemBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.utils.Utils

class HomeCategoriesAdapter(
    private val list: ArrayList<HomeResponse.TopBanner>,
    val context: Context
): RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder>() {

    var listener: RecyclerViewClickListener? = null

    fun onItemClick(listener: RecyclerViewClickListener) {
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

    class ViewHolder(val view: View, val listener: RecyclerViewClickListener?, val context: Context) :
        RecyclerView.ViewHolder(view) {

        private val binding: HomeCategoriesLayoutItemBinding? = DataBindingUtil.bind(view)

        init {
            view.setOnClickListener { listener?.click(adapterPosition.toString()) }
        }

        fun bind(modal: HomeResponse.TopBanner ) {
            binding?.homeCategoriesItemNameTv?.text = modal.id
            Glide.with(context).asBitmap().load(modal.image).into(binding?.imgBanner!!)
            //        holder.itemView.apply {
//            home_categories_item_name_tv.text = items.name
//        }
//        holder.itemView.cl_category_item_root.setOnClickListener {
//            val intent = Intent(context, TopSellingActivity::class.java)
//            context.startActivity(intent)
//        }
        }
    }

}