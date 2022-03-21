package com.shoparty.android.ui.search

data class SearchRequestModel(
    val offset: String = "",
    val limit: String? = "",
    val language_id: String = "",
    val search_data: String = "",
    val user_id: String = "",
    val device_type: String = "",
    val device_token: String = ""
)