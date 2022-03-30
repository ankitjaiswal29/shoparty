package com.shoparty.android.ui.main.product_list
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shoparty.android.R
import com.shoparty.android.common_modal.Product
import com.shoparty.android.databinding.ActivityTopSellingBinding
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.ui.filter.FilterActivity
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.deals.EndlessRecyclerViewScrollListener
import com.shoparty.android.ui.main.wishlist.WishListViewModel
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.ui.shoppingbag.ShoppingBagActivity

import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class ProductListActivity : AppCompatActivity(),
    View.OnClickListener,
    RecyclerViewFavouriteListener {
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var binding: ActivityTopSellingBinding
    private lateinit var viewModel: ProductListViewModel
    private lateinit var viewModeladdwishlist: WishListViewModel
    private var newproductlist: ArrayList<Product> = ArrayList()
    lateinit var dialog: BottomSheetDialog
    var color = false
    var size = false
    var age = false
    var gender = false
    var viewall_status = ""
    var pageOffset=0
    var pageLimit=6
    private lateinit var adapter:ProductListAdapters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_selling)
        initialise()
        withpaginationAdapterSet()

        if(intent.extras != null)
        {
            if(intent.getStringExtra(Constants.CATEGORYFRAGMENT).equals("1"))
            {
                binding.infoTool.tvTitle.text = intent.getStringExtra("categoryname")
                productListApi()
            }
            else if (intent.getStringExtra(Constants.TOP20SELLING).equals("2"))  //top20selling
            {
                binding.infoTool.tvTitle.text = getString(R.string.top_20_selling_items)
                viewall_status = "1"
                setupPaginationRecylarview()
                viewAllApi("1") //api call
            }

            else if (intent.getStringExtra(Constants.DRAWERSUBCATEGORY).equals("3"))  //drawer page
            {
                binding.infoTool.tvTitle.text = intent.getStringExtra(Constants.CATEGORYNAME)
                productListApi()
            }
        }
        setObserver()
    }

    private fun initialise()
    {
        viewModel =
            ViewModelProvider(this, ViewModalFactory(application))[ProductListViewModel::class.java]
        viewModeladdwishlist =
            ViewModelProvider(this, ViewModalFactory(application))[WishListViewModel::class.java]

        binding.infoTool.ivBagBtn.visibility = View.VISIBLE
        binding.infoTool.ivBtnsearch.visibility = View.VISIBLE
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.infoTool.ivBagBtn.setOnClickListener(this)
        binding.infoTool.ivBtnsearch.setOnClickListener(this)
        binding.tvFilter.setOnClickListener(this)
        binding.tvSort.setOnClickListener(this)
    }


    private fun withpaginationAdapterSet()
    {
        layoutManager = GridLayoutManager(this,2)
        binding. dealsItemRecycler.layoutManager = layoutManager
        adapter = ProductListAdapters(this, newproductlist,this)
        binding.dealsItemRecycler.adapter = adapter
    }


    private fun withoutPaginationAdapterSet(arrayList: ArrayList<Product>)
    {
        layoutManager = GridLayoutManager(this,2)
        binding. dealsItemRecycler.layoutManager = layoutManager
        adapter = ProductListAdapters(this, arrayList,this)
        binding.dealsItemRecycler.adapter = adapter
    }

    private fun setObserver() {
        viewModel.productList.observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    val productlist = response.data as ArrayList<Product>

                    if(productlist.isNullOrEmpty() && newproductlist.isNullOrEmpty())
                    {
                        binding.ivNoData.visibility = View.VISIBLE
                        binding.tvNoData.visibility = View.VISIBLE
                        binding.dealsItemRecycler.visibility = View.GONE
                    }
                    else
                    {
                        binding.ivNoData.visibility = View.GONE
                        binding.dealsItemRecycler.visibility = View.VISIBLE
                        binding.tvNoData.visibility = View.GONE
                        if(viewall_status == "1")
                        {
                            setupData(productlist)
                        }
                        else
                        {
                            withoutPaginationAdapterSet(response.data as ArrayList<Product>)
                        }
                    }
                }
                is Resource.Loading -> {
                    com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        viewModeladdwishlist.addremovewishlist.observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                    //  com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    if (viewall_status == "1")     //pagination
                    {
                        newproductlist.clear()
                        viewAllApi("1")        //api call
                    }

                    else
                    {
                        productListApi()   //api call
                    }
                }
                is Resource.Loading -> {
                    //   com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    //  com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    //  com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })


    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
            R.id.tv_sort -> {
                showBottomsheetDialog()
            }
            R.id.tv_filter -> {
                val intent = Intent(this, FilterActivity::class.java)
                startActivity(intent)
            }
            R.id.ivBagBtn -> {
                val intent = Intent(this, ShoppingBagActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_btnsearch -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun viewAllApi(type: String) {
        viewModel.topSellingProducatList(
            "1",
            type,
            pageOffset.toString(),pageLimit.toString(),
            PrefManager.read(PrefManager.USER_ID, ""))           //api call
    }
        private fun productListApi() {
            viewModel.producatList(
                intent.getStringExtra(Constants.PRODUCTID).toString(), "3",
                "1", PrefManager.read(PrefManager.USER_ID, "")
            )   //api call
        }


        private fun showBottomsheetDialog() {
            val view = layoutInflater.inflate(R.layout.top_selling_bottomsheet_layout, null)
            dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
            val recyclerView =
                view.findViewById<RecyclerView>(R.id.rv_top_selling_bottomsheetrecyclar)
            recyclerView.layoutManager = LinearLayoutManager(this)
            val data = ArrayList<String>()
            data.add("Newest To Oldest")
            data.add("Oldest To Newest")
            data.add("Price - Low To High")
            data.add("Price - High To Low")
            val adapter = ProductListSortingBottomSheetAdapter(data)
            recyclerView.adapter = adapter
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()


        }


    override fun onBackPressed() {
        super.onBackPressed()
    }


    override fun favourite(producat_id: String, type: String, product_detail_id: String) {
        if (PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        else
        {
            viewModeladdwishlist.addremoveWishlist(
                producat_id,
                type.toInt(),
                product_detail_id.toInt())
        }
    }


    private fun setupData(mproductlist: ArrayList<Product>)
    {
        mproductlist.let {
            newproductlist.addAll(it)
        }
        if(newproductlist.size>0)
        {
            var newList = newproductlist.distinctBy { it.id}
            adapter.updateItems(newList as ArrayList<Product>)
            adapter.notifyDataSetChanged()
            //   binding.rvContestLeaderBoard.visibility = View.VISIBLE
            //   binding.noResult.visibility = View.GONE
        }else{
            //   binding.rvContestLeaderBoard.visibility = View.GONE
            //   binding.noResult.visibility = View.VISIBLE
        }
    }

    private fun setupPaginationRecylarview()
    {
        binding.dealsItemRecycler.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                pageOffset++
                viewAllApi("1")  //api call
               /*  if (listSize > productlist.size) {
                }*/
            }
        })
    }

    }



