package com.shoparty.android.ui.main.drawer.drawer_main_category

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.DrawerListChildBinding
import com.shoparty.android.ui.main.drawer.drawer_sub_category.DrawerSubCategoryActivity
import com.shoparty.android.utils.PrefManager

class DrawerChildAdapter(
    var context: Context,
    private val itemList: List<DrawerResponse.Category.ChildCategory>
) :
    RecyclerView.Adapter<DrawerChildAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.drawer_list_child, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]

        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            holder.binding.mainLayoutC.layoutDirection = View.LAYOUT_DIRECTION_RTL
            holder.binding.tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_keyboard_arrow_back, 0, 0, 0);
        }else {
            holder.binding.mainLayoutC.layoutDirection = View.LAYOUT_DIRECTION_LTR
            holder.binding.tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_forward, 0);
        }

        holder.binding.tvTitle.text = item.category_name

        holder.binding.tvTitle.setOnClickListener {
            val intent = Intent(it.context, DrawerSubCategoryActivity::class.java)
                .putExtra("categoryName", item.category_name)
                .putParcelableArrayListExtra("category", item.child_category)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = DrawerListChildBinding.bind(view)
    }
}
