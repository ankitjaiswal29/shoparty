package com.shoparty.android.ui.main.topselling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mohammedalaa.seekbar.DoubleValueSeekBarView
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityTopSellingBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.interfaces.RecyclerViewItemClickListener

import com.shoparty.android.ui.filter.*
import com.shoparty.android.ui.main.deals.TopSellingHomeModel
import com.shoparty.android.ui.main.wishlist.WishListViewModel

import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.ui.shoppingbag.ShopingBagActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.SpacesItemDecoration
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.android.synthetic.main.bottomsheet_filter_layout.view.*
import kotlinx.android.synthetic.main.fragment_deals.*

class TopSellingActivity : AppCompatActivity(), View.OnClickListener,RecyclerViewClickListener,RecyclerViewItemClickListener,RecyclerViewFavouriteListener {
    private lateinit var binding: ActivityTopSellingBinding
    private lateinit var viewModel: ProductListViewModel
    private lateinit var viewModeladdwishlist: WishListViewModel
    private var productlist: ArrayList<ProductListResponse.ProductList> = ArrayList()
    lateinit var dialog:BottomSheetDialog
    var color=false
    var size=false
    var age=false
    var gender=false
    var price=false
    private var recyvlerviewItemList=ArrayList<RecyclerView>()
    private var filterIconItem=ArrayList<TextView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_top_selling)
        viewModel = ViewModelProvider(this, ViewModalFactory(application))[ProductListViewModel::class.java]
        viewModeladdwishlist = ViewModelProvider(this, ViewModalFactory(application))[WishListViewModel::class.java]

        if(intent.extras != null)
        {
            if(intent.getStringExtra(Constants.CATEGORYFRAGMENT).equals("1"))
            {
                binding.infoTool.tvTitle.text=intent.getStringExtra("categoryname")
                viewModel.myOrders(intent.getStringExtra(Constants.PRODUCTID).toString())   //api call
            }

            else if(intent.getStringExtra(Constants.TOP20SELLING).equals("2"))  //top20selling
            {
                binding.infoTool.tvTitle.text=getString(R.string.top_20_selling_items)
            //    viewModel.myOrders(intent.getStringExtra(Constants.PRODUCTID).toString())  //api call
            }
        }
        initialise()
        setObserver()

    }
    private fun initialise() {
        binding.infoTool.ivBagBtn.visibility=View.VISIBLE
        binding.infoTool.ivBtnsearch.visibility=View.VISIBLE
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.infoTool.ivBagBtn.setOnClickListener(this)
        binding.infoTool.ivBtnsearch.setOnClickListener(this)
        binding.tvFilter.setOnClickListener(this)
        binding.tvSort.setOnClickListener(this)
    }

    private fun setObserver() {
        viewModel.productList.observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()

                    if(response.data.isNullOrEmpty())
                    {
                      //  binding.clNoData.visibility=View.VISIBLE
                       // binding.myorderRecyclerview.visibility=View.GONE
                    }
                    else
                    {
                        productlist = response.data as ArrayList<ProductListResponse.ProductList>
                        setProductListAdapter(productlist)
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
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()

                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
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


    }
    private fun setProductListAdapter(data: ArrayList<ProductListResponse.ProductList>) {

        val gridLayoutManager = GridLayoutManager(this, 2)
        deals_item_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = ProductListAdapters(this@TopSellingActivity,data!!,this@TopSellingActivity,this@TopSellingActivity)
        }

    }
    override fun onClick(v: View?) {
        when(v?.id){
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
                val intent = Intent(this, ShopingBagActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_btnsearch -> {
                val intent = Intent (this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }






    private fun showBottomsheetDialog() {
        val view = layoutInflater.inflate(R.layout.top_selling_bottomsheet_layout, null)
        dialog = BottomSheetDialog(this,R.style.BottomSheetDialog)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_top_selling_bottomsheetrecyclar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<String>()
        data.add("Newest To Oldest")
        data.add("Oldest To Newest")
        data.add("Price - Low To High")
        data.add("Price - High To Low")
        val adapter=TopSellingBottomSheetAdapter(data,this)
        recyclerView.adapter=adapter
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()


    }

    private fun Topsellingitem() {
        val naItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),

            )

        val gridLayoutManager = GridLayoutManager(this, 2)
        deals_item_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = TopSellingDemoAdapter(naItemList,this@TopSellingActivity)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun click(pos: String) {
        Toast.makeText(this,pos,Toast.LENGTH_LONG).show()
        val intent = Intent (this, ProductDetailsActivity::class.java)
        startActivity(intent)
    }


    override fun onClick(pos: String, view: View?) {
        val intent = Intent (this, ProductDetailsActivity::class.java)
        startActivity(intent)
    }

    override fun favourite(product_id: String, status: String)
    {
        viewModeladdwishlist.addremoveWishlist(product_id,status.toInt())
    }

}