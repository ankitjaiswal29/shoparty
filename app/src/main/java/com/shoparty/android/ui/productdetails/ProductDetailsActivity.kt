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
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityProductDetailsBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.customize.CustomizeActivity
import com.shoparty.android.ui.main.home.HomeResponse
import com.shoparty.android.ui.main.home.MySliderImageAdapter
import com.shoparty.android.ui.main.wishlist.WishListViewModel
import com.shoparty.android.ui.shoppingbag.ShoppingBagActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class ProductDetailsActivity : AppCompatActivity(), View.OnClickListener,RecyclerViewClickListener,ProductDetailCallback {
    private var l: Layout?=null
    private lateinit var binding: ActivityProductDetailsBinding
    var product_id = ""
    var product_details_id = ""
    var product_sizeId = ""
    var product_colorId = ""
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
      //  binding.ivShare.setOnClickListener(this)

        if(intent.extras != null)
        {
            binding.infoTool.tvTitle.text= intent.getStringExtra(Constants.PRODUCATNAME)?.substring(0, 1)?.toUpperCase() +
                    intent.getStringExtra(Constants.PRODUCATNAME)?.substring(1)?.toLowerCase()
            product_id= intent.getStringExtra(Constants.IDPRODUCT)!!
            product_details_id= intent.getStringExtra(Constants.PRODUCATDETAILSID)!!
            product_sizeId= intent.getStringExtra(Constants.PRODUCTSIZEID)!!
            product_colorId= intent.getStringExtra(Constants.PRODUCTCOLORID)!!
            viewModel.postProducatDetails("1",product_details_id,product_id,product_sizeId,product_colorId) //api call
        }

        binding.ivShare.setOnClickListener {
            Utils.showLongToast(this,getString(R.string.comingsoon))
        }
    }


    private fun choesColorRecyclaritem(colors: List<ProducatDetailsResponse.Color>)
    {
        colors.forEachIndexed { pos,it->
            it.color_code = "#FFBB86FC"
            if(pos==0)
            {
                it.ischecked=true
            }
        }


        val gridLayoutManager = GridLayoutManager(this, 9)
        binding.rvColorrecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = ColorAdapter(this@ProductDetailsActivity,colors,this@ProductDetailsActivity)
        }

    }

    private fun setrecyclaryoumayalsolike(productDetailList: List<ProducatDetailsResponse.ProductDetailList>)
    {
        binding.rvProductdetailsRecyclarview.adapter = ProductDetailsAdapter(this,productDetailList)
    }

    private fun recyclarcustomeralsobought(productDetailList: List<ProducatDetailsResponse.ProductDetailList>) {
        binding.rvProductdetailsRecyclarview2.adapter = ProductDetailsAdapter(this,productDetailList)
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
                /*lifecycleScope.launch(Dispatchers.IO) {
                    MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                        .insertCartProduct(CartProduct("Asd", 1, "", "1"))

                    MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                        .insertCartProduct(CartProduct("Asd", 2, "", "1"))
                    val intent = Intent(this@ProductDetailsActivity, ShoppingBagActivity::class.java)
                    startActivity(intent)
                }*/
                Utils.showLongToast(this,getString(R.string.comingsoon))
            }
            R.id.ivBagBtn ->
            {
                val intent = Intent(this, ShoppingBagActivity::class.java)
                startActivity(intent)
            }



            R.id.iv_drawer_back -> {
                onBackPressed()
            }

            R.id.tv_wishlist -> {
                if(fav_status == "0")
                {
                    wishlistviewModel.addremoveWishlist(product_id,1,product_details_id.toInt(),product_sizeId,product_colorId)
                    fav_status="1"
                }
                else
                {
                   wishlistviewModel.addremoveWishlist(product_id,0,product_details_id.toInt(),product_sizeId,product_colorId)
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

    @SuppressLint("SetTextI18n")
    private fun setObserver()
    {
        viewModel.product.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    setImageInSlider(response.data?.product_details?.images!!)
                    setData(response.data?.product_details)
                    setrecyclaryoumayalsolike(response.data?.you_may_also_like!!)
                    recyclarcustomeralsobought(response.data?.also_bought!!)
                    checkReadMore(response.data.product_details.product_desc)
                    choesColorRecyclaritem(response.data.product_details.colors)
                    binding.infoTool.tvTitle.text=response.data.product_details.product_name?.substring(0, 1)?.toUpperCase() +
                            response.data.product_details.product_name.substring(1)?.toLowerCase()
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
                      //ProgressDialog.hideProgressBar()
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
                    //  ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                   //   ProgressDialog.hideProgressBar()
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
        l = binding.tvProductDetailsDescr.layout
        l?.let{
            val lines: Int = it.lineCount
            if (lines > 0)
                if (it.getEllipsisCount(lines - 1) > 0)
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
            binding.tvProductPrice.text=getString(R.string.dollor)+data.sale_price
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

    override fun click(color_id: String)
    {
       // Utils.showLongToast(this,color_id)
    }

    override fun onProductClick(product_detail_id: Int, product_id: Int,product_sizeId:String,product_colorId:String) {
        viewModel.postProducatDetails("1",product_detail_id.toString(),product_id.toString(),product_sizeId,product_colorId) //api call
    }
}