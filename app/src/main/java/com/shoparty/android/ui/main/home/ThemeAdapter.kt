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
import com.shoparty.android.databinding.HomeCategoriesLayoutItemBinding
import com.shoparty.android.databinding.HomeThemeItemBinding
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.ui.main.product_list.ProductListActivity
import com.shoparty.android.utils.Constants

class ThemeAdapter(
    private val list: ArrayList<HomeResponse.Home.Theme>,
    val context: Context
) : RecyclerView.Adapter<ThemeAdapter.ViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_theme_item, parent, false) as View
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

        private val binding: HomeThemeItemBinding? = DataBindingUtil.bind(view)

        init { }

        fun bind(modal: HomeResponse.Home.Theme) {
            val metrics: DisplayMetrics = context.resources.displayMetrics
            val deviceTotalWidth = metrics.widthPixels
            binding?.clCategoryItemRoot?.minimumWidth  = (deviceTotalWidth/2.3).toInt()
            binding?.clCategoryItemRoot?.maxWidth  = (deviceTotalWidth/2.3).toInt()



            binding?.homeCategoriesItemNameTv?.text = modal.theame_name
            Glide.with(context).asBitmap().load(modal.theme_image).into(binding?.imgBanner!!)
            view.setOnClickListener {
                val intent = Intent(context, ProductListActivity::class.java)
                intent.putExtra(Constants.THEMESITEMS,"5")
                intent.putExtra(Constants.PRODUCTID,modal.category_id)  //themeid
                intent.putExtra(Constants.CATEGORYNAME,modal.theame_name)
                context.startActivity(intent)
            }
        }
    }

}