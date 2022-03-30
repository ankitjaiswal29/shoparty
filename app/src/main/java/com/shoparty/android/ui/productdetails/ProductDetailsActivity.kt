package com.shoparty.android.ui.productdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.InputType
import android.text.Layout
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.database.MyDatabase
import com.shoparty.android.databinding.ActivityProductDetailsBinding
import com.shoparty.android.ui.customize.CustomizeActivity
import com.shoparty.android.ui.filter.FilterColorAdapter
import com.shoparty.android.ui.main.home.HomeResponse
import com.shoparty.android.ui.main.home.MySliderImageAdapter
import com.shoparty.android.ui.main.wishlist.WishListViewModel
import com.shoparty.android.ui.shoppingbag.ShoppingBagActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProductDetailsBinding
    var product_id = ""
    var product_details_id = ""
    var fav_status = ""
    private lateinit var viewModel: ProducatDetailsViewModel
    private lateinit var wishlistviewModel: WishListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        viewModel = ViewModelProvider(this, ViewModalFactory(application))[ProducatDetailsViewModel::class.java]
        wishlistviewModel = ViewModelProvider(this, ViewModalFactory(application))[WishListViewModel::class.java]
        initialise()
        setObserver()
    }

    @SuppressLint("SetTextI18n")
    private fun initialise() {
        binding.infoTool.ivBagBtn.visibility = View.VISIBLE
        binding.btnCostomizeit.setOnClickListener(this)
        binding.tvAddtobag.setOnClickListener(this)
        binding.infoTool.ivBagBtn.setOnClickListener(this)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.tvWishlist.setOnClickListener(this)
        binding.tvReadmore.setOnClickListener(this)
        if(intent.extras != null)
        {
            binding.infoTool.tvTitle.text= intent.getStringExtra(Constants.PRODUCATNAME)?.substring(0, 1)?.toUpperCase() +
                    intent.getStringExtra(Constants.PRODUCATNAME)?.substring(1)?.toLowerCase()
            product_id= intent.getStringExtra(Constants.IDPRODUCT)!!
            product_details_id= intent.getStringExtra(Constants.PRODUCATDETAILSID)!!
            viewModel.postProducatDetails("1",product_details_id,product_id) //api call
        }
        choesColorRecyclaritem()
    }


    private fun choesColorRecyclaritem()
    {
        val data = ArrayList<String>()
        data.add("#FFBB86FC")
        data.add("#606060")
        data.add("#FFBB86FC")
        data.add("#FFBB86FC")
        data.add("#E30986")


        val gridLayoutManager = GridLayoutManager(this, 9)
        binding.rvColorrecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterColorAdapter(data)
        }

    }

    private fun setrecyclaryoumayalsolike(productDetailList: List<ProducatDetailsResponse.ProductDetailList>)
    {
        binding.rvProductdetailsRecyclarview.adapter = ProductdetailsAdapter(this,productDetailList)
    }

    private fun recyclarcustomeralsobought(productDetailList: List<ProducatDetailsResponse.ProductDetailList>) {
        binding.rvProductdetailsRecyclarview2.adapter = ProductdetailsAdapter(this,productDetailList)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
            R.id.btn_costomizeit -> {
                val intent = Intent(this, CustomizeActivity::class.java)
                startActivity(intent)
                binding.tvAddtobag.setOnClickListener(this)
                binding.infoTool.ivBagBtn.setOnClickListener(this)
                binding.infoTool.ivDrawerBack.setOnClickListener(this)
            }
            R.id.tv_addtobag -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                        .insertCartProduct(CartProduct("Asd", 1, "", "1"))

                    MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                        .insertCartProduct(CartProduct("Asd", 2, "", "1"))
                    val intent = Intent(this@ProductDetailsActivity, ShoppingBagActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.ivBagBtn -> {
                val intent = Intent(this, ShoppingBagActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_drawer_back -> {
                onBackPressed()
            }

            R.id.tv_wishlist -> {
                if(fav_status == "0")
                {
                    wishlistviewModel.addremoveWishlist(product_id,1,product_details_id.toInt())
                    fav_status="1"
                }
                else
                {
                    wishlistviewModel.addremoveWishlist(product_id,0,product_details_id.toInt())
                    fav_status="0"
                }
            }


            R.id.tv_readmore -> {
                if(binding.tvReadmore.text.equals(getString(R.string.str_hideless)))
                {
                    binding.tvProductDetailsDescr.ellipsize =TextUtils.TruncateAt.END
                    binding.tvProductDetailsDescr.maxLines = 2
                    binding.tvReadmore.text=getString(R.string.read_more_withforword)
                }
                else
                {
                    binding.tvProductDetailsDescr.ellipsize = null
                    binding.tvProductDetailsDescr.isElegantTextHeight = true
                    binding.tvProductDetailsDescr.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE;
                    binding.tvProductDetailsDescr.isSingleLine = false
                    binding.tvReadmore.text=getString(R.string.str_hideless)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setObserver()
    {
        viewModel.product.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    setImageInSlider(response.data?.product_details?.images!!)
                    setData(response.data.product_details)
                    setrecyclaryoumayalsolike(response.data?.you_may_also_like)
                    recyclarcustomeralsobought(response.data?.also_bought)
                    checkReadMore(response.data.product_details.product_desc)
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

        wishlistviewModel.addremovewishlist.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    //  ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    if(fav_status=="0")
                    {
                        binding.imgEmptyHeart.visibility=View.VISIBLE
                        binding.imgFillHeart.visibility=View.GONE
                    }
                    else
                    {
                        binding.imgEmptyHeart.visibility=View.GONE
                        binding.imgFillHeart.visibility=View.VISIBLE
                    }
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

    private fun checkReadMore(productDesc: String)
    {
        binding.tvProductDetailsDescr.text=productDesc
        val l: Layout = binding.tvProductDetailsDescr.layout
        if (l != null)
        {
            val lines: Int = l.lineCount
            if (lines > 0)
                if (l.getEllipsisCount(lines - 1) > 0)
                {
                    binding.tvReadmore.visibility=View.VISIBLE

                }
                else
                {
                    binding.tvReadmore.visibility=View.GONE
                }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(data: ProducatDetailsResponse.ProductDetails)
    {
        if(data.fav_status==0)
        {
            binding.imgEmptyHeart.visibility=View.VISIBLE
            binding.imgFillHeart.visibility=View.GONE
            fav_status="0"
        }
        else
        {
            binding.imgEmptyHeart.visibility=View.GONE
            binding.imgFillHeart.visibility=View.VISIBLE
            fav_status="1"
        }
        binding.tvProductTitle.text=data.product_name
        if(data.cost_price.toDouble()>data.sale_price.toDouble())
        {
            binding.tvProductCostPrice.visibility=View.VISIBLE
            binding.tvProductCostPrice.text=getString(R.string.dollor)+data.cost_price
            binding.tvProductCostPrice.paintFlags =  binding.tvProductCostPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        else
        {
            binding.tvProductCostPrice.visibility=View.GONE
            binding.tvProductPrice.text=getString(R.string.dollor)+data.sale_price
        }

        if(data.is_customizable==0)
        {
           binding.btnCostomizeit.visibility=View.GONE
        }
        else
        {
            binding.btnCostomizeit.visibility=View.VISIBLE
        }

    }

    private fun setImageInSlider(images: List<HomeResponse.Home.Banner>) {
        val adapter = MySliderImageAdapter(this)
        adapter.renewItems(images as ArrayList<HomeResponse.Home.Banner>)
        binding.imageSliderr.setSliderAdapter(adapter)
        binding.imageSliderr.isAutoCycle = true
        binding.imageSliderr.startAutoCycle()
    }
}