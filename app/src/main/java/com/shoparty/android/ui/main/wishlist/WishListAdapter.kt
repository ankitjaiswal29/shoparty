package com.shoparty.android.ui.main.wishlist


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.WishlistItemLayoutBinding
import com.shoparty.android.interfaces.RecyclerViewWishListClickListener

class WishListAdapter(
    private val list: List<WishListResponse.Data>,
    var recyclerViewWishListClickListener: RecyclerViewWishListClickListener
) : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {

 /*   var listener: RecyclerViewClickListener? = null

    fun onItemClick(listener: RecyclerViewClickListener) {
        this.listener = listener
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wishlist_item_layout, parent, false) as View
        return ViewHolder(
            view = view,
            listener = recyclerViewWishListClickListener
           /* listener = listener,
            context = context*/
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let { holder.bind(it) }
    }

    class ViewHolder(val view: View, val listener: RecyclerViewWishListClickListener) :
        RecyclerView.ViewHolder(view) {

        private val binding: WishlistItemLayoutBinding? = DataBindingUtil.bind(view)

       /* init {
            view.setOnClickListener { listener?.itemclick(listadapterPosition.toString()) }
        }*/

        fun bind(modal: WishListResponse.Data ) {
            binding?.tvItemName?.text = modal.product_name
            binding?.clWishlistRootItem?.setOnClickListener {
                listener.itemclick(modal.id,modal)
            }
           // Glide.with(context).asBitmap().load(modal.category_image).into(binding?.ivCategoryItem!!)
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