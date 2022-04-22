package com.shoparty.android.ui.productdetails

import android.os.Parcelable
import com.shoparty.android.ui.main.home.HomeResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProducatDetailsResponse(
    val message: String,
    val response_code: Int,
    val result: ProductData
) : Parcelable {
    @Parcelize
    data class ProductData(
        val also_bought: List<ProductDetailList>,
        val product_details: ProductDetails,
        val you_may_also_like: List<ProductDetailList>
    ) : Parcelable

    @Parcelize
    data class ProductDetails(
        val age_from: String,
        val delivery_time: String,
        val age_to: String,
        val ar_description: String,
        val ar_name: String,
        val barcode: String?,
        val brand_id: Int,
        val categories: List<Category>,
        val colors: List<Color>,
        val cost_price: String,
        val en_description: String,
        val en_name: String,
        val fav_status: Int,
        val gender: String,
        val id: Int,
        val images: List<HomeResponse.Home.Banner>,
        val is_customizable: Int,
        val is_deliverable: Int,
        val product_desc: String,
        val product_detail_id: String,
        val product_id: Int,
        val product_name: String,
        val sale_price: String,
        val season_id: String?,
        val sizes: List<Size>,
        val sold_quantity: String?,
        val status: Int,
        val stocks: List<Stock>,
        val tax: String,
        val tax_type: Int,
        val theme_id: String?,
        val in_cart: String?,
        val cart_quantity: Int?,
    ) : Parcelable

    @Parcelize
    data class Stock(
        val id: Int,
        val product_detail_id: Int,
        val product_id: Int,
        val product_size_id: Int,
        val status: Int,
        val stock_quantity: String
    ) : Parcelable

    @Parcelize
    data class Size(
        val id: Int,
        val name: String,
        val product_detail_id: Int,
        val product_id: Int,
        val status: Int,
    ) : Parcelable

    @Parcelize
    data class Image(
        val id: Int,
        val name: String,
        val product_detail_id: Int,
        val product_id: Int,
        val status: Int,
    ) : Parcelable

    @Parcelize
    data class Color(
        val color_id: Int,
        var color_code: String,
        val id: Int,
        val product_detail_id: Int,
        val product_id: Int,
        val status: Int,
        var ischecked: Boolean = false
    ) : Parcelable

    @Parcelize
    data class Category(
        val category_id: Int,
        val id: Int,
        val product_id: Int,
        val status: Int,
    ) : Parcelable

    @Parcelize
    data class ProductDetailList(
        val age_from: String,
        val age_to: String,
        val ar_color_name: String,
        val ar_description: String,
        val ar_name: String,
        val brand_id: Int,
        val color_name: String,
        val cost_price: String,
        val en_description: String,
        val en_name: String,
        val fav_status: Int,
        val gender: String,
        val id: Int,
        val image: String,
        val is_customizable: Int,
        val is_deliverable: Int,
        val product_color_id: Int,
        val product_descripion: String,
        val product_detail_id: Int,
        val product_id: Int,
        val product_image_id: Int,
        val product_name: String,
        val product_quantity_id: Int,
        val product_size_id: Int,
        val quantity: String,
        val sale_price: String,
        val season_id: String?,
        val size: String,
        val status: Int,
        val tax: String,
        val tax_type: Int,
        val theme_id: String?,
    ) : Parcelable


}