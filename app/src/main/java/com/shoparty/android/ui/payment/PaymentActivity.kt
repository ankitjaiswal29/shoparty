package com.shoparty.android.ui.payment

import abhishekti7.unicorn.filepicker.utils.Utils
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.util.Util
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityPaymentBinding
import com.shoparty.android.ui.addcard.AddCardActivity
import com.shoparty.android.ui.myorders.ordersuccess.OrderSuccessfulyActivity
import com.shoparty.android.utils.Constants


class PaymentActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_payment)
        initialise()
    }

    private fun initialise() {
        binding.tvAddCard.setOnClickListener(this)
        binding.btnContinewToPayment.setOnClickListener(this)
        binding.infoTool.tvTitle.text = getString(R.string.Payment)
        binding.infoTool.back.setOnClickListener(this)



       /* intent.putExtra(Constants.SUMMERYPRICE,binding.tvSummeryPrice.text.toString())
        intent.putExtra(Constants.TOTALAMOUNT,binding.tvTotalPriceDetail.text.toString())
        intent.putExtra(Constants.TOTALTAX,binding.tvTaxPrice.text.toString())
        intent.putStringArrayListExtra(Constants.SHOPPINGID,shoopingidlist)
        intent.putExtra(Constants.TOTALTAX,binding.tvTaxPrice.text.toString())
        intent.putExtra(Constants.ORDERTYPE,ordertype.toString())
        intent.putExtra(Constants.PROMOCODEID,CoupenId.toString())
        intent.putExtra(Constants.DISCOUNTAMOUNT,totaldiscountamount.toString())
        intent.putExtra(Constants.ISDELIVERABLE,isDeliverable.toString())
        intent.putExtra(Constants.STOREID,storeSelectedId.toString())*/

    }

    override fun onClick(v: View?) {
        when(v?.id){
             R.id.tvAddCard -> {
                /* val intent = Intent(this, AddCardActivity::class.java)
                 startActivity(intent)*/
                 com.shoparty.android.utils.Utils.showLongToast(this,getString(R.string.comingsoon))
             }
            R.id.btnContinewToPayment -> {

                val intent = Intent(this, OrderSuccessfulyActivity::class.java)
                startActivity(intent)
            }
            R.id.back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}