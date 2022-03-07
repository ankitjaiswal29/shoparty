package com.shoparty.android.ui.ballons

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.iamkamrul.expandablerecyclerviewlist.viewholder.ChildViewHolder

import com.shoparty.android.R
import kotlinx.android.synthetic.main.item_category_list.view.*

class CategoryListViewHolder(view:View) : ChildViewHolder(view){
    fun bind(categoryList: CategoryList, context: Context){
        itemView.findViewById<TextView>(R.id.nameTv).text = categoryList.name
        itemView.nameTv.setOnClickListener {
            Toast.makeText(context,categoryList.name,Toast.LENGTH_LONG).show()
        }
    }
}