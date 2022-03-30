package com.shoparty.android.ui.main.categories


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.ui.main.product_list.ProductListActivity
import com.shoparty.android.utils.Constants

class CategoryAdapter1( val context: Context,
                        private val list: List<CategoryResponse.Category>) : RecyclerView.Adapter<CategoryAdapter1.ViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

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
            val intent = Intent(context, ProductListActivity::class.java)
            intent.putExtra(Constants.PRODUCTID,modal.id)  //categoryid
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