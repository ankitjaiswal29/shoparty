package com.shoparty.android.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityFilterBinding
import com.shoparty.android.databinding.ActivitySearchBinding

import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_search)
        initialise()

    }

    private fun initialise() {
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.infoTool.tvTitle.setText(getString(R.string.Search))
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

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.iv_drawer_back->{
                onBackPressed()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}