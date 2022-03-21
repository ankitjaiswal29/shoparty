package com.shoparty.android.ui.customize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityCustomizeBinding
import com.shoparty.android.databinding.ActivityProductDetailsBinding
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.ui.shoppingbag.ShopingBagActivity


class CustomizeActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityCustomizeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_customize)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText(getString(R.string.customize_it))
    //    binding.customizeAddGraphic.setOnClickListener(this)
        binding.btnPreview.setOnClickListener(this)
        binding.infoTool.ivBagBtn.setOnClickListener(this)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.customizeApproveBtn.setOnClickListener(this)
        binding.infoTool.ivBagBtn.visibility=View.VISIBLE
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            /* R.id.btnCancel -> {
                 val intent = Intent(this, CancelOrderActivity::class.java)
                 intent.putExtra("key","Ongoeing")
                 startActivity(intent)
             }*/
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
           /* R.id.customize_add_graphic -> {
                binding.ctConstrainLay3.visibility=View.GONE
                binding.ctConstrainEditLay.visibility=View.VISIBLE
            }*/
            R.id.btn_preview -> {
                previewDialog()
                /*binding.ctConstrainLay3.visibility=View.GONE
                binding.ctConstrainEditLay.visibility=View.VISIBLE*/
            }
            R.id.customize_approve_btn -> {
             finish()
            }
            R.id.ivBagBtn -> {
                val intent = Intent(this, ShopingBagActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun previewDialog() {
        val builder = android.app.AlertDialog.Builder(this, R.style.CustomAlertDialogWithMargin)
        val inflater = layoutInflater
        val dialogLayout: View =
            inflater.inflate(R.layout.preview_dialog_layout, null)
        val iv_close = dialogLayout.findViewById<ImageView>(R.id.iv_close)

        builder.setView(dialogLayout)
        val builderinstance = builder.show()

        iv_close.setOnClickListener {
            builder.setCancelable(true)
            builderinstance.dismiss()
        }
    }
}