package com.shoparty.android.ui.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.common_modal.Category
import com.shoparty.android.common_modal.Product
import com.shoparty.android.database.MyDatabase
import com.shoparty.android.databinding.ActivitySearchBinding
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.ui.main.product_list.ProductListActivity
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchHistoryProductAdapter: SearchHistoryProductAdapter
    private lateinit var searchHistoryCategoryAdapter: SearchHistoryCategoryAdapter
    private val listproduct: ArrayList<Product> = ArrayList()
    private val listcategory: ArrayList<Category> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.mainLayoutSearch.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.infoTool.ivDrawerBack.rotation = 0F
        }else {
            binding.mainLayoutSearch.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.infoTool.ivDrawerBack.rotation = 180F
        }

        viewModel =
            ViewModelProvider(this, ViewModalFactory(application))[SearchViewModel::class.java]
        initialise()
        setObserver()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initialise() {
        binding.infoTool.ivDrawerBack.setOnClickListener {
            onBackPressed()
        }
        binding.infoTool.tvTitle.text = getString(R.string.Search)
        setSeachProductAdapter()
        setSeachCategoryAdapter()

        searchHistoryProductAdapter.onItemClick(object : RVItemClickListener {
            override fun onClick(pos: String, view: View?) {
                lifecycleScope.launch(Dispatchers.IO) {
                    MyDatabase.getInstance(this@SearchActivity).getProductDao()
                        .insertProduct(listproduct[pos.toInt()])
                }
                val intent = Intent(this@SearchActivity, ProductDetailsActivity::class.java)
                intent.putExtra(Constants.IDPRODUCT,listproduct[pos.toInt()].product_id.toString())
                intent.putExtra(Constants.PRODUCATDETAILSID,listproduct[pos.toInt()].product_detail_id.toString())
                intent.putExtra(Constants.PRODUCATNAME,listproduct[pos.toInt()].product_name.toString())
                intent.putExtra(Constants.PRODUCTSIZEID,listproduct[pos.toInt()].product_size_id.toString())
                intent.putExtra(Constants.PRODUCTCOLORID,listproduct[pos.toInt()].product_color_id.toString())
                startActivity(intent)
            }
        })

        searchHistoryCategoryAdapter.onItemClick(object : RVItemClickListener {
            override fun onClick(pos: String, view: View?) {
                lifecycleScope.launch(Dispatchers.IO) {
                    MyDatabase.getInstance(this@SearchActivity).getProductDao()
                        .insertCategory(listcategory[pos.toInt()])
                }
                val intent = Intent(this@SearchActivity, ProductListActivity::class.java)
                intent.putExtra(Constants.PRODUCTID,listcategory[pos.toInt()].category_id.toString())
                intent.putExtra(Constants.CATEGORYNAME,listcategory[pos.toInt()].category_name)
                startActivity(intent)
            }
        })


        binding.etSearch.addTextChangedListener {
            if (!it.isNullOrBlank()) {
                if (it.length >= 3) {
                    val requestModel = SearchRequestModel(
                        "0",
                        "100", PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),
                        binding.etSearch.text.toString().trim(),
                        PrefManager.read(PrefManager.USER_ID, ""),
                        Constants.DEVICE_TYPE,
                        PrefManager.read(PrefManager.DEVICETOKEN, "")
                    )
                    viewModel.searchProduct(requestModel)
                }
            }
        }

        binding.tvClear.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                MyDatabase.getInstance(this@SearchActivity).getProductDao()
                    .deleteAllProduct()
                listproduct.clear()
                lifecycleScope.launch(Dispatchers.Main) {
                    searchHistoryProductAdapter.notifyDataSetChanged()
                }
            }
        }

    }

    private fun setSeachProductAdapter() {
        searchHistoryProductAdapter = SearchHistoryProductAdapter(this@SearchActivity, listproduct)
        val gridLayoutManager = GridLayoutManager(this, 1)
        binding.searchHistoryListRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = searchHistoryProductAdapter
        }
        lifecycleScope.launch(Dispatchers.IO) {
            listproduct.clear()
            val dbList = MyDatabase.getInstance(this@SearchActivity).getProductDao().getAllProduct()
            listproduct.addAll(dbList)
            lifecycleScope.launch(Dispatchers.Main) {
                searchHistoryProductAdapter.notifyDataSetChanged()
            }

        }

    }


    private fun setSeachCategoryAdapter()
    {
        searchHistoryCategoryAdapter = SearchHistoryCategoryAdapter(this@SearchActivity, listcategory)
        val gridLayoutManager = GridLayoutManager(this, 1)
        binding.searchCategoryRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = searchHistoryCategoryAdapter
        }

        lifecycleScope.launch(Dispatchers.IO) {
            listcategory.clear()
            val dbList = MyDatabase.getInstance(this@SearchActivity).getProductDao().getAllCategory()
            listcategory.addAll(dbList)
            lifecycleScope.launch(Dispatchers.Main) {
                searchHistoryCategoryAdapter.notifyDataSetChanged()
            }

        }

    }

    private fun setObserver()
    {
        viewModel.productList.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                  //  ProgressDialog.hideProgressBar()
                        listproduct.clear()
                        listcategory.clear()
                        listproduct.addAll(response.data!!.products)
                        listcategory.addAll(response.data!!.categories)
                       if(!listproduct.isNullOrEmpty())
                        {
                            searchHistoryProductAdapter.notifyDataSetChanged()
                        }
                        else
                        {
                            binding.searchHistoryListRecycler.visibility=View.GONE
                        }
                        if(!listcategory.isNullOrEmpty())
                        {
                            searchHistoryCategoryAdapter.notifyDataSetChanged()
                        }
                        else
                        {
                            binding.searchCategoryRecycler.visibility=View.GONE
                        }
                }

                is Resource.Loading -> {
                  //  ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                 //   ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                  //  ProgressDialog.hideProgressBar()
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