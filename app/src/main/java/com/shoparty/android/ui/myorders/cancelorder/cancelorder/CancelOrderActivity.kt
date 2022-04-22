package com.shoparty.android.ui.myorders.cancelorder.cancelorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityCancelOrderBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener

import com.shoparty.android.ui.myorders.MyOrderViewModel

import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsAdapter
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsResponse
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class CancelOrderActivity : AppCompatActivity(), View.OnClickListener, RecyclerViewClickListener {
    private lateinit var binding: ActivityCancelOrderBinding
    private lateinit var viewModel: MyOrderViewModel
    var order_id=""
    var cancelReasonName=""
    private var cancelReasonlist: ArrayList<CancelReasonResponse.Result> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_cancel_order)
        initialise()
        viewModel.orderDetails(order_id.toInt())   //api call
        viewModel.cancelReason()   //api call
        setObserver()
    }

    private fun initialise()
    {
        viewModel = ViewModelProvider(this, ViewModalFactory(application))[MyOrderViewModel::class.java]
        order_id = intent.getStringExtra("order_id").toString()
        binding.infoTool.tvTitle.setText(R.string.cancel_order)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnCancel -> {
                if(cancelReasonName.isNullOrEmpty())
                {
                    Utils.showLongToast(this,getString(R.string.pleaseselectreason))
                }
                else
                {
                  viewModel.cancelOrderSuccess(cancelReasonName,order_id)
                }

            }
            R.id.iv_drawer_back -> {
               onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
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

        viewModel.CancelReason.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    cancelReasonlist= response.data as ArrayList<CancelReasonResponse.Result>
                    setcancelReasonAdapter(cancelReasonlist)
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


        viewModel.orderCancelSucces.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, CancelConfirmActivity::class.java)
                    intent.putExtra("order_id",order_id)
                    startActivity(intent)
                    finish()
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

    private fun setcancelReasonAdapter(data: List<CancelReasonResponse.Result>?)
    {
        var adapter = CancelReasonAdapter(data!! as ArrayList<CancelReasonResponse.Result>,this@CancelOrderActivity)
        binding.recyclerReason.layoutManager = LinearLayoutManager(this)
        binding.recyclerReason.adapter = adapter

    }


    private fun setData(data: OrderDetailsResponse.OrderList?)
    {
        binding.tvOrderNumber.text=getString(R.string.ordernumber)+" "+data?.order_number
        binding.tvTotalPrice.text=getString(R.string.dollor)+data?.amount_to_paid
    }
    private fun setOrderListAdapter(productResponse: List<OrderDetailsResponse.ProductResponse>) {
        var adapter = OrderDetailsAdapter(productResponse!!,this@CancelOrderActivity)
        binding.recyclerOrderList.layoutManager = LinearLayoutManager(this)
        binding.recyclerOrderList.adapter = adapter
    }

    override fun click(pos: String) {
        cancelReasonName=cancelReasonlist[pos.toInt()].reason
       // Utils.showLongToast(this,cancelReasonName)
    }

}