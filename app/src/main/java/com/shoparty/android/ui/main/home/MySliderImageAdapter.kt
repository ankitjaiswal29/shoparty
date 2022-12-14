package com.shoparty.android.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.common_modal.Product
import com.smarteist.autoimageslider.SliderViewAdapter

class MySliderImageAdapter(val context: Context) :
    SliderViewAdapter<MySliderImageAdapter.VH>() {
    private var mSliderItems = ArrayList<HomeResponse.Home.Banner>()
    fun renewItems(sliderItems: ArrayList<HomeResponse.Home.Banner>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup): VH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, null)
        return VH(inflate)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        Glide.with(context).asBitmap().load(mSliderItems[position].image)
           // .placeholder(R.drawable.ic_launcher_foreground)
            //.error(R.drawable.ic_launcher_foreground)
            .into(viewHolder.imageView)
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class VH(itemView: View) : ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageSlider)

    }
}