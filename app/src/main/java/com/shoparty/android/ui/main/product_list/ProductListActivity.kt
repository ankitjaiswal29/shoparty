package com.shoparty.android.ui.main.product_list

import android.app.Activity
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
import com.shoparty.android.databinding.ActivityProductListBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.ui.filter.FilterActivity
import com.shoparty.android.ui.filter.age.AgeRequest
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
    RecyclerViewFavouriteListener, RecyclerViewClickListener {
    private var sortclickName: String = ""
    private var bottomlist: ArrayList<String> = ArrayList()
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var binding: ActivityProductListBinding
    private lateinit var viewModel: ProductListViewModel
    private lateinit var viewModeladdwishlist: WishListViewModel
    private var productlist: ArrayList<Product> = ArrayList()
    private var newproductlist: ArrayList<Product> = ArrayList()
    lateinit var dialog: BottomSheetDialog
    var color = false
    var size = false
    var age = false
    var gender = false
    var progressshow = true
    var viewall_status = ""
    var pageOffset = 0
    var pageLimit = 100
    var fav_position: Int = 0
    var fav_type: Int = 0
    var filter_applied = 0
    var sort_applied = 0
    var sort_type = 0
    var category_id = ""
    var filter = ProductListRequestModel.Filter()
    private var selectedColorList:ArrayList<String> =  ArrayList()
    private var selectedSizeList:ArrayList<String> =  ArrayList()
    private var selectedAgeList:ArrayList<AgeRequest> =  ArrayList()
    private var selectedGenderList:ArrayList<String> =  ArrayList()
    private var selectedminprice = 0
    private var selectedmaxprice = 0
    private lateinit var adapter: ProductListAdapters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.clRootlayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.infoTool.ivDrawerBack.rotation = 0F
        }else {
            binding.clRootlayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.infoTool.ivDrawerBack.rotation = 180F
        }
        initialise()
        withpaginationAdapterSet(newproductlist)
        if (intent.extras != null)
        {
            if(intent.getStringExtra(Constants.TOP20SELLING).equals("2"))  //top20selling view all
            {
                binding.infoTool.tvTitle.text = getString(R.string.top_20_selling_items)
                viewall_status = "1"
                setupPaginationRecylarview()
                viewAllApi(
                    "1",
                    filter_applied.toString(),
                    ProductListRequestModel.Filter(),
                    sort_applied,
                    sort_type
                ) //api call
            }
            else
            {
                getCategoryId()
                binding.infoTool.tvTitle.text = intent.getStringExtra(Constants.CATEGORYNAME)
                productListApiCall(
                    PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),
                    filter_applied.toString(),
                    filter,
                    sort_applied,
                    sort_type)
            }
        }
        setObserver()
    }

    private fun getCategoryId()
    {
        category_id=intent.getStringExtra(Constants.PRODUCTID).toString()
        if(category_id.isNotEmpty())
        {
            filter_applied=1
            filter.category_id=category_id
        }
    }

    private fun initialise() {
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.clRootlayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.clRootlayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.clRootlayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }else {
            binding.clRootlayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.clRootlayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.clRootlayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }

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
        saveSortLocal()
    }

    private fun saveSortLocal() {
        PrefManager.write(PrefManager.SORTSELECTEDNAME, sortclickName)
    }


    private fun withpaginationAdapterSet(newList: List<Product>) {
        layoutManager = GridLayoutManager(this, 2)
        binding.dealsItemRecycler.layoutManager = layoutManager
        adapter = ProductListAdapters(this, newList as ArrayList<Product>, this)
        binding.dealsItemRecycler.adapter = adapter
    }


    private fun withoutPaginationAdapterSet(arrayList: ArrayList<Product>) {
        layoutManager = GridLayoutManager(this, 2)
        binding.dealsItemRecycler.layoutManager = layoutManager
        adapter = ProductListAdapters(this, arrayList, this)
        binding.dealsItemRecycler.adapter = adapter
    }

    private fun setObserver()
    {
        viewModel.productList.observe(this) { response ->
            when (response) {
                is Resource.Success ->
                {
                    if (progressshow) {
                        com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    }
                    progressshow = false
                    productlist = response.data as ArrayList<Product>
                    if(sort_applied != 0)     //sort applied
                    {
                        dialog.dismiss()         //update bottom sheet dialog
                        saveSortLocal()
                    }
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
                            withoutPaginationAdapterSet(response.data)
                        }
                    }
                }
                is Resource.Loading -> {
                    if (progressshow) {
                        com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                    }
                }
                is Resource.Error -> {
                    if (progressshow) {
                        com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    }
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
        }

        viewModeladdwishlist.addremovewishlist.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    if (viewall_status == "1")     //pagination
                    {
                        newproductlist[fav_position].fav_status = fav_type
                        adapter.notifyDataSetChanged()
                    } else {
                        productlist[fav_position].fav_status = fav_type
                        adapter.notifyDataSetChanged()
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
        }


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
                intent.putStringArrayListExtra("colorList",selectedColorList)
                intent.putParcelableArrayListExtra("ageList",selectedAgeList)
                intent.putStringArrayListExtra("genderList",selectedGenderList)
                intent.putStringArrayListExtra("sizeList",selectedSizeList)
                startActivityForResult(intent, 101)
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


    private fun viewAllApi(
        type: String,
        filter_applied: String,
        filterlist: ProductListRequestModel.Filter,
        sort_applied: Int,
        sort_type: Int)
    {
        viewModel.topSellingProducatList(
            PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),
            type,
            pageOffset.toString(), pageLimit.toString(),
            PrefManager.read(PrefManager.USER_ID, ""), filter_applied,
            filterlist, sort_applied, sort_type)
    }

    private fun productListApiCall(
        langauge_id: String,
        filter_applied: String,
        filterlist: ProductListRequestModel.Filter,
        sort_applied: Int,
        sort_type: Int) {
        viewModel.producatList(
            langauge_id,
            filter_applied,
            filterlist,
            sort_applied,
            sort_type
        )   //api call
    }


    private fun showBottomsheetDialog() {
        val view = layoutInflater.inflate(R.layout.top_selling_bottomsheet_layout, null)
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
        val recyclerView =
            view.findViewById<RecyclerView>(R.id.rv_top_selling_bottomsheetrecyclar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bottomlist = ArrayList<String>()
        bottomlist.add(getString(R.string.newest_to_oldest))
        bottomlist.add(getString(R.string.oldest_to_newest))
        bottomlist.add(getString(R.string.price_low_to_high))
        bottomlist.add(getString(R.string.price_high_to_low))
        val bottomsheetadapter = ProductListSortingBottomSheetAdapter(bottomlist, this)
        recyclerView.adapter = bottomsheetadapter
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }


    override fun favourite(
        position: Int,
        producat_id: String,
        type: String,
        product_detail_id: String,
        product_sizeId: String,
        product_colorId: String
    ) {
        fav_position = position
        fav_type = type.toInt()
        if (PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            viewModeladdwishlist.addremoveWishlist(
                producat_id,
                type.toInt(),
                product_detail_id.toInt(),
                product_sizeId,
                product_colorId
            )
        }
    }


    private fun setupData(mproductlist: ArrayList<Product>) {
        mproductlist.let {
            newproductlist.addAll(it)
        }
        if(newproductlist.size > 0) {
            val newList = newproductlist.distinctBy { it.id }
            withpaginationAdapterSet(newList)
            //adapter.updateItems(newList as ArrayList<Product>)
        }
        else { }
    }

    private fun setupPaginationRecylarview() {
        binding.dealsItemRecycler.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                pageOffset = newproductlist.size
                viewAllApi(
                    "1",
                    filter_applied.toString(),
                    ProductListRequestModel.Filter(),
                    sort_applied,
                    sort_type
                )  //api call
            }
        })
    }

    override fun click(pos: String)
    {
        sortclickName = bottomlist[pos.toInt()].toString()
        if (bottomlist[pos.toInt()] == getString(R.string.newest_to_oldest)) {
            sort_type = 1
        } else if (bottomlist[pos.toInt()] == getString(R.string.oldest_to_newest)) {
            sort_type = 2
        } else if (bottomlist[pos.toInt()] == getString(R.string.price_low_to_high)) {
            sort_type = 3
        } else if (bottomlist[pos.toInt()] == getString(R.string.price_high_to_low)) {
            sort_type = 4
        }
        sort_applied = 1
        progressshow = true
        newproductlist.clear()
        productlist.clear()
        pageOffset = 1
        if(intent.getStringExtra(Constants.TOP20SELLING).equals("2"))  //top20selling view all
        {
            binding.infoTool.tvTitle.text = getString(R.string.top_20_selling_items)
            viewall_status = "1"
            setupPaginationRecylarview()
            viewAllApi(
                "1",
                filter_applied.toString(),
                filter,
                sort_applied,
                sort_type) //api call
        }
        else
        {
            getCategoryId()
            binding.infoTool.tvTitle.text = intent.getStringExtra(Constants.CATEGORYNAME)
            productListApiCall(
                PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),
                filter_applied.toString(),filter, sort_applied, sort_type)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 101) {
            selectedColorList = data?.getStringArrayListExtra("colorList") as ArrayList<String>
            selectedAgeList = data?.getParcelableArrayListExtra<AgeRequest>("ageList") as ArrayList<AgeRequest>
            selectedSizeList = data?.getStringArrayListExtra("sizeList") as ArrayList<String>
            selectedGenderList = data?.getStringArrayListExtra("genderList") as ArrayList<String>
            selectedminprice = data?.getIntExtra("selectedminprice",0)!!
            selectedmaxprice = data?.getIntExtra("selectedmaxprice",0)!!

             filter = ProductListRequestModel.Filter()
            val priceObj = ProductListRequestModel.Filter.Price()
            filter.color.addAll( selectedColorList)
            filter.size.addAll( selectedSizeList)
            filter.age.addAll( selectedAgeList)
            filter.gender.addAll( selectedGenderList)
            filter_applied =1
            progressshow=true

            if(selectedmaxprice != 0) {
                priceObj.from = selectedminprice
                priceObj.to = selectedmaxprice
            }
            filter.price = priceObj
            newproductlist.clear()
            productlist.clear()
            pageOffset = 1

            if(intent.getStringExtra(Constants.TOP20SELLING).equals("2"))  //top20selling view all
            {
                binding.infoTool.tvTitle.text = getString(R.string.top_20_selling_items)
                viewall_status = "1"
                pageOffset=0
                setupPaginationRecylarview()
                viewAllApi(
                    "1",
                    filter_applied.toString(),
                    filter,
                    sort_applied,
                    sort_type) //api call
            }
            else
            {
                if(intent.extras!=null)
                {
                    getCategoryId()
                }
                binding.infoTool.tvTitle.text = intent.getStringExtra(Constants.CATEGORYNAME)
                productListApiCall(
                    PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),
                    filter_applied.toString(),filter, sort_applied, sort_type)
            }




        }
    }
}



