package com.shoparty.android.ui.search

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
data class SearchProductHistory(
    val en_description: String,
    val en_name: String,
    val id: Int,
)