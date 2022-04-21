package com.shoparty.android.ui.payment

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.shoparty.android.R
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.databinding.ActivityPaymentBinding
import com.shoparty.android.ui.myorders.ordersuccess.OrderSuccessfulyActivity
import com.shoparty.android.ui.payment.orderplaced.OrderPlacedViewModel
import com.shoparty.android.ui.shoppingbag.StoreListResponse
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory


class PaymentActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityPaymentBinding
    private var summaryprice=""
    private var totaltax=""
    private var totalamount=""
    private var paymentstatus="1"
    private var paymenttype="1"
    private var transactionid="t2ad01zchq10qe040dec0d5cq2e504da2a04da2d0cc"
    private var ordertype=""
    private var promocodeid=""
    private var discountprice=""
    private var addressid=""
    private var isdeliverable=""
    private var storeid=""
    private var shoppingidlist:ArrayList<String> = ArrayList()
    private lateinit var viewmodel:OrderPlacedViewModel
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_payment)
        initialise()
        getIntentData()
        setObserver()

    }


    @SuppressLint("SetTextI18n")
    private fun getIntentData() {
        summaryprice= intent.getStringExtra(Constants.SUMMERYPRICE)!!
        totaltax= intent.getStringExtra(Constants.TOTALTAX)!!
        totalamount= intent.getStringExtra(Constants.TOTALAMOUNT)!!
        ordertype= intent.getStringExtra(Constants.ORDERTYPE)!!
        promocodeid= intent.getStringExtra(Constants.PROMOCODEID)!!
        discountprice= intent.getStringExtra(Constants.DISCOUNTAMOUNT)!!
        addressid= intent.getStringExtra(Constants.ADDRESSID)!!
        isdeliverable= intent.getStringExtra(Constants.ISDELIVERABLE)!!
        storeid= intent.getStringExtra(Constants.STOREID)!!
        shoppingidlist= intent.getSerializableExtra(Constants.SHOPPINGID)!! as ArrayList<String>
        binding.tvSummeryPrice.text=getString(R.string.dollor)+summaryprice
        binding.tvTaxPrice.text=getString(R.string.dollor)+totaltax
        binding.tvTotalPrice.text=getString(R.string.dollor)+totalamount
        if(discountprice == "0.0")
        {
            hideCoupenDiscount()
        }
        else
        {
            showCoupenDiscount()
        }
    }

    private fun initialise() {
        binding.tvAddCard.setOnClickListener(this)
        binding.btnContinewToPayment.setOnClickListener(this)
        binding.infoTool.tvTitle.text = getString(R.string.Payment)
        binding.infoTool.back.setOnClickListener(this)
        viewmodel = ViewModelProvider(this, ViewModalFactory(application))[OrderPlacedViewModel::class.java]
    }


    private fun hideCoupenDiscount() {
        binding.tvDiscount.visibility=View.GONE
        binding.tvDiscountPrice.visibility=View.GONE
    }

    private fun showCoupenDiscount() {
        binding.tvDiscount.visibility=View.VISIBLE
        binding.tvDiscountPrice.visibility=View.VISIBLE
        binding.tvDiscountPrice.text= "-"+" "+getString(R.string.dollor)+discountprice
    }

    override fun onClick(v: View?) {
        when(v?.id){
             R.id.tvAddCard -> {
                /* val intent = Intent(this, AddCardActivity::class.java)
                 startActivity(intent)*/
                 com.shoparty.android.utils.Utils.showLongToast(this,getString(R.string.comingsoon))
             }
            R.id.btnContinewToPayment -> {
                viewmodel.postOrderPlaced(PrefManager.read(PrefManager.LANGUAGEID,1).toString(),
                    shoppingidlist,paymentstatus,paymenttype,transactionid,
                    ordertype,promocodeid,summaryprice,discountprice,totaltax,
                    totalamount,addressid,isdeliverable,storeid)    //api call
            }
            R.id.back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setObserver()
    {
        viewmodel.orderplacedsucess.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    val intent = Intent(this, OrderSuccessfulyActivity::class.java)
                    intent.putExtra("order_id",response.data?.order_id.toString())
                    startActivity(intent)
                    finish()
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

}