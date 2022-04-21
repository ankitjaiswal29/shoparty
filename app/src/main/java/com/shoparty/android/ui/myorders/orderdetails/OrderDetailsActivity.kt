package com.shoparty.android.ui.myorders.orderdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityOrderDetailsBinding
import com.shoparty.android.ui.myorders.MyOrderViewModel

import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
class OrderDetailsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOrderDetailsBinding
    private lateinit var viewModel: MyOrderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details)
        initialise()
        viewModel.orderDetails(intent.getStringExtra("order_id")!!.toInt())   //api call
        setObserver()
    }

    private fun initialise()
    {
        viewModel = ViewModelProvider(this, ViewModalFactory(application!!))[MyOrderViewModel::class.java]
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCancel -> {
               /* val intent = Intent(this, CancelOrderActivity::class.java)
                intent.putExtra("key", "OrderDetails")
                startActivity(intent)*/
                Utils.showLongToast(this,getString(R.string.comingsoon))
            }
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    @SuppressLint("SetTextI18n")
    private fun setObserver()
    {
        viewModel.Orderdetails.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    setData(response.data)
                    setOrderListAdapter(response.data!!?.product_response!!)
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

    private fun setData(data: OrderDetailsResponse.OrderList?)
    {
        binding.tvOrderNumber.text=getString(R.string.ordernumber)+" "+data?.order_number
        //  binding.tvSummeryPrice.text=getString(R.string.dollor)+response.data?.
        //  binding.tvTax.text=getString(R.string.tax_vat_5)+" "+"("+response.data?.tax+"%"+")"
        binding.tvTaxPrice.text=getString(R.string.dollor)+data?.tax
        binding.tvTotalPriceDetail.text=getString(R.string.dollor)+data?.total_amount
        binding.tvTotalPrice.text=getString(R.string.dollor)+data?.total_amount
        if(data?.action_status.equals(Constants.ONGOING))
        {
            binding.infoTool.tvTitle.setText(R.string.ongoingorderdetails)
        }
        else
        {
            binding.infoTool.tvTitle.setText(R.string.orderdetails)
        }
        setOrderStatus(data?.action_status!!)
    }

    private fun setOrderStatus(actionStatus: String) {
        if(actionStatus == "1")    //1==placed
        {
           placeOrderShow()
        }
        else if(actionStatus == "2")  //2===Confiremed
        {
            placeOrderShow()
            confirmedOrderShow()
        }

        else if(actionStatus == "3")  //3===out for delivery
        {
            placeOrderShow()
            confirmedOrderShow()
            outForDeliveryOrderShow()
        }

        else if(actionStatus == "4")  //4===order delivered
        {
            placeOrderShow()
            confirmedOrderShow()
            outForDeliveryOrderShow()
            deliveredOrderShow()
        }
        else if(actionStatus == "5")  //5===for order cancel
        {

        }
        else if(actionStatus == "6")  //5===for order failed
        {

        }
    }

    private fun placeOrderShow()
    {
        binding.ivImageOrderplace.setImageDrawable(resources.getDrawable(R.drawable.img_pink_order_placed))
    }

    private fun confirmedOrderShow()
    {
        binding.ivOrderConfirm.setImageDrawable(resources.getDrawable(R.drawable.img_pink_order_confirm))
        binding.ivOrderPlacedLine.setImageDrawable(resources.getDrawable(R.drawable.img_colorline))
    }


    private fun outForDeliveryOrderShow() {
        binding.ivOutForDelivery.setImageDrawable(resources.getDrawable(R.drawable.img_pink_outfordelivery))
        binding.ivOrderConfirmLine.setImageDrawable(resources.getDrawable(R.drawable.img_colorline))
    }

    private fun deliveredOrderShow() {
        binding.ivOrderDelivered.setImageDrawable(resources.getDrawable(R.drawable.img_pink_order_delivered))
        binding.ivOutForDeliveryLine.setImageDrawable(resources.getDrawable(R.drawable.img_colorline))
    }

    private fun setOrderListAdapter(productResponse: List<OrderDetailsResponse.ProductResponse>) {
       var adapter = OrderDetailsAdapter(productResponse!!,this)
        binding.recyclerOrderList.layoutManager = LinearLayoutManager(this)
        binding.recyclerOrderList.adapter = adapter
    }
}