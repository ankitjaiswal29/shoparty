package com.example.shoparty_android.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.HomeSeasonsAdapter
import com.example.shoparty_android.adapter.SearchHistoryAdapter
import com.example.shoparty_android.model.HomeCategoriesModel
import com.example.shoparty_android.model.SearchHistoryModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_home.*

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