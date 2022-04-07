package com.shoparty.android.ui.customize

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.DrawerListItemLayoutBinding
import com.shoparty.android.databinding.ItemCategoryBinding
import com.shoparty.android.databinding.ItemTextBinding
import com.shoparty.android.databinding.PopupLayoutAvailableFontsBinding
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.ui.main.drawer.drawer_main_category.DrawerChildAdapter
import com.shoparty.android.ui.main.drawer.drawer_main_category.DrawerResponse

class SizeAdapter(
    val context: Context,
    private val list: List<Int>
) :
    RecyclerView.Adapter<SizeAdapter.ViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_text, parent, false)
        return ViewHolder(view,listener,context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(
        val view: View,
        val listener: RVItemClickListener?,
        val context: Context
    ) :
        RecyclerView.ViewHolder(view) {

        private val binding: ItemTextBinding? = DataBindingUtil.bind(view)

        init {
            view.setOnClickListener {
                listener?.onClick(adapterPosition.toString())
            }
        }

        fun bind(modal:Int) {
            binding?.tvData?.text = modal.toString()

        }
    }

}