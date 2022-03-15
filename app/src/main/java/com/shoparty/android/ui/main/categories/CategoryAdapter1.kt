package com.shoparty.android.ui.main.categories


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.CategoryItem1Binding
import com.shoparty.android.interfaces.RecyclerViewClickListener

class CategoryAdapter1( val context: Context,
    private val list: List<CategoryResponse.Category>,
   val recyclerViewClickListener: RecyclerViewClickListener
) : RecyclerView.Adapter<CategoryAdapter1.ViewHolder>() {

  /*  var listener: RecyclerViewClickListener? = null

    fun onItemClick(listener: RecyclerViewClickListener) {
        this.listener = listener
    }
*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item1, parent, false) as View
        return ViewHolder(
            view = view,
            listener = recyclerViewClickListener,
            context = context
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let { holder.bind(it,recyclerViewClickListener) }
    }

    class ViewHolder(val view: View, val listener: RecyclerViewClickListener?, val context: Context) :
        RecyclerView.ViewHolder(view) {

        private val binding: CategoryItem1Binding? = DataBindingUtil.bind(view)

       /* init {
            view.setOnClickListener { listener?.click(adapterPosition.toString()) }
        }*/

        fun bind(
            modal: CategoryResponse.Category,
            recyclerViewClickListener: RecyclerViewClickListener
        ) {
            binding?.categoryName?.text = modal.category_name
            Glide.with(context).asBitmap().load(modal.category_image).into(binding?.ivCategoryItem!!)
            binding.categoryRootLay.setOnClickListener {
               recyclerViewClickListener.click(modal.id!!)
               // Toast.makeText(context,modal.category_id, Toast.LENGTH_LONG).show()
            }
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