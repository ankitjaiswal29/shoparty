package com.shoparty.android.ui.payment

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.stream.JsonReader
import com.nsoftware.ipworks3ds.sdk.Transaction
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings
import com.oppwa.mobile.connect.exception.PaymentError
import com.oppwa.mobile.connect.provider.Connect
import com.oppwa.mobile.connect.provider.TransactionType
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityPaymentBinding
import com.shoparty.android.ui.myorders.ordersuccess.OrderSuccessfulyActivity
import com.shoparty.android.ui.payment.orderplaced.OrderPlacedViewModel
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


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
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.mainLayoutPayment.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.infoTool.back.rotation = 180F
        }else {
            binding.mainLayoutPayment.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.infoTool.back.rotation = 0F
        }

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

               // val checkID = requestCheckoutId()
               // Log.e("TAG", "onClick: $checkID")
                val paymentBrands = hashSetOf("VISA", "MASTER")
               // val checkoutSettings = CheckoutSettings(checkID!!, paymentBrands, Connect.ProviderMode.TEST)
                val checkoutSettings = CheckoutSettings("B2730B92C1D8A27EAACC3EF25D0DF202.uat01-vm-tx04", paymentBrands, Connect.ProviderMode.TEST)
                val intent = checkoutSettings.createCheckoutActivityIntent(this)
                startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT)
            }
            R.id.back -> {
                onBackPressed()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            CheckoutActivity.RESULT_OK -> {
                /* transaction completed */
                val transaction: Transaction = data!!.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_TRANSACTION)!!
                Log.e("TAG", "onActivityResult: "+transaction.authenticationRequestParameters.sdkTransactionID )

                /* resource path if needed */
                val resourcePath = data.getStringExtra(CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH)
                Log.e("TAG", "onActivityResult: "+resourcePath.toString() )
              //  requestPaymentStatus(resourcePath)
//                if (transaction.transactionType == TransactionType.SYNC) {
//                    /* check the result of synchronous transaction */
//                } else {
//                    /* wait for the asynchronous transaction callback in the onNewIntent() */
//                }
            }

            CheckoutActivity.RESULT_CANCELED -> {
                /* shopper cancelled the checkout process */
            }

            CheckoutActivity.RESULT_ERROR -> {
                /* error occurred */
                val error: PaymentError = data!!.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_ERROR)!!
            }
        }
    }

    private fun requestPaymentStatus(resourcePath:String?): String? {
        val url: URL
        var connection: HttpURLConnection? = null
        var paymentStatus: String? = null

        val urlString = "https://eu-test.oppwa.com/v1/checkouts".toString() + "/paymentStatus?resourcePath=" + URLEncoder.encode(resourcePath, "UTF-8")

        try {
            url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection

            val jsonReader = JsonReader(InputStreamReader(connection.inputStream, "UTF-8"))
            jsonReader.beginObject()

            while (jsonReader.hasNext()) {
                if (jsonReader.nextName() == "paymentResult") {
                    paymentStatus = jsonReader.nextString()

                    break
                }
            }

            jsonReader.endObject()
            jsonReader.close()
        } catch (e: Exception) {
            /* error occurred */
        } finally {
            connection?.disconnect()
        }

        return paymentStatus
    }

    fun requestCheckoutId(): String? {
        val url: URL
        var connection: HttpURLConnection? = null
        var checkoutId: String? = null

        val urlString = "https://eu-test.oppwa.com/v1/checkouts" + "?amount=48.99&currency=SAR&paymentType=DB"

        try {
            url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection

            val reader = JsonReader(InputStreamReader(connection.inputStream, "UTF-8"))
            reader.beginObject()

            while (reader.hasNext()) {
                if (reader.nextName() == "checkoutId") {
                    checkoutId = reader.nextString()

                    break
                }
            }

            reader.endObject()
            reader.close()
        } catch (e: Exception) {
            /* error occurred */
        } finally {
            connection?.disconnect()
        }

        return checkoutId
    }

    private fun setObserver()
    {
        viewmodel.orderplacedsucess.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
//                    val intent = Intent(this, OrderSuccessfulyActivity::class.java)
//                    intent.putExtra("order_id",response.data?.order_id.toString())
//                    startActivity(intent)
//                    finish()
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