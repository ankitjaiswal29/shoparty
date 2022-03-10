package com.shoparty.android.ui.main.categories


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.CategoryItem1Binding
import com.shoparty.android.interfaces.RecyclerViewClickListener

class CategoryAdapter1(
    private val list: List<CategoryResponse.Category>,
    val context: Context
) : RecyclerView.Adapter<CategoryAdapter1.ViewHolder>() {

    var listener: RecyclerViewClickListener? = null

    fun onItemClick(listener: RecyclerViewClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item1, parent, false) as View
        return ViewHolder(
            view = view,
            listener = listener,
            context = context
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let { holder.bind(it) }
    }

    class ViewHolder(val view: View, val listener: RecyclerViewClickListener?, val context: Context) :
        RecyclerView.ViewHolder(view) {

        private val binding: CategoryItem1Binding? = DataBindingUtil.bind(view)

        init {
            view.setOnClickListener { listener?.click(adapterPosition.toString()) }
        }

        fun bind(modal: CategoryResponse.Category ) {
            binding?.categoryName?.text = modal.category_name
            Glide.with(context).asBitmap().load(modal.category_image).into(binding?.ivCategoryItem!!)
//        holder.itemView.apply {
//            category_name.text = items.name
//            category_root_lay.setOnClickListener {
//                //  findNavController().navigate(R.id.categoryItemListFragment)
//            }
//        }
//
//        holder.itemView.category_root_lay.setOnClickListener {
//            val intent = Intent(context, TopSellingActivity::class.java)
//            context.startActivity(intent)
        }
    }

}