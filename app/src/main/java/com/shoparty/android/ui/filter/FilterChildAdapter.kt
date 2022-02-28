package com.shoparty.android.ui.filter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.FilterRecyclarItemLayoutBinding
import com.shoparty.android.ui.ballons.BallonsActivity
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.home.HomeCategoriesModel
import com.shoparty.android.utils.inflate
import kotlinx.android.synthetic.main.drawer_list_item_layout.view.*
import java.util.ArrayList

class FilterChildAdapter(
    var context: Context,
    private val itemList: List<String>
): RecyclerView.Adapter<FilterChildAdapter.MyViewHolder>()
{
    var check=false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_recyclar_item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val item = itemList[position]
       // holder.binding.tvTitle.text = item.name
        holder.binding.tvText.text = item

        holder.binding.tvText.setOnClickListener(View.OnClickListener {
            if(check){
                holder.binding.tvText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check_black, 0);
                check=false
                //Toast.makeText(context,item[position].toString(),Toast.LENGTH_LONG).show()
            }else{
                //Toast.makeText(context,item[position].toString(),Toast.LENGTH_LONG).show()
                check=true
                holder.binding.tvText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_24, 0);

            }
        })

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var  binding = FilterRecyclarItemLayoutBinding.bind(view)
    }

}
