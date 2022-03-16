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
        val arrival_response: List<Arrival>,
        val brand_response: List<Brand>,
        ) {
        data class Banner(
            val id: String,
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
        data class Arrival(
            val arrival_id: String,
            val arrival_name: String? = "",
            val arrival_description: String?="",
            val price: String?="",
            val arrival_image: String
        )
    }


}