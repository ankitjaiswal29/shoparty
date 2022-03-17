package com.shoparty.android.ui.main.deals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.DealsItemLayoutBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener

class DealsAdapter(
    private val list: List<DealsResponse.Deals>,
    val context: Context
) : RecyclerView.Adapter<DealsAdapter.ViewHolderDeals>() {

    var listener: RecyclerViewClickListener? = null

    fun onItemClick(listener: RecyclerViewClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDeals {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.deals_item_layout, parent, false) as View
        return ViewHolderDeals(
            view = view,
            listener = listener,
            context = context

        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolderDeals, position: Int) {
        list[position].let { holder.bind(it) }
    }

    class ViewHolderDeals(val view: View, val listener: RecyclerViewClickListener?, val context: Context) :
        RecyclerView.ViewHolder(view) {

        private val binding: DealsItemLayoutBinding? = DealsItemLayoutBinding.bind(view)

        init {
            view.setOnClickListener { listener?.click(adapterPosition.toString()) }
        }

        fun bind(modal: DealsResponse.Deals ) {
            binding?.tvProductname?.text = modal.language_id
            binding?.tvProductSubtitle?.text = modal.limit
            binding?.tvPrice?.text = modal.offset
            Glide.with(context).asBitmap().load(modal.language_id).into(binding?.ivDealsimg!!)


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





//    (private val itemList: List<TopSellingHomeModel>, val requireContext: Context) :
//    RecyclerView.Adapter<DealsAdapter.NewArrivalItemLIstViewHolder>() {
//
//    var fav = false;
//
//    inner class NewArrivalItemLIstViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): NewArrivalItemLIstViewHolder {
//        return NewArrivalItemLIstViewHolder(parent.inflate(R.layout.deals_item_layout))
//    }
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//
//    override fun onBindViewHolder(holder: NewArrivalItemLIstViewHolder, position: Int) {
//        val items = itemList[position]
//        holder.itemView.apply {
//
//            tv_productname.text = items.name
//
//            /* tsi_item_name.text = items.name
//             tsi_item_price_tv.text = items.price
//
//             top_selling_item_root.setOnClickListener {
//                 findNavController().navigate(R.id.newArrivalItemDetailsFragment)
//             }*/
//        }
//
//        holder.itemView.iv_background.setOnClickListener {
//            if (fav) {
//                holder.itemView.iv_unselect.visibility = View.GONE
//                holder.itemView.iv_select.visibility = View.VISIBLE
//                fav = false
//
//            } else
//            {
//                holder.itemView.iv_select.visibility = View.GONE
//                holder.itemView.iv_unselect.visibility = View.VISIBLE
//                fav = true
//
//            }
//        }
//
//        holder.itemView.top_selling_item_root.setOnClickListener {
//            val intent = Intent(requireContext, ProductDetailsActivity::class.java)
//            requireContext.startActivity(intent)
//
//        }
//    }
//}


