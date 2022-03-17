package com.shoparty.android.ui.main.deals

/**
 * Created by Amit Gupta on 16-03-2022.
 */
data class DealsResponse(
    val message: String,
    val response_code: Int,
    val result: List<Deals>
) {
    data class Deals(
        val language_id: String,
        val offset: String,
        val limit: String,
    )
}
