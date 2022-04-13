package com.shoparty.android.ui.vouchers

import android.R.attr.label
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityVouchersBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory


class VouchersActivity : AppCompatActivity(),RecyclerViewClickListener{
    private lateinit var binding: ActivityVouchersBinding
    private lateinit var viewModel: VoucherViewModel
    private lateinit var adapter: VoucherAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_vouchers)
        viewModel = ViewModelProvider(this, ViewModalFactory(application))[VoucherViewModel::class.java]
        initialise()
        viewModel.getVoucher()    //api call
        setObserver()
    }


    private fun initialise() {
        binding.infoTool.tvTitle.text = getString(R.string.vouchers)
        binding.infoTool.ivDrawerBack.setOnClickListener {
            onBackPressed()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setObserver()
    {
        viewModel.voucher.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    setVoucherListAdapter(response.data)
                }
                is Resource.Loading -> {
                    com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                }
                else -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                }
            }
        }
    }

    private fun setVoucherListAdapter(data: List<VoucherListResponse.Data>?)
    {
        binding.vouchersRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = VoucherAdapter(this, data!!,this)
        binding.vouchersRecyclerview.adapter = adapter
    }

    override fun click(couponcode: String)
    {
        /*val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label.toString(), couponcode)
        clipboard.setPrimaryClip(clip)
        Utils.showShortToast(this,getString(R.string.copytoclipboard))*/
        setResult(Activity.RESULT_OK, intent.putExtra(Constants.Coupon_Code,couponcode))
     //   setResult(Activity.RESULT_OK, data?.extras?.getString(Constants.Coupon_Price))
        finish()

    }

}