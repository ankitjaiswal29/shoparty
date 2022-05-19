package com.shoparty.android.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.common_modal.Product
import com.shoparty.android.databinding.SearchItemLayBinding
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.utils.PrefManager

class SearchHistoryProductAdapter(var context: Context, private var itemList: List<Product>) :
    RecyclerView.Adapter<SearchHistoryProductAdapter.ViewHolder>() {
    var listener: RVItemClickListener? = null
    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchHistoryProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item_lay, parent, false) as View
        return SearchHistoryProductAdapter.ViewHolder(
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
        private val binding: SearchItemLayBinding? = DataBindingUtil.bind(view)
        init
        {
            view.setOnClickListener { listener?.onClick(adapterPosition.toString()) }
        }

        fun bind(modal: Product) {
            binding?.searchItemNameTv?.text = modal.en_name
            //Glide.with(context).asBitmap().load(modal.image).into(binding?.!!)
            if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
                binding?.searchItemArrowImg?.rotation = 180F
            }else {
                binding?.searchItemArrowImg?.rotation = 0F
            }
        }
    }

}