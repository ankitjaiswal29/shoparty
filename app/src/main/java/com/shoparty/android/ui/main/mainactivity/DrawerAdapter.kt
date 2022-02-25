package com.shoparty.android.ui.main.mainactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.DrawerListItemLayoutBinding
import com.shoparty.android.ui.main.home.HomeCategoriesModel

class DrawerAdapter(
    var context: Context,
    private val itemList: List<HomeCategoriesModel>):
    RecyclerView.Adapter<DrawerAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.drawer_list_item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val item = itemList[position]
        holder.binding.tvTitle.text = item.name

        holder.binding.arrowStriet.setOnClickListener(View.OnClickListener {
            holder.binding.arrowStriet.setVisibility(View.GONE)
            holder.binding.arrowUp.setVisibility(View.VISIBLE)
            holder.binding.recycleChild.setVisibility(View.VISIBLE)
        })

        holder.binding.arrowUp.setOnClickListener(View.OnClickListener {
            holder.binding.arrowStriet.setVisibility(View.VISIBLE)
            holder.binding.arrowUp.setVisibility(View.GONE)
            holder.binding.recycleChild.setVisibility(View.GONE)
        })

        holder.binding.recycleChild.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        val drawerchildadapter = DrawerChildAdapter(context,itemList)
        holder.binding.recycleChild.setAdapter(drawerchildadapter)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var  binding = DrawerListItemLayoutBinding.bind(view)
    }

}
