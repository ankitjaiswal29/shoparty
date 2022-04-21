package com.shoparty.android.ui.shoppingbag

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.service.autofill.FieldClassification
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
import com.shoparty.android.databinding.ActivityShopingBagBinding

import com.shoparty.android.interfaces.RVCartItemClickListener
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.address.addaddress.getaddress.AddressActivity
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.payment.PaymentActivity
import com.shoparty.android.ui.productdetails.ProducatDetailsViewModel
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.ui.vouchers.VouchersActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import kotlin.math.roundToLong

class ShoppingBagActivity : AppCompatActivity(), View.OnClickListener,RecyclerViewClickListener {
    private var fulladdress: String=""
    private var addressid: String=""
    private var coupenapplied: Boolean=false
    private var totaldiscountamount: Double=0.00
    private var totalpayamount: Double=0.00
    private var CoupenDiscount: Int=0
    private var CoupenId: Int=0
    private var taxPrice: Double=0.00
    private var clickAction: Int=0
    private var summaryprice: Double=0.00
    private var totalprice: Double=0.00
    private var storeSelectedId: Int=0
    private var pickupHomeSelected: Int=1
    private var ordertype: Int=1
    private var isDeliverable: Int=0
    private lateinit var binding: ActivityShopingBagBinding
    private lateinit var viewModel: ProducatDetailsViewModel
    private lateinit var shoopingbagviewModel: ShoppingBagViewModel
    private lateinit var adapterShoppingBag: ShoppingBagItemAdapter
    private var listCartProduct: ArrayList<CartProduct> = ArrayList()
    private var shoopingidlist: ArrayList<String> = ArrayList()
    private var storeList: ArrayList<StoreListResponse.Result> = ArrayList()
    private var quantity:Int=0
    private var position:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shoping_bag)
        viewModel = ViewModelProvider(this,
            ViewModalFactory(application)
        )[ProducatDetailsViewModel::class.java]
        shoopingbagviewModel = ViewModelProvider(this,
            ViewModalFactory(application)
        )[ShoppingBagViewModel::class.java]
        initialise()
        setObserver()
        if(PrefManager.read(PrefManager.AUTH_TOKEN,"") == "")
        {
            shoppingBagListLocal()
        }
        else                            //shopping Bag List Api
        {
            shoopingbagviewModel.cartItemList(PrefManager.read(PrefManager.LANGUAGEID,1).toString())
        }
    }

    private fun initialise()
    {
        binding.infoTool.tvTitle.text = getString(R.string.shippingbag)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.btnProcessTocheckOut.setOnClickListener(this)
        binding.bagItemConsLay3.setOnClickListener(this)
        binding.imgCoupenCross.setOnClickListener(this)
        binding.btnProcessTocheckOut.setOnClickListener(this)

        shoopingbagviewModel.storeList(PrefManager.read(PrefManager.LANGUAGEID,1).toString()) //api call

        binding.cbPickupBranch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                binding.bagItemPickupRecycler.visibility = View.VISIBLE
                binding.checkPickuphome.isChecked=false
            }
            else
            {
                binding.bagItemPickupRecycler.visibility = View.GONE
            }
        }

        binding.checkPickuphome.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
            {
                binding.bagItemPickupRecycler.visibility = View.GONE
                binding.cbPickupBranch.isChecked=false
                pickupHomeSelected=1
                ordertype=1
            }

        }

        binding.checkPurchaseGuast.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Utils.showLongToast(this, getString(R.string.comingsoon))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setObserver()
    {
        shoopingbagviewModel.cartitems.observe(this) { response ->
                when (response) {
                    is Resource.Success -> {
                        ProgressDialog.hideProgressBar()
                        listCartProduct= response.data as ArrayList<CartProduct>
                        if(listCartProduct.isNullOrEmpty())
                        {
                            binding.linearNoData.visibility=View.VISIBLE
                            binding.linearBagData.visibility=View.GONE
                        }
                        else
                        {
                            calculateUpdatedPrice()
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


        shoopingbagviewModel.storeList.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    storeList= response.data as ArrayList<StoreListResponse.Result>
                    shoppingBagPickup(storeList)

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




        viewModel.addbag.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    if(clickAction==1)  //add item click
                    {
                        updateAddPrice()
                    }
                    else
                    {
                        updateMinusPrice()
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

        shoopingbagviewModel.cartitemsremove.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    listCartProduct= response.data as ArrayList<CartProduct>
                    if(listCartProduct.isNullOrEmpty())
                    {
                        binding.linearNoData.visibility=View.VISIBLE
                        binding.linearBagData.visibility=View.GONE
                    }
                    else
                    {
                        calculateUpdatedPrice()
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
    }

    private fun calculateUpdatedPrice()
    {
        taxPrice=0.0
        summaryprice=0.0
        shoopingidlist.clear()

        listCartProduct.forEach {
            if(it.tax_type==1)  //calculation
            {
                taxPrice += (it.sale_price.toDouble() * it.shopping_qnty.toDouble()) * it.tax.toInt() / 100
            }
            summaryprice += it.sale_price.toDouble() * it.shopping_qnty.toDouble()
            totalprice=summaryprice+taxPrice
            shoopingidlist.addAll(listOf(it.shopping_id))

            isDeliverable=it.is_deliverable
        }
        binding.tvSummeryPrice.text=getString(R.string.dollor)+summaryprice.toString()
        binding.tvTaxPrice.text=getString(R.string.dollor)+taxPrice.toString()
        binding.linearNoData.visibility=View.GONE
        binding.linearBagData.visibility=View.VISIBLE
        shoppingBagListApi(listCartProduct)
        if(coupenapplied)
        {
            showCoupenDiscount()
        }
        else
        {
            binding.tvTotalPriceDetail.text=getString(R.string.dollor)+totalprice.toString()
        }




    }


    private fun updateAddPrice()
    {
        listCartProduct[position].shopping_qnty = quantity.toString()
        adapterShoppingBag.notifyDataSetChanged()
        summaryprice +=listCartProduct[position].sale_price.toDouble()
        binding.tvSummeryPrice.text=getString(R.string.dollor)+summaryprice.toString()

        if(listCartProduct[position].tax_type==1)  //tax caluclation
        {
            taxPrice+=listCartProduct[position].sale_price.toDouble()*listCartProduct[position].tax.toDouble()/100
            binding.tvTaxPrice.text=getString(R.string.dollor)+taxPrice.toString()
        }
        totalprice =summaryprice+taxPrice
        if(coupenapplied)
        {
            showCoupenDiscount()
        }
        else
        {
            binding.tvTotalPriceDetail.text=getString(R.string.dollor)+totalprice.toString()
        }
    }
    private fun updateMinusPrice() {
        listCartProduct[position].shopping_qnty = quantity.toString()
        adapterShoppingBag.notifyDataSetChanged()
        summaryprice -=listCartProduct[position].sale_price.toDouble()
        binding.tvSummeryPrice.text=getString(R.string.dollor)+summaryprice.toString()
        if(listCartProduct[position].tax_type==1)  //tax caluclation
        {
            taxPrice-=listCartProduct[position].sale_price.toDouble()*listCartProduct[position].tax.toDouble()/100
            binding.tvTaxPrice.text=getString(R.string.dollor)+taxPrice.toString()
        }
        totalprice =summaryprice+taxPrice
        if(coupenapplied)
        {
            showCoupenDiscount()
        }
        else
        {
            binding.tvTotalPriceDetail.text=getString(R.string.dollor)+totalprice.toString()
        }
    }

    private fun shoppingBagPickup(storeList: ArrayList<StoreListResponse.Result>)
    {
        binding.bagItemPickupRecycler.adapter = ShopingBagPickupAdapter(storeList,this)
    }

    private fun shoppingBagListLocal()
    {
        lifecycleScope.launch(Dispatchers.IO) {
            listCartProduct.clear()
            val dbList =
                MyDatabase.getInstance(this@ShoppingBagActivity).getProductDao().getAllCartProduct()
            listCartProduct.addAll(dbList)
        }
        adapterShoppingBag = ShoppingBagItemAdapter(this@ShoppingBagActivity, listCartProduct)
        val gridLayoutManager = GridLayoutManager(this, 1)
        binding.rvShopingitem.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterShoppingBag
        }
        adapterShoppingBag.onItemClick(object : RVCartItemClickListener {
            override fun onClick(pos: Int, view: View?) {
                startActivity(Intent(this@ShoppingBagActivity,ProductDetailsActivity::class.java))
            }

            override fun onPlus(pos: Int, view: View?) {
                lifecycleScope.launch(Dispatchers.IO) {
                    listCartProduct[pos].quantity =
                        (listCartProduct[pos].quantity.toInt() + 1).toString()
                    MyDatabase.getInstance(this@ShoppingBagActivity).getProductDao()
                        .updateCartProduct(listCartProduct[pos])
                    lifecycleScope.launch(Dispatchers.Main) {
                        adapterShoppingBag.notifyDataSetChanged()
                    }
                }
            }

            override fun onMinus(pos: Int, view: View?, shoppingId: Int) {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (listCartProduct.size > 0) {
                        if (listCartProduct[pos].quantity.toInt() >= 2) {
                            listCartProduct[pos].quantity =
                                (listCartProduct[pos].quantity.toInt() - 1).toString()
                            MyDatabase.getInstance(this@ShoppingBagActivity).getProductDao()
                                .updateCartProduct(listCartProduct[pos])
                        } else {
                            MyDatabase.getInstance(this@ShoppingBagActivity).getProductDao()
                                .deleteCartProduct(listCartProduct[pos])
                            listCartProduct.removeAt(pos)
                        }
                        lifecycleScope.launch(Dispatchers.Main) {
                            adapterShoppingBag.notifyDataSetChanged()
                        }
                    }
                }

            }

            override fun onClear(pos: Int, view: View?) {
                lifecycleScope.launch(Dispatchers.IO) {
                    MyDatabase.getInstance(this@ShoppingBagActivity).getProductDao()
                        .deleteCartProduct(listCartProduct[pos])
                    listCartProduct.removeAt(pos)
                    lifecycleScope.launch(Dispatchers.Main) {
                        adapterShoppingBag.notifyDataSetChanged()
                    }
                }
            }
        })
    }


    private fun shoppingBagListApi(listCartProduct: ArrayList<CartProduct>)
    {
        adapterShoppingBag = ShoppingBagItemAdapter(this@ShoppingBagActivity, listCartProduct)
        val gridLayoutManager = GridLayoutManager(this, 1)
        binding.rvShopingitem.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterShoppingBag
        }
        adapterShoppingBag.onItemClick(object : RVCartItemClickListener {
            override fun onClick(pos: Int, view: View?)
            {
              //  startActivity(Intent(this@ShoppingBagActivity,ProductDetailsActivity::class.java))
            }

            override fun onPlus(pos: Int, view: View?)
            {
                position=pos
                clickAction=1
                quantity=listCartProduct[position].shopping_qnty.toInt()+1
                viewModel.postAddProduct(listCartProduct[position].product_id.toString(),listCartProduct[position].product_detail_id.toString(),
                    listCartProduct[position].product_size_id.toString(),listCartProduct[position].product_color_id.toString(),quantity,
                    listCartProduct[position].sale_price)  //api call
            }

            override fun onMinus(pos: Int, view: View?, shoppingId: Int) {
                position=pos
                clickAction=0
                quantity=listCartProduct[pos].shopping_qnty.toInt()-1
                if(quantity==0)
                {
                    shoopingbagviewModel.cartItemRemove(shoppingId.toString())
                }
               else
               {
                   viewModel.postAddProduct(listCartProduct[pos].product_id.toString(),listCartProduct[pos].product_detail_id.toString(),
                       listCartProduct[pos].product_size_id.toString(),listCartProduct[pos].product_color_id.toString(),quantity,
                       listCartProduct[pos].sale_price)      //api call
               }
            }

            override fun onClear(shopping_id: Int, view: View?)
            {
                //remove api
                shoopingbagviewModel.cartItemRemove(shopping_id.toString())
            }
        })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
            R.id.btn_ProcessTocheckOut ->
            {
                if(PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty())
                {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    PrefManager.write(PrefManager.IS_SHIPPING_PAGE, "1")
                    startActivity(intent)
                }
                else
                {
                        if(addressid.isNullOrEmpty())
                          {
                              val intent = Intent(this, AddressActivity::class.java)
                              intent.putExtra("pagestatus","1")
                              startActivityForResult(intent,Constants.SHOPPINGBAGTOADDRESS)
                          }
                        else
                          {
                              val intent = Intent(applicationContext, PaymentActivity::class.java)
                              intent.putExtra(Constants.SUMMERYPRICE,summaryprice.toString())
                              intent.putExtra(Constants.TOTALAMOUNT,binding.tvTotalPriceDetail.text.toString().replace("$",""))
                              intent.putExtra(Constants.SHOPPINGID,shoopingidlist)
                              intent.putExtra(Constants.TOTALTAX,taxPrice.toString())
                              intent.putExtra(Constants.ADDRESSID,addressid)
                              intent.putExtra(Constants.ORDERTYPE,ordertype.toString())
                              intent.putExtra(Constants.PROMOCODEID,CoupenId.toString())
                              intent.putExtra(Constants.DISCOUNTAMOUNT,totaldiscountamount.toString())
                              intent.putExtra(Constants.ISDELIVERABLE,isDeliverable.toString())
                              intent.putExtra(Constants.STOREID,storeSelectedId.toString())
                              startActivity(intent)
                           }
                   //   Utils.showLongToast(this,getString(R.string.comingsoon))
                }
            }

            R.id.bag_item_cons_lay3->
            {
                val intent = Intent(applicationContext, VouchersActivity::class.java)
                startActivityForResult(intent, Constants.SHOPPINGBAG_CODE)
            }

            R.id.imgCoupenCross->
            {
               hideCoupenDiscount()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.SHOPPINGBAG_CODE && resultCode == Activity.RESULT_OK)
        {
            binding.txtapplycode.text=data?.extras?.getString(Constants.Coupon_Code)
            CoupenDiscount= data?.extras?.getInt(Constants.Coupon_Discount)!!
            CoupenId= data?.extras?.getInt(Constants.CouponID)!!
            showCoupenDiscount()
            coupenapplied=true
        }
        else if(requestCode == Constants.SHOPPINGBAGTOADDRESS && resultCode == Activity.RESULT_OK)
        {
            addressid=data?.getStringExtra("addressid").toString()
            fulladdress=data?.getStringExtra("fulladdress").toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showCoupenDiscount()
    {
        totaldiscountamount = totalprice * CoupenDiscount!! /100
        totalpayamount=totalprice-totaldiscountamount
        binding.imgCoupenCross.visibility=View.VISIBLE
        binding.tvDiscount.visibility=View.VISIBLE
        binding.tvDiscountPrice.visibility=View.VISIBLE
        binding.tvDiscountPrice.text="-"+" "+getString(R.string.dollor)+totaldiscountamount.toString()
        binding.tvTotalPriceDetail.text=getString(R.string.dollor)+ totalpayamount.roundToLong().toString()
    }

    private fun hideCoupenDiscount()
    {
        coupenapplied=false
        totaldiscountamount=0.0
        binding.imgCoupenCross.visibility=View.INVISIBLE
        binding.tvDiscount.visibility=View.GONE
        binding.tvDiscountPrice.visibility=View.GONE
        binding.tvDiscountPrice.text=totaldiscountamount.toString()
        binding.txtapplycode.text=getString(R.string.apply_promo_code)
        binding.tvTotalPriceDetail.text=getString(R.string.dollor)+totalprice.toString()
    }

    override fun click(pos: String) {
        storeSelectedId=storeList[pos.toInt()].id.toInt()
        ordertype=0
       // Utils.showLongToast(this,storeSelectedId.toString())
    }



}