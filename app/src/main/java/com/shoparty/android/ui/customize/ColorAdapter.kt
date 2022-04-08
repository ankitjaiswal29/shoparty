package com.shoparty.android.ui.customize

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.ItemColorBinding
import com.shoparty.android.interfaces.RVItemClickListener

class ColorAdapter(
    val context: Context,
    private val list: List<String>
) :
    RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_color, parent, false)
        return ViewHolder(view, listener, context)
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

        private val binding: ItemColorBinding? = DataBindingUtil.bind(view)

        init {
            view.setOnClickListener {
                listener?.onClick(adapterPosition.toString())
            }
        }

        fun bind(modal: String) {
            binding?.view?.setBackgroundColor(Color.parseColor(modal))

        }
    }

}