package com.shoparty.android.ui.main.home

data class HomeResponse(
    val message: String,
    val response_code: Int,
    val result: Home
) {
    data class Home(
        val top_banner: List<TopBanner>,
        val bottom_banner: List<BottomBanner>
    )

    data class TopBanner(
        val id: String,
        val image: String
    )

    data class BottomBanner(
        val id: String,
        val image: String
    )
}