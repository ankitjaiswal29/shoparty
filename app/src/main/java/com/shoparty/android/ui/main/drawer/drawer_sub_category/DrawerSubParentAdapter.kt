package com.shoparty.android.ui.main.drawer.drawer_sub_category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.DrawerListItemsubParentLayoutBinding
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.ui.main.drawer.drawer_main_category.DrawerResponse
import com.shoparty.android.utils.PrefManager

class DrawerSubParentAdapter(
    var context: Context,
    private val list: ArrayList<DrawerResponse.Category.ChildCategory.ChildCategoryX>) :
    RecyclerView.Adapter<DrawerSubParentAdapter.MyViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.drawer_list_itemsub_parent_layout, parent, false)
        return MyViewHolder(itemView, listener, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        list[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(val view: View, val listener: RVItemClickListener?, val context: Context) : RecyclerView.ViewHolder(view) {
        val binding: DrawerListItemsubParentLayoutBinding? = DataBindingUtil.bind(view)
        init {
            view.setOnClickListener {
                if (binding?.ivArrow?.rotation == 90f){
                    binding.ivArrow.rotation = 180f
                    binding.rvChildCategory.visibility = View.GONE
                }else{
                    binding?.ivArrow?.rotation = 90f
                    binding?.rvChildCategory?.visibility = View.VISIBLE
                }
            }
        }
        fun bind(modal: DrawerResponse.Category.ChildCategory.ChildCategoryX) {
            if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
                binding?.mainSubParent?.layoutDirection = View.LAYOUT_DIRECTION_RTL
                binding?.ivArrow?.rotation = 180f
            }else {
                binding?.mainSubParent?.layoutDirection = View.LAYOUT_DIRECTION_LTR
                binding?.ivArrow?.rotation = 0f
            }
            binding?.tvTitle?.text = modal.category_name
            binding?.rvChildCategory?.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false)
            val adapter = DrawerSubChildAdapter(context,modal.child_category)
            binding?.rvChildCategory?.adapter = adapter
        }
    }

}


