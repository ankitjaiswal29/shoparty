package com.shoparty.android.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.common_modal.Category
import com.shoparty.android.common_modal.Product
import com.shoparty.android.databinding.SearchItemLayBinding
import com.shoparty.android.databinding.SearchItemLayCategoryBinding
import com.shoparty.android.interfaces.RVItemClickListener

class SearchHistoryCategoryAdapter(var context: Context,
                                   private var itemList: List<Category>) :
    RecyclerView.Adapter<SearchHistoryCategoryAdapter.ViewHolder>() {
    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchHistoryCategoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item_lay_category, parent, false) as View
        return SearchHistoryCategoryAdapter.ViewHolder(
            view = view,
            listener = listener,
            context = context
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemList[position].let { holder.bind(it) }
    }

    class ViewHolder(
        val view: View,
        val listener: RVItemClickListener?,
        val context: Context
    ) :
        RecyclerView.ViewHolder(view) {
        private val binding: SearchItemLayCategoryBinding? = DataBindingUtil.bind(view)
        init
        {
            view.setOnClickListener { listener?.onClick(adapterPosition.toString()) }
        }

        fun bind(modal: Category) {
            binding?.searchItemNameTv?.text = modal.category_name
            //Glide.with(context).asBitmap().load(modal.image).into(binding?.!!)
        }
    }

}