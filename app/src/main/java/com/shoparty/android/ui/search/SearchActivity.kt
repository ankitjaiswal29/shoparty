package com.shoparty.android.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.common_modal.Product
import com.shoparty.android.databinding.ActivitySearchBinding
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private val list: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        viewModel =
            ViewModelProvider(this, ViewModalFactory(application)).get(SearchViewModel::class.java)

        initialise()
    }

    private fun initialise() {
        binding.infoTool.ivDrawerBack.setOnClickListener {
            onBackPressed()
        }
        binding.infoTool.tvTitle.setText(getString(R.string.Search))

        searchHistoryAdapter = SearchHistoryAdapter(this@SearchActivity, list)
        val gridLayoutManager = GridLayoutManager(this, 1)
        binding.searchHistoryListRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = searchHistoryAdapter
        }
        setObserver()
        binding.etSearch.addTextChangedListener {
            if (!it.isNullOrBlank()) {
                if (it.length >= 3) {
                    val requestModel = SearchRequestModel(
                        "0",
                        "100",
                        "1",
                        binding.etSearch.text.toString().trim(),
                        PrefManager.read(PrefManager.USER_ID, ""),
                        Constants.DEVICE_TYPE,
                        Constants.DEVICE_TOKEN
                    )
                    viewModel.searchProduct(requestModel)
                }
            }
        }


    }

    private fun setObserver() {
        viewModel.productList.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    list.clear()
                    list.addAll(response.data!!)
                    searchHistoryAdapter.notifyDataSetChanged()
                }

                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}