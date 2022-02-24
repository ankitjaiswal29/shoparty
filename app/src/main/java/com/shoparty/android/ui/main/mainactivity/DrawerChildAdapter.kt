package com.shoparty.android.ui.main.mainactivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.DrawerListChildBinding
import com.shoparty.android.databinding.DrawerListItemLayoutBinding
import com.shoparty.android.ui.ballons.BallonsActivity
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.home.HomeCategoriesModel
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.drawer_list_item_layout.view.*
import java.util.ArrayList

class DrawerChildAdapter(
    var context: Context,
    private val itemList: List<HomeCategoriesModel>):
    RecyclerView.Adapter<DrawerChildAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.drawer_list_child, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val item = itemList[position]
       // holder.binding.tvTitle.text = item.name
        holder.binding.tvTitle.text = "ballons"

        holder.binding.tvTitle.setOnClickListener(View.OnClickListener {
            val intent = Intent(it.context, BallonsActivity::class.java)
            it.context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var  binding = DrawerListChildBinding.bind(view)
    }

}
