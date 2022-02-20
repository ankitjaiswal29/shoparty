package com.shoparty.android.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shoparty.android.R

import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        fillSearchHistoryRecyclerView(searchHistoryList)
    }

    private val searchHistoryList = listOf<SearchHistoryModel>(
        SearchHistoryModel("Birthday cap"),
        SearchHistoryModel("Part Caps"),
        SearchHistoryModel("Part Cap"),
        SearchHistoryModel("Ballons"),

        )

    private fun fillSearchHistoryRecyclerView(searchList: List<SearchHistoryModel>) {
        search_history_list_recycler.adapter = SearchHistoryAdapter(searchHistoryList)
    }
}