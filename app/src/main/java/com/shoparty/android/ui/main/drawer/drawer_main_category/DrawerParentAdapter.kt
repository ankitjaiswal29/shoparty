package com.shoparty.android.ui.main.drawer.drawer_main_category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.DrawerListItemLayoutBinding
import com.shoparty.android.interfaces.RVItemClickListener

class DrawerParentAdapter(
    var context: Context,
    private val list: ArrayList<DrawerResponse.Category>
) :
    RecyclerView.Adapter<DrawerParentAdapter.MyViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.drawer_list_item_layout, parent, false)
        return MyViewHolder(itemView, listener, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        list[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(
        val view: View,
        val listener: RVItemClickListener?,
        val context: Context
    ) :
        RecyclerView.ViewHolder(view) {

        private val binding: DrawerListItemLayoutBinding? = DataBindingUtil.bind(view)

        init {
            view.setOnClickListener {
                if (binding?.ivArrow?.rotation == 90f){
                    binding?.ivArrow?.rotation = 0f
                    binding?.rvChildCategory?.visibility = View.GONE
                }
                else{
                    binding?.ivArrow?.rotation = 90f
                    binding?.rvChildCategory?.visibility = View.VISIBLE
                }
            }
        }

        fun bind(modal: DrawerResponse.Category) {
            binding?.tvTitle?.text = modal.category_name
            binding?.rvChildCategory?.setLayoutManager(
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false

                )
            )
            val adapter = DrawerChildAdapter(context,modal.child_category)
            binding?.rvChildCategory?.adapter = adapter
        }
    }

}


