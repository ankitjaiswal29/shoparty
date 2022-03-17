package com.shoparty.android.ui.main.categories


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.CategoryItem1Binding
import com.shoparty.android.interfaces.RecyclerViewAddressClickListener
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse
import com.shoparty.android.ui.main.topselling.TopSellingActivity
import com.shoparty.android.utils.Constants

class CategoryAdapter1( val context: Context,
                        private val list: List<CategoryResponse.Category>
) : RecyclerView.Adapter<CategoryAdapter1.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modal = list[position]
        holder.categoryName?.text = modal.category_name
        Glide.with(context).asBitmap().load(modal.category_image).into(holder.ivCategoryItem!!)

        holder.category_root_lay?.setOnClickListener {
            val intent = Intent(context, TopSellingActivity::class.java)
            intent.putExtra(Constants.PRODUCTID,modal.id)
            intent.putExtra(Constants.CATEGORYFRAGMENT,"1")
            intent.putExtra("categoryname",holder.categoryName?.text.toString())
            context.startActivity(intent)
        }


    }
    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivCategoryItem: ImageView = itemView.findViewById(R.id.ivCategoryItem)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val category_root_lay: ConstraintLayout = itemView.findViewById(R.id.category_root_lay)

    }
}