package com.shoparty.android.ui.main.myaccount

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.MyaccountItemBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener

class MyAccountAdapter(
    val context:Context,
    private val mList: List<MyAccountCustomModel>,
    private val recyclerViewClickListener: RecyclerViewClickListener) :
    RecyclerView.Adapter<MyAccountAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.myaccount_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val item = mList[position]
        holder.binding.textview.text=item.text

        holder.binding.ivIcon.setImageResource(item.image)

        holder.itemView.setOnClickListener {
            recyclerViewClickListener.click(item.id)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding =MyaccountItemBinding.bind(view)
        init {
        }
    }

}