package com.shoparty.android.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivitySearchBinding
import kotlinx.android.synthetic.main.activity_search.*
import android.text.Editable

import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener


class SearchActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        initialise()

    }

    private fun initialise() {
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.infoTool.tvTitle.setText(getString(R.string.Search))
        fillSearchHistoryRecyclerView(searchHistoryList)
        binding.etEdittextsearch.addTextChangedListener {
            searchBasedOnFAQ(it.toString())
        }
    }
    private fun searchBasedOnFAQ(searchText: String) {
        val filterDataList = searchHistoryList.filter {
            it.name.contains(searchText.toString(), true)
        }
        searchHistoryAdapter.setDataList(filterDataList);
    }

    private val searchHistoryList = listOf<SearchHistoryModel>(
        SearchHistoryModel("Birthday cap"),
        SearchHistoryModel("Part Caps"),
        SearchHistoryModel("Part Cap"),
        SearchHistoryModel("Ballons"),

        )

    private fun fillSearchHistoryRecyclerView(searchList: List<SearchHistoryModel>) {

        searchHistoryAdapter = SearchHistoryAdapter(searchHistoryList)
        search_history_list_recycler.adapter=searchHistoryAdapter
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}