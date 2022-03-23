package com.shoparty.android.ui.main.drawer.drawer_sub_category

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.DrawerListChildBinding
import com.shoparty.android.ui.main.drawer.drawer_main_category.DrawerResponse
import com.shoparty.android.ui.main.product_list.ProductListActivity
import com.shoparty.android.utils.Constants

class DrawerSubChildAdapter(
    var context: Context,
    private val itemList: List<DrawerResponse.Category.ChildCategory.ChildCategoryX.ChildCategoryXX>
) :
    RecyclerView.Adapter<DrawerSubChildAdapter.MyViewHolder>() {
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
        holder.binding.tvTitle.text = item.category_name

        holder.binding.tvTitle.setOnClickListener {
            val intent = Intent(context, ProductListActivity::class.java)
                .putExtra(Constants.CATEGORYNAME,item.category_name)
                .putExtra(Constants.PRODUCTID,item.id.toString())
                .putExtra(Constants.DRAWERSUBCATEGORY,"3")
                context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = DrawerListChildBinding.bind(view)
    }

}
