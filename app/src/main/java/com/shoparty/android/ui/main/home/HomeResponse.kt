package com.shoparty.android.ui.main.home

data class HomeResponse(
    val message: String,
    val response_code: Int,
    val result: Home
) {
    data class Home(
        val top_banner: List<Banner>,
        val bottom_banner: List<Banner>,
        val upcoming_banner: List<Banner>,
        val category_response: List<Category>,
        val season_response: List<Season>,
        val theame_response: List<Theme>,
        val arrival_response: List<ArrivalResponse>,
        val brand_response: List<Brand>,
        val OffersAndDiscountedItems: List<OffersAndDiscountedItem>,
        val top20Selling: List<Top20Selling>,
        val topSubCategory: List<TopSubCategory>,
        )
    {
        data class Banner(
            val created_at: String,
            val deleted_at: Any,
            val id: Int,
            val name: String,
            val product_detail_id: Int,
            val product_id: Int,
            val status: Int,
            val updated_at: String,
            val image: String
        )
        data class Category(
            val category_id: String,
            val category_name: String,
            val category_image: String
        )
        data class Theme(
            val theme_id: String,
            val theame_name: String,
            val theme_image: String
        )
        data class Brand(
            val brand_id: String,
            val brand_name: String,
            val brand_image: String
        )
        data class Season(
            val season_id: String,
            val season_name: String,
            val season_image: String
        )
        data class ArrivalResponse(
            val age_from: String,
            val age_to: String,
            val ar_color_name: String,
            val ar_description: String,
            val ar_name: String,
            val brand_id: Int,
            val color_id: String,
            val color_name: String,
            val cost_price: String,
            val created_at: String,
            val deleted_at: Any,
            val delivery_time: String,
            val discount: Int,
            val en_description: String,
            val en_name: String,
            val fav_status: Int,
            val gender: String,
            val id: Int,
            val image: String,
            val is_customizable: Int,
            val is_deliverable: Int,
            val product_age_id: Int,
            val product_color_id: String,
            val product_descripion: String,
            val product_detail_id: Int,
            val product_id: Int,
            val product_image_id: Int,
            val product_name: String,
            val product_quantity_id: Int,
            val product_size_id: Int,
            val quantity: String,
            val sale_price: String,
            val season_id: Any,
            val size: String,
            val status: Int,
            val store_id: Any,
            val tax: String,
            val tax_type: Int,
            val theme_id: Any,
            val updated_at: String
        )
    }

    data class OffersAndDiscountedItem(
        val age_from: String,
        val age_to: String,
        val ar_color_name: String,
        val ar_description: String,
        val ar_name: String,
        val brand_id: Int,
        val color_id: String,
        val color_name: String,
        val cost_price: String,
        val created_at: String,
        val deleted_at: Any,
        val discount: Int,
        val en_description: String,
        val en_name: String,
        val fav_status: Int,
        val gender: String,
        val id: Int,
        val image: String,
        val is_customizable: Int,
        val is_deliverable: Int,
        val product_age_id: Int,
        val product_color_id: String,
        val product_descripion: String,
        val product_detail_id: Int,
        val product_id: Int,
        val product_image_id: Int,
        val product_name: String,
        val product_quantity_id: Int,
        val product_size_id: Int,
        val quantity: String,
        val sale_price: String,
        val season_id: Any,
        val size: String,
        val status: Int,
        val store_id: Any,
        val tax: String,
        val tax_type: Int,
        val theme_id: Any,
        val offer_code: String,
        val updated_at: String,
        val offer_discount: String,
        val offer_amount: String,
    )

    data class TopSubCategory(
        val category_id: String,
        val category_image: String,
        val category_name: String
    )
    data class Top20Selling(
        val age_from: String,
        val age_to: String,
        val ar_color_name: String,
        val ar_description: String,
        val ar_name: String,
        val brand_id: Int,
        val color_id: String,
        val color_name: String,
        val cost_price: String,
        val created_at: String,
        val deleted_at: Any,
        val discount: Any,
        val en_description: String,
        val en_name: String,
        var fav_status: Int,
        val gender: String,
        val id: Int,
        val image: String,
        val is_customizable: Int,
        val is_deliverable: Int,
        val product_age_id: Int,
        val product_color_id: String,
        val product_descripion: String,
        val product_detail_id: Int,
        val product_id: Int,
        val product_image_id: Int,
        val product_name: String,
        val product_quantity_id: Int,
        val product_size_id: Int,
        val quantity: String,
        val sale_price: String,
        val season_id: Any,
        val size: String,
        val status: Int,
        val store_id: Any,
        val tax: String,
        val tax_type: Int,
        val theme_id: Any,
        val updated_at: String
    )


}