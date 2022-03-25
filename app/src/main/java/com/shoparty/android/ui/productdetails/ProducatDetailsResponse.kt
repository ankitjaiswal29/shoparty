package com.shoparty.android.ui.productdetails

import com.shoparty.android.ui.main.home.HomeResponse

data class ProducatDetailsResponse(
    val message: String,
    val response_code: Int,
    val result: ProductData
)
{
    data class ProductData(
        val also_bought: List<AlsoBought>,
        val product_details: ProductDetails,
        val you_may_also_like: List<YouMayAlsoLike>
    )

    data class ProductDetails(
        val age_from: String,
        val age_to: String,
        val ar_description: String,
        val ar_name: String,
        val barcode: String,
        val brand_id: Int,
        val categories: List<Category>,
        val colors: List<Color>,
        val cost_price: String,
        val created_at: String,
        val deleted_at: String,
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
        val season_id: String,
        val sizes: List<Size>,
        val sold_quantity: String,
        val status: Int,
        val stocks: List<Stock>,
        val tax: String,
        val tax_type: Int,
        val theme_id: String,
        val updated_at: String
    )
    data class Stock(
        val created_at: String,
        val deleted_at: String,
        val id: Int,
        val product_detail_id: Int,
        val product_id: Int,
        val product_size_id: Int,
        val status: Int,
        val stock_quantity: String,
        val updated_at: String
    )

    data class Size(
        val created_at: String,
        val id: Int,
        val name: String,
        val product_detail_id: Int,
        val product_id: Int,
        val status: Int,
        val updated_at: String
    )

    data class Image(
        val created_at: String,
        val deleted_at: Any,
        val id: Int,
        val name: String,
        val product_detail_id: Int,
        val product_id: Int,
        val status: Int,
        val updated_at: String
    )

    data class Color(
        val color_id: Int,
        val created_at: String,
        val deleted_at: String,
        val id: Int,
        val product_detail_id: Int,
        val product_id: Int,
        val status: Int,
        val updated_at: String
    )

    data class Category(
        val category_id: Int,
        val created_at: String,
        val deleted_at: String,
        val id: Int,
        val product_id: Int,
        val status: Int,
        val updated_at: String
    )

    data class AlsoBought(
        val age_from: String,
        val age_to: String,
        val ar_color_name: String,
        val ar_description: String,
        val ar_name: String,
        val brand_id: Int,
        val color_name: String,
        val cost_price: String,
        val created_at: String,
        val deleted_at: String,
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
        val season_id: String,
        val size: String,
        val status: Int,
        val tax: String,
        val tax_type: Int,
        val theme_id: String,
        val updated_at: String
    )


    data class YouMayAlsoLike(
        val age_from: String,
        val age_to: String,
        val ar_color_name: String,
        val ar_description: String,
        val ar_name: String,
        val brand_id: Int,
        val color_name: String,
        val cost_price: String,
        val created_at: String,
        val deleted_at: String,
        val en_description: String,
        val en_name: String,
        val fav_status: Int,
        val gender: String,
        val id: Int,
        val image: String,
        val is_customizable: Int,
        val is_deliverable: Int,
        val product_color_id: Int,
        val product_descripion: Any,
        val product_detail_id: Int,
        val product_id: Int,
        val product_image_id: Int,
        val product_name: String,
        val product_quantity_id: Int,
        val product_size_id: Int,
        val quantity: String,
        val sale_price: String,
        val season_id: String,
        val size: String,
        val status: Int,
        val tax: String,
        val tax_type: Int,
        val theme_id: String,
        val updated_at: String
    )


}