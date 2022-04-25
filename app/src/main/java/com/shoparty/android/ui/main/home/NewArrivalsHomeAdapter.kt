package com.shoparty.android.ui.main.home

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.NewArrivalItemBinding
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.ui.main.product_list.ProductListActivity
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.Utils
import kotlinx.android.synthetic.main.home_offers_item_layout.view.*

class NewArrivalsHomeAdapter(
    private val list: ArrayList<HomeResponse.Home.ArrivalResponse>,
    val context: Context
) : RecyclerView.Adapter<NewArrivalsHomeAdapter.ViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_arrival_item, parent, false) as View
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

    class ViewHolder(
        val view: View,
        val listener: RVItemClickListener?,
        val context: Context
    ) :
        RecyclerView.ViewHolder(view) {
        private val binding: NewArrivalItemBinding? = DataBindingUtil.bind(view)

        fun bind(modal: HomeResponse.Home.ArrivalResponse) {
            val metrics: DisplayMetrics = context.resources.displayMetrics
            val deviceTotalWidth = metrics.widthPixels

            binding?.newarrivel?.minimumWidth  = (deviceTotalWidth/2.3).toInt()
            binding?.newarrivel?.maxWidth  = (deviceTotalWidth/2.3).toInt()

            binding?.newArrivalItemNameTv?.text = modal.product_name
            if (modal.sale_price != null)
                binding?.tvPrice?.text = context.getString(R.string.dollor)+modal.sale_price

            Glide.with(context).asBitmap().load(modal.image)
                .into(binding?.newArrivalItemImg!!)

            view.setOnClickListener {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra(Constants.IDPRODUCT,modal.product_id.toString())
                intent.putExtra(Constants.PRODUCATNAME,modal.product_name)
                intent.putExtra(Constants.PRODUCATDETAILSID,modal.product_detail_id.toString())
                intent.putExtra(Constants.PRODUCTSIZEID,modal.product_size_id.toString())
                intent.putExtra(Constants.PRODUCTCOLORID,modal.product_color_id.toString())
                context.startActivity(intent)
            }
        }
    }

}

