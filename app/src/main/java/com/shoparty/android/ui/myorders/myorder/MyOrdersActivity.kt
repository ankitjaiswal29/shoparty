package com.shoparty.android.ui.myorders.myorder


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityMyOrdersBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class MyOrdersActivity : AppCompatActivity(), View.OnClickListener,
    RecyclerViewClickListener {
    private lateinit var binding: ActivityMyOrdersBinding
    private lateinit var adapter: MyOrderAdapters
    private lateinit var viewModel: MyOrderViewModel
    private var myorderlist: ArrayList<MyOrderResponse.Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_orders)
        viewModel =
            ViewModelProvider(this, ViewModalFactory(application))[MyOrderViewModel::class.java]
        viewModel.myOrders()          //api call
        initialise()
        setObserver()
    }

    private fun initialise() {
        // this creates a vertical layout Manager
        binding.infoTool.tvTitle.setText(getString(R.string.my_orders))
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.myorderRecyclerview.layoutManager = LinearLayoutManager(this)
        //  myOrderListing()

    }

    private fun setObserver() {
        viewModel.myorder.observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()

                    if(response.data.isNullOrEmpty())
                    {
                        binding.clNoData.visibility=View.VISIBLE
                        binding.myorderRecyclerview.visibility=View.GONE
                    }
                    else
                    {
                        binding.clNoData.visibility=View.GONE
                        binding.myorderRecyclerview.visibility=View.VISIBLE
                        myorderlist.clear()
                        myorderlist = response.data as ArrayList<MyOrderResponse.Data>
                        setMyOrderListAdapter(myorderlist)
                    }
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
        })


    }

    private fun setMyOrderListAdapter(data: List<MyOrderResponse.Data>?) {
        adapter = MyOrderAdapters(data!!, this)
        binding.myorderRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.myorderRecyclerview.adapter = adapter
    }




    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    override fun click(pos: String) {

    }
}