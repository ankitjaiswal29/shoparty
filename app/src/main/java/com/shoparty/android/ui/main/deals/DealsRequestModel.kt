package com.shoparty.android.ui.main.deals

import com.google.gson.JsonObject


data class DealsRequestModel(
    val language_id: String = "",
    val offset: String = "",
    val limit: String = "",
    val user_id: String = "",
    val filter_applied: String="",
    val filter: String="",
)