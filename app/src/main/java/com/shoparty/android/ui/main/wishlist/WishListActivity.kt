package com.shoparty.android.ui.main.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityWishListBinding
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.interfaces.WishListAddBagClickListener
import com.shoparty.android.ui.productdetails.ProducatDetailsViewModel
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.ui.shoppingbag.ShoppingBagActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import okhttp3.MultipartBody

class WishListActivity : AppCompatActivity(), View.OnClickListener,
    RecyclerViewFavouriteListener, WishListAddBagClickListener {
    private lateinit var binding: ActivityWishListBinding
    private lateinit var viewModel: WishListViewModel
    private lateinit var adapterWishlist: WishListAdapters
    private lateinit var productdetailsViewModel: ProducatDetailsViewModel
    var quantity: Int = 0
    private var position: Int = 0
    private var action_type: Int = 0
    private var listWishlistt: ArrayList<WishListResponse.Result> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wish_list)
        initialise()
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(this.application!!)
        )[WishListViewModel::class.java]
        productdetailsViewModel = ViewModelProvider(
            this,
            ViewModalFactory(this.application!!)
        )[ProducatDetailsViewModel::class.java]
        setObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWishlist(PrefManager.read(PrefManager.LANGUAGEID, 1).toString())//api call
    }

    private fun initialise() {
        binding.infoTool.tvTitle.text = getString(R.string.wishlist)
        binding.infoTool.ivBagBtn.visibility = View.VISIBLE
        binding.infoTool.ivBtnsearch.visibility = View.VISIBLE
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.infoTool.ivBagBtn.setOnClickListener(this)
        binding.infoTool.ivBtnsearch.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
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

    private fun setObserver() {
        viewModel.wishlist.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    listWishlistt = response.data!! as ArrayList<WishListResponse.Result>
                    if (listWishlistt.isNullOrEmpty()) {
                        binding.ivNoData.visibility = View.VISIBLE
                        binding.tvNoData.visibility = View.VISIBLE
                        binding.wishlistRecyclerview.visibility = View.GONE
                    } else {
                        binding.ivNoData.visibility = View.GONE
                        binding.tvNoData.visibility = View.GONE
                        binding.wishlistRecyclerview.visibility = View.VISIBLE
                        setWishListAdapter(listWishlistt)
                    }
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

        viewModel.addremovewishlist.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    //  ProgressDialog.hideProgressBar()
                    viewModel.getWishlist("1")    //api call
                }
                is Resource.Loading -> {
                    //   ProgressDialog.showProgressBar(requireContext())
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
                    //    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        productdetailsViewModel.addbag.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    adapterWishlist.updateData(position, quantity)
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

    private fun setWishListAdapter(data: List<WishListResponse.Result>?) {
        adapterWishlist = WishListAdapters(this, data!!, this, this)
        binding.wishlistRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.wishlistRecyclerview.adapter = adapterWishlist
    }

    override fun favourite(
        position: Int,
        producat_id: String,
        type: String,
        product_detail_id: String,
        product_sizeId: String,
        product_colorId: String
    ) {
        viewModel.addremoveWishlist(
            producat_id,
            type.toInt(),
            product_detail_id.toInt(),
            product_sizeId,
            product_colorId
        )
    }

    override fun addBagClick(pos: Int, actiontype: Int) {
        if (PrefManager.read(PrefManager.AUTH_TOKEN, "") == "") {
            /* lifecycleScope.launch(Dispatchers.IO) {
                 MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                     .insertCartProduct(CartProduct(productDetails.en_name, productDetails.id.toString(), productDetails.images[0].image.toString(), "1"))

                 MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                     .insertCartProduct(CartProduct(productDetails.en_name, productDetails.id.toString(), productDetails.images[0].image.toString(), "1"))

                 val intent = Intent(this@ProductDetailsActivity, ShoppingBagActivity::class.java)
                 startActivity(intent)
             }*/
        }
        else {
            position = pos
            action_type = actiontype
            quantity = listWishlistt[pos].cart_quantity!!

            if(action_type == Constants.CARTACTIONMINUSTYPE)   //for minus
            {
                if(listWishlistt[pos].is_customizable == 1) {
                        val intent =
                            Intent(this@WishListActivity, ProductDetailsActivity::class.java)
                        intent.putExtra(
                            Constants.IDPRODUCT,
                            listWishlistt[pos].product_id.toString()
                        )
                        intent.putExtra(
                            Constants.PRODUCATDETAILSID,
                            listWishlistt[pos].product_detail_id.toString()
                        )
                        intent.putExtra(Constants.PRODUCATNAME, listWishlistt[pos].product_name)
                        intent.putExtra(
                            Constants.PRODUCTSIZEID,
                            listWishlistt[pos].product_size_id.toString()
                        )
                        intent.putExtra(
                            Constants.PRODUCTCOLORID,
                            listWishlistt[pos].product_color_id.toString()
                        )
                        startActivity(intent)
                    }
                    else
                    {
                        quantity -= 1
                        if (listWishlistt[pos].cart_quantity != 0) {
                            addToBagApi(pos)
                        }
                    }
            }
            else
            {
                if(listWishlistt[pos].is_customizable == 1) {
                    val intent =
                        Intent(this@WishListActivity, ProductDetailsActivity::class.java)
                    intent.putExtra(
                        Constants.IDPRODUCT,
                        listWishlistt[pos].product_id.toString()
                    )
                    intent.putExtra(
                        Constants.PRODUCATDETAILSID,
                        listWishlistt[pos].product_detail_id.toString()
                    )
                    intent.putExtra(Constants.PRODUCATNAME, listWishlistt[pos].product_name)
                    intent.putExtra(
                        Constants.PRODUCTSIZEID,
                        listWishlistt[pos].product_size_id.toString()
                    )
                    intent.putExtra(
                        Constants.PRODUCTCOLORID,
                        listWishlistt[pos].product_color_id.toString()
                    )
                    startActivity(intent)
                }
                else
                {
                    quantity += 1
                    addToBagApi(pos)
                }
            }
        }

    }

    private fun addToBagApi(pos: Int) {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        if(listWishlistt[pos].is_customizable.toString() == "0")
        {
            builder.addFormDataPart(
                "customized_image",listWishlistt[pos].image)
        }
        else
        {
            builder.addFormDataPart(
                "customized_image",listWishlistt[pos].image)
        }
        builder.addFormDataPart("is_customizable", listWishlistt[pos].is_customizable.toString())
        builder.addFormDataPart("product_id", listWishlistt[pos].product_id.toString())
        builder.addFormDataPart(
            "product_detail_id",
            listWishlistt[pos].product_detail_id.toString()
        )
        builder.addFormDataPart("product_size_id", listWishlistt[pos].product_size_id.toString())
        builder.addFormDataPart("product_color_id", listWishlistt[pos].product_color_id.toString())
        builder.addFormDataPart("quantity", quantity.toString())
        builder.addFormDataPart("price", listWishlistt[pos].sale_price)
        val body = builder.build()
        productdetailsViewModel.postAddProduct(body)
    }
}