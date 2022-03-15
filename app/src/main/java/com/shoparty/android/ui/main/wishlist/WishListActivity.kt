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
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.ui.shoppingbag.ShopingBagActivity
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class WishListActivity : AppCompatActivity(), View.OnClickListener, RecyclerViewClickListener {
    private lateinit var binding: ActivityWishListBinding
    private lateinit var viewModel: WishListViewModel
    private lateinit var adapterWishlist: WishListAdapters
    private var listWishlistt: ArrayList<WishListResponse.Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wish_list)
        initialise()
        viewModel = ViewModelProvider(this, ViewModalFactory(this.application!!))[WishListViewModel::class.java]

        binding.ivNoData.visibility=View.VISIBLE
        binding.tvNoData.visibility=View.VISIBLE
        //   viewModel.getWishlist("1")    //api call
        setObserver()
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
                val intent = Intent(this, ShopingBagActivity::class.java)
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
                    listWishlistt.clear()
                    listWishlistt=response.data!! as ArrayList<WishListResponse.Data>
                    setWishListAdapter(listWishlistt)
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.removewishlist.observe(this) { response ->
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
    }


    private fun setWishListAdapter(data: List<WishListResponse.Data>?)
    {
        adapterWishlist = WishListAdapters(this,data!!,this)
        binding.wishlistRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.wishlistRecyclerview.adapter = adapterWishlist
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun click(product_id: String)
    {
        viewModel.removeWishlist(product_id,"0")                    //api call
    }
}