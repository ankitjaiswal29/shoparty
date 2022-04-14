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
    private var voucherlist: List<VoucherListResponse.Data>? = null
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
                    voucherlist=response.data
                    setVoucherListAdapter(voucherlist)
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

    private fun setVoucherListAdapter(voucherlist: List<VoucherListResponse.Data>?)
    {
        binding.vouchersRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = VoucherAdapter(this, voucherlist!!,this)
        binding.vouchersRecyclerview.adapter = adapter
    }

    override fun click(pos: String)
    {
        /*val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label.toString(), couponcode)
        clipboard.setPrimaryClip(clip)
        Utils.showShortToast(this,getString(R.string.copytoclipboard))*/
       var coupen_code = voucherlist?.get(pos.toInt())?.coupon_code
       var coupen_discount = voucherlist?.get(pos.toInt())?.discount
        setResult(Activity.RESULT_OK, intent.putExtra(Constants.Coupon_Code,coupen_code)
            .putExtra(Constants.Coupon_Discount,coupen_discount))
           finish()
    }



}