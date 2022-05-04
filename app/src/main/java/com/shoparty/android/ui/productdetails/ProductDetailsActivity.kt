package com.shoparty.android.ui.productdetails

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.InputType
import android.text.Layout
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import com.shoparty.android.BuildConfig
import com.shoparty.android.R
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.database.MyDatabase
import com.shoparty.android.databinding.ActivityProductDetailsBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.interfaces.WishListAddBagClickListener
import com.shoparty.android.ui.customize.CustomizeActivity
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.home.HomeResponse
import com.shoparty.android.ui.main.home.MySliderImageAdapter
import com.shoparty.android.ui.main.product_list.ProductListActivity
import com.shoparty.android.ui.main.wishlist.WishListViewModel
import com.shoparty.android.ui.shoppingbag.ShoppingBagActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProductDetailsActivity : AppCompatActivity(), View.OnClickListener,
    WishListAddBagClickListener,
    ProductDetailCallback {
    private var comment: String?=""
    private var pageClick:Boolean=false
    private var imageFile: File?=null
    private var l: Layout? = null
    private lateinit var binding: ActivityProductDetailsBinding
    var product_id = ""
    var product_details_id = ""
    var product_sizeId = ""
    var product_colorId = ""
    var fav_status = ""
    var sale_price = ""
    var product_name = ""
    var is_customizable = ""
    var sliderfirstimage = ""
    var quantity: Int = 0
    var colorfirattimeset:Boolean=false
    private lateinit var viewModel: ProducatDetailsViewModel
    private lateinit var wishlistviewModel: WishListViewModel
    private lateinit var productDetails: ProducatDetailsResponse.ProductDetails
    private lateinit var icon: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(application))[ProducatDetailsViewModel::class.java]
        wishlistviewModel =
            ViewModelProvider(this, ViewModalFactory(application))[WishListViewModel::class.java]
        icon = BitmapFactory.decodeResource(
            resources,
            R.mipmap.ic_success_logo
        )
        Constants.PRODUCT_COLOR = 0
        initialise()
        setObserver()
    }


    override fun onResume()
    {
        super.onResume()
          viewModel.postProducatDetails(
                PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),
                PrefManager.read(PrefManager.USER_ID, ""),
                product_details_id,
                product_id,
                product_sizeId,
                product_colorId) //api call
    }

    @SuppressLint("SetTextI18n")
    private fun initialise()
    {
        binding.infoTool.ivBagBtn.visibility = View.VISIBLE
        binding.btnCostomizeit.setOnClickListener(this)
        binding.tvAddtobag.setOnClickListener(this)
        binding.infoTool.ivBagBtn.setOnClickListener(this)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.tvWishlist.setOnClickListener(this)
        binding.tvReadmore.setOnClickListener(this)
        binding.ivShare.setOnClickListener(this)
        binding.ivPlus.setOnClickListener(this)
        binding.ivMinus.setOnClickListener(this)
        if (PrefManager.read(PrefManager.isFromLink, false)) {
            PrefManager.write(PrefManager.isFromLink, false)
            product_id = PrefManager.read(PrefManager.IDPRODUCT1, "")
            product_name = PrefManager.read(PrefManager.PRODUCATNAME1, "")
            product_details_id = PrefManager.read(PrefManager.PRODUCATDETAILSID1, "")
            product_sizeId = PrefManager.read(PrefManager.PRODUCTSIZEID1, "")
            product_colorId = PrefManager.read(PrefManager.PRODUCTCOLORID1, "")
        } else {
            if (intent.extras != null) {
                product_name = intent.getStringExtra(Constants.PRODUCATNAME).toString()
                product_id = intent.getStringExtra(Constants.IDPRODUCT)!!
                product_details_id = intent.getStringExtra(Constants.PRODUCATDETAILSID)!!
                product_sizeId = intent.getStringExtra(Constants.PRODUCTSIZEID)!!
                product_colorId = intent.getStringExtra(Constants.PRODUCTCOLORID)!!
                binding.infoTool.tvTitle.text =
                    product_name.substring(0, 1).uppercase() +
                            intent.getStringExtra(Constants.PRODUCATNAME)?.substring(1)
                                ?.lowercase()
            }
        }

        binding.ivShare.setOnClickListener {
            val sharedLink =
                "https://shoparty.page.link/share?pid=$product_id&pdetail=$product_details_id&psize=$product_sizeId&pcolor=$product_colorId&pname=" + product_name?.replace(
                    " ",
                    "_"
                )
            val DOMAIN_URI_PREFIX = "https://shoparty.page.link"
            val dynamicLink = Firebase.dynamicLinks.shortLinkAsync {
                link = Uri.parse(sharedLink)
                val socialMetaTag = DynamicLink.SocialMetaTagParameters.Builder()
                    .setTitle("Shoparty")
                    .setImageUrl(Uri.parse(""))
                    .build()
                setSocialMetaTagParameters(socialMetaTag)
                domainUriPrefix = DOMAIN_URI_PREFIX
                androidParameters {
                    minimumVersion = BuildConfig.VERSION_CODE
                }
            }.addOnSuccessListener {
                val shortLink = it.shortLink
                val previewLink = it.previewLink
                Log.e("TAG", ">>>>> shortLink ::$shortLink")
                Log.e("TAG", ">>>>> previewLink ::$previewLink")
                shareLink(shortLink.toString())
            }.addOnFailureListener {
                Log.e("TAG", ">>>>> exception ::${it.message}")
            }
        }


    }

    private fun shareLink(link: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        intent.putExtra(Intent.EXTRA_TEXT, link)
        startActivity(
            Intent.createChooser(intent, getString(R.string.share_to))
        )
    }

    private fun choesColorRecyclaritem(colors: List<ProducatDetailsResponse.Color>) {
        if (Constants.PRODUCT_COLOR != 0){
            colors.forEachIndexed { pos, it ->
                if (it.product_color_id == Constants.PRODUCT_COLOR) {
                    it.ischecked = true
                }
            }
        }else {
            colors.forEachIndexed { pos, it ->
                if (pos == 0) {
                    it.ischecked = true
                }
            }
        }
        val gridLayoutManager = GridLayoutManager(this, 9)
        binding.rvColorrecyclarview.layoutManager = gridLayoutManager
        binding.rvColorrecyclarview.adapter = ProductDetailsColorAdapter(
            this@ProductDetailsActivity,
            colors,
            this@ProductDetailsActivity)
    }

    private fun setrecyclaryoumayalsolike(productDetailList: List<ProducatDetailsResponse.ProductDetailList>)
    {
        binding.rvProductdetailsRecyclarview.adapter =
            ProductDetailsAdapter(this, productDetailList)
    }

    private fun recyclarcustomeralsobought(productDetailList: List<ProducatDetailsResponse.ProductDetailList>) {
        binding.rvProductdetailsRecyclarview2.adapter =
            ProductDetailsAdapter(this, productDetailList)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
            R.id.btn_costomizeit -> {
                val intent = Intent(this, CustomizeActivity::class.java)
                    .putExtra("image", productDetails?.images?.get(0)?.image.toString())
                   // .putExtra("modal", productDetails)
                startActivityForResult(intent,101)
            }
            R.id.tv_addtobag -> {
                if(PrefManager.read(PrefManager.AUTH_TOKEN, "") == "")
                {
                    val defQantity = lifecycleScope.async (Dispatchers.IO) {
                        quantity += 1
                        MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                            .insertCartProduct(
                                CartProduct(
                                    en_name = productDetails.en_name,
                                    id = productDetails.id,
                                    product_detail_id = productDetails.product_detail_id,
                                    product_size_id = product_sizeId.toInt(),
                                    product_colorId = product_colorId,
                                    image = productDetails.images[0].image,
                                    sale_price = productDetails.sale_price,
                                    cost_price = productDetails.cost_price,
                                    tax = productDetails.tax,
                                    shopping_qnty = quantity.toString(),
                                    bitmap = icon))
                          quantity
                    }

                    lifecycleScope.launch(Dispatchers.Main) {
                        defQantity.await()
                        if(quantity == 0)
                        {
                            addToBagButtonVisible()
                        }
                        else
                        {
                            addToBagButtonInVisible()
                        }
                    }

                }
                else
                {
                    quantity += 1
                    addToBagApi()
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
               if(PrefManager.read(PrefManager.AUTH_TOKEN,"") == "")
               {
                   val intent = Intent(this, LoginActivity::class.java)
                 //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                   startActivity(intent)
               //    finishAffinity()
               }
                else
                {
                    if(fav_status == "0")
                    {
                        wishlistviewModel.addremoveWishlist(
                            product_id,
                            1,
                            product_details_id.toInt(),
                            product_sizeId,
                            product_colorId)
                        fav_status = "1"
                    } else {
                        wishlistviewModel.addremoveWishlist(
                            product_id,
                            0,
                            product_details_id.toInt(),
                            product_sizeId,
                            product_colorId
                        )
                        fav_status = "0"
                    }
                }
            }


            R.id.tv_readmore -> {
                if (binding.tvReadmore.text.equals(getString(R.string.str_hideless))) {
                    binding.tvProductDetailsDescr.ellipsize = TextUtils.TruncateAt.END
                    binding.tvProductDetailsDescr.maxLines = 2
                    binding.tvReadmore.text = getString(R.string.read_more_withforword)
                } else {
                    binding.tvProductDetailsDescr.ellipsize = null
                    binding.tvProductDetailsDescr.isElegantTextHeight = true
                    binding.tvProductDetailsDescr.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE;
                    binding.tvProductDetailsDescr.isSingleLine = false
                    binding.tvReadmore.text = getString(R.string.str_hideless)
                }
            }
            R.id.iv_plus -> {
                if(PrefManager.read(PrefManager.AUTH_TOKEN, "") == "")
                {
                    val defQantity = lifecycleScope.async (Dispatchers.IO) {
                        quantity += 1
                        MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                            .insertCartProduct(
                                CartProduct(
                                    en_name = productDetails.en_name,
                                    id = productDetails.id,
                                    product_detail_id = productDetails.product_detail_id,
                                    product_size_id = product_sizeId.toInt(),
                                    product_colorId = product_colorId,
                                    image = productDetails.images[0].image,
                                    sale_price = productDetails.sale_price,
                                    cost_price = productDetails.cost_price,
                                    tax = productDetails.tax,
                                    shopping_qnty = quantity.toString(),
                                    bitmap = icon))
                        quantity

                    }
                    lifecycleScope.launch(Dispatchers.Main) {
                        defQantity.await()
                        if(quantity == 0)
                        {
                            addToBagButtonVisible()
                        }
                        else
                        {
                            addToBagButtonInVisible()
                        }
                    }
                }
                else
                {
                    quantity += 1
                    addToBagApi()
                }
            }

            R.id.iv_minus -> {
                if (PrefManager.read(PrefManager.AUTH_TOKEN, "") == "")
                {
                    val defQantity = lifecycleScope.async(Dispatchers.IO) {
                        if (quantity == 1)
                        {
                            quantity -= 1
                            val product =
                                MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                                    .getCartProduct(productDetails?.product_id?.toString()!!)
                            if (product != null)
                                MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                                    .deleteCartProduct(product)
                        }
                        else {
                            quantity -= 1
                            MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                                .insertCartProduct(
                                    CartProduct(
                                        en_name = productDetails.en_name,
                                        id = productDetails.id,
                                        product_detail_id = productDetails.product_detail_id,
                                        product_size_id = product_sizeId.toInt(),
                                        product_colorId = product_colorId,
                                        image = productDetails.images[0].image,
                                        sale_price = productDetails.sale_price,
                                        cost_price = productDetails.cost_price,
                                        tax = productDetails.tax,
                                        shopping_qnty = quantity.toString(),
                                        bitmap = icon
                                    )
                                )
                        }
                        quantity
                    }

                    lifecycleScope.launch(Dispatchers.Main) {
                        defQantity.await()
                        if(quantity == 0)
                        {
                            addToBagButtonVisible()
                        }
                        else
                        {
                            addToBagButtonInVisible()
                        }
                    }
                }
                else {
                    if (quantity >= 2) {
                        quantity -= 1
                        addToBagApi()
                    } else if (quantity == 1) {
                        quantity -= 1
                        addToBagApi()
                    }
                }
            }
        }
    }

    private fun addToBagApi()
    {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        if(imageFile != null)
        {
                builder.addFormDataPart(
                "customized_image",
                imageFile?.name,
                imageFile!!.asRequestBody("image/*".toMediaTypeOrNull()))

        }
        else
        {
            builder.addFormDataPart(
                "customized_image",sliderfirstimage)
        }
        builder.addFormDataPart("is_customizable", is_customizable)
        builder.addFormDataPart("product_id", product_id)
        builder.addFormDataPart("product_detail_id", product_details_id)
        builder.addFormDataPart("product_size_id",product_sizeId)
        builder.addFormDataPart("product_color_id",product_colorId)
        builder.addFormDataPart("quantity",quantity.toString())
        builder.addFormDataPart("price",  sale_price)
        builder.addFormDataPart("comment",  comment!!)
        val body = builder.build()
        viewModel.postAddProduct(body)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    @SuppressLint("SetTextI18n")
    private fun setObserver() {
        viewModel.product.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    setImageInSlider(response.data?.product_details?.images!!)
                    sliderfirstimage=response.data?.product_details.images[0].image
                    product_details_id=response.data?.product_details.product_detail_id
                    response.data.product_details.let {
                        setData(it)
                    }     //for data set
                    setrecyclaryoumayalsolike(response.data.you_may_also_like)
                    recyclarcustomeralsobought(response.data.also_bought)
                    checkReadMore(response.data.product_details.product_desc)

                    if(colorfirattimeset)  //color click user
                    {
                        colorfirattimeset=false
                    }
                    else {
                        choesColorRecyclaritem(response.data.product_details.colors)
                    }
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    ProgressDialog.hideProgressBar()
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
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    if (fav_status == "0") {
                        binding.imgEmptyHeart.visibility = View.VISIBLE
                        binding.imgFillHeart.visibility = View.GONE
                    } else {
                        binding.imgEmptyHeart.visibility = View.GONE
                        binding.imgFillHeart.visibility = View.VISIBLE
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

        viewModel.addbag.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    if(pageClick)
                    {
                        viewModel.postProducatDetails(
                            PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),
                            PrefManager.read(PrefManager.USER_ID, "").toString(),
                            product_details_id,
                            product_id,
                            product_sizeId,
                            product_colorId
                        ) //api call
                        pageClick=false
                    }
                    if(quantity == 0)
                    {
                        addToBagButtonVisible()
                    }
                    else
                    {
                        addToBagButtonInVisible()
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
    }

    private fun resetAllValues(
        productdetail_id: Int,
        productid: Int,
        productsizeId: String,
        productcolorId: String
    ) {
        product_id = productid.toString()
        product_details_id = productdetail_id.toString()
        product_sizeId = productsizeId
        product_colorId = productcolorId
    }

    private fun checkReadMore(productDesc: String) {
        binding.tvProductDetailsDescr.text = productDesc
        l = binding.tvProductDetailsDescr.layout
        l?.let {
            val lines: Int = it.lineCount
            if (lines > 0)
                if (it.getEllipsisCount(lines - 1) > 0) {
                    binding.tvReadmore.visibility = View.VISIBLE
                } else {
                    binding.tvReadmore.visibility = View.GONE
                }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(data: ProducatDetailsResponse.ProductDetails)
    {
        productDetails = data
        if(PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty())  //for local database
        {
            lifecycleScope.launch(Dispatchers.IO) {
                val product = MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao().getCartProduct(productDetails.product_id.toString())
                if(product != null)
                {
                    if(product.shopping_qnty !="")
                        quantity = product.shopping_qnty.toInt()
                    lifecycleScope.launch(Dispatchers.Main) {
                        if (quantity <= 0) {
                            addToBagButtonVisible()
                        } else {
                            addToBagButtonInVisible()
                        }
                    }
                }
                else
                {
                    lifecycleScope.launch(Dispatchers.Main) {
                        addToBagButtonVisible()
                    }
                }
            }
        }
        else     //for api quantity
        {
            quantity = data.cart_quantity!!                   //for quantity
            if (quantity <= 0)
            {
                addToBagButtonVisible()
            } else {
                addToBagButtonInVisible()
            }
        }
        sale_price = data.sale_price
        fav_status = data.fav_status.toString()
        quantity = data.cart_quantity!!
        is_customizable=data.is_customizable.toString()

        if (data.fav_status == 0) {
            binding.imgEmptyHeart.visibility = View.VISIBLE
            binding.imgFillHeart.visibility = View.GONE
            fav_status = "0"
        } else {
            binding.imgEmptyHeart.visibility = View.GONE
            binding.imgFillHeart.visibility = View.VISIBLE
            fav_status = "1"
        }
        binding.tvProductTitle.text = data.product_name

        if (data.cost_price.toDouble() > data.sale_price.toDouble()) {
            binding.tvProductCostPrice.visibility = View.VISIBLE
            binding.tvProductPrice.text = getString(R.string.dollor) + data.sale_price
            binding.tvProductCostPrice.text = getString(R.string.dollor) + data.cost_price
            binding.tvProductCostPrice.paintFlags =
                binding.tvProductCostPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.tvProductCostPrice.visibility = View.GONE
            binding.tvProductPrice.text = getString(R.string.dollor) + data.sale_price
        }
        if (data.is_customizable == 0) {
            binding.btnCostomizeit.visibility = View.GONE
        } else {
            binding.btnCostomizeit.visibility = View.VISIBLE
        }
        if (data.delivery_time.isNullOrEmpty()) {
            binding.tvOrdernow.visibility = View.GONE
            binding.tvOrdernowdate.visibility = View.GONE
        } else {
            binding.tvOrdernow.visibility = View.VISIBLE
            binding.tvOrdernowdate.visibility = View.VISIBLE
            binding.tvOrdernowdate.text = getString(R.string.andgetitby) + " " + data.delivery_time
        }


        binding.infoTool.tvTitle.text =
            data.product_name?.substring(0, 1)?.toUpperCase() +
                    data.product_name.substring(1)
                        ?.toLowerCase()
        binding.infoTool.tvTitle.text = data.product_name?.substring(0, 1)?.toUpperCase() +
                data.product_name.substring(1)?.toLowerCase()
        binding.nestedScrool.scrollTo(0, 0)
        binding.imageSliderr.requestFocus()
    }

    private fun addToBagButtonInVisible() {
        binding.tvAddtobag.visibility = View.GONE
        binding.relativeAddMinus.visibility = View.VISIBLE
        binding.tvCount.text = quantity.toString()

    }

    private fun addToBagButtonVisible() {
        binding.tvAddtobag.visibility = View.VISIBLE
        binding.relativeAddMinus.visibility = View.GONE
    }

    private fun setImageInSlider(images: List<HomeResponse.Home.Banner>) {
        val adapter = MySliderImageAdapter(this)
        adapter.renewItems(images as ArrayList<HomeResponse.Home.Banner>)
        binding.imageSliderr.setSliderAdapter(adapter)
        binding.imageSliderr.isAutoCycle = true
        binding.imageSliderr.startAutoCycle()
    }



    override fun onProductClick(
        product_detail_id: Int,
        product_id: Int,
        product_sizeId: String,
        product_colorId: String)
    {
        resetAllValues(product_detail_id, product_id, product_sizeId, product_colorId)
        viewModel.postProducatDetails(
            PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),
            PrefManager.read(PrefManager.USER_ID, ""),
            product_detail_id.toString(), product_id.toString(), product_sizeId, product_colorId) //api call
        binding.nestedScrool.scrollTo(0, binding.nestedScrool.top)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==101 && resultCode == Activity.RESULT_OK){
            imageFile= data?.getSerializableExtra("file") as File?
            comment = data?.getStringExtra("comment")
            quantity+=1
            pageClick=true
            if(PrefManager.read(PrefManager.AUTH_TOKEN, "") == "")
            {
                val defQantity = lifecycleScope.async (Dispatchers.IO) {
                    MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                        .insertCartProduct(
                            CartProduct(
                                en_name = productDetails.en_name,
                                comment = comment.toString(),
                                id = productDetails.id,
                                product_detail_id = productDetails.product_detail_id,
                                product_size_id = product_sizeId.toInt(),
                                product_colorId = product_colorId,
                                image = productDetails.images[0].image,
                                sale_price = productDetails.sale_price,
                                cost_price = productDetails.cost_price,
                                tax = productDetails.tax,
                                shopping_qnty = quantity.toString(),
                                bitmap = icon))
                    quantity

                }

                lifecycleScope.launch(Dispatchers.Main) {
                    defQantity.await()
                    if(quantity == 0)
                    {
                        addToBagButtonVisible()
                    }
                    else
                    {
                        addToBagButtonInVisible()
                    }
                }

            }
            else
            {
                addToBagApi()
            }
        }
    }

    override fun twoitemsPassClick(color_id: Int,product_detailid: Int) {
        viewModel.postProducatDetails(
            PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),
            PrefManager.read(PrefManager.USER_ID, ""),
            product_detailid.toString(), product_id, product_sizeId,color_id.toString()) //api call
        colorfirattimeset=true
        Constants.PRODUCT_COLOR = color_id
        product_colorId=color_id.toString()
      //  product_detailid=product_detailid.toString()
    }

}