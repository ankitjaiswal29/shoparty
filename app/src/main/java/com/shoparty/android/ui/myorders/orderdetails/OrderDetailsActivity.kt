package com.shoparty.android.ui.myorders.orderdetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityOrderDetailsBinding
import com.shoparty.android.ui.myorders.MyOrderViewModel

import com.shoparty.android.ui.myorders.cancelorder.cancelorder.CancelOrderActivity
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class OrderDetailsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOrderDetailsBinding
    private lateinit var viewModel: MyOrderViewModel
    var totalitem=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details)
        initialise()
        viewModel = ViewModelProvider(this, ViewModalFactory(application!!))[MyOrderViewModel::class.java]
        viewModel.orderDetails(intent.getStringExtra("order_id").toString())   //api call
        setObserver()
    }

    private fun initialise()
    {
       /* val message = intent.getStringExtra("data")
        if (message.equals("1")) { } else {
            binding.group1.visibility = View.GONE
            binding.group2.visibility = View.GONE
            binding.ivOrderDelivered.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_order_cancelled_pink
                )
            )
        }*/
        binding.infoTool.tvTitle.setText(R.string.orderdetails)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCancel -> {
                val intent = Intent(this, CancelOrderActivity::class.java)
                intent.putExtra("key", "OrderDetails")
                startActivity(intent)
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
                    binding.tvOrderid.text=getString(R.string.orderId)+" "+response.data?.order_id
                    Glide.with(this).asBitmap().load(response.data?.product_response!![0].product_image).into(binding.ivProductImg)

                    response.data.product_response.forEachIndexed { index, productResponse ->
                        if(index==0)
                        {
                            totalitem=response.data?.product_response[index].product_name
                        }
                        else
                        {
                            var prductname=response.data?.product_response[index].product_name
                            totalitem= "$totalitem+$prductname"
                        }
                    }
                    binding.productName.text=totalitem

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
}