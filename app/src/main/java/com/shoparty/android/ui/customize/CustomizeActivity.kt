package com.shoparty.android.ui.customize

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.fonts.SystemFonts
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityCustomizeBinding
import com.shoparty.android.databinding.PopupLayoutAvailableFontsBinding
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.ui.shoppingbag.ShoppingBagActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class CustomizeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCustomizeBinding
    val listFonts: ArrayList<Font> = ArrayList()
    val listColor: ArrayList<String> = ArrayList()
    val listSize: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_customize)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText(getString(R.string.customize_it))
        binding.btnPreview.setOnClickListener(this)
        binding.infoTool.ivBagBtn.setOnClickListener(this)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.customizeApproveBtn.setOnClickListener(this)
        binding.infoTool.ivBagBtn.visibility = View.VISIBLE

        binding.tvFont.setOnClickListener {
            showAvailableFonts()
        }

        binding.tvSize.setOnClickListener {
            showAvailableTextSize()
        }


        lifecycleScope.launch(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val fonts = SystemFonts.getAvailableFonts()
                //Log.e("fontsSize",fonts.size.toString())
                fonts.forEach {
                    //  Log.e("font",it.toString())
                    listFonts.add(Font(it?.file?.name.toString(), it?.file!!))
                }
            } else {
                val path = "/system/fonts"
                val file = File(path)
                val fonts: Array<File> = file.listFiles()
                //  Log.e("fontsSize",fonts.size.toString())
                fonts.forEach {
                    listFonts.add(Font(it?.name.toString(), it))
                    Log.e("font", it.name)
                    //Log.e("fontsPath",it.absolutePath)
                    //Log.e("fontsExt",it.extension)
                }
            }

        }


    }

    override fun onClick(v: View?) {
        when (v?.id) {
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
                val intent = Intent(this, ProductDetailsActivity::class.java)
                startActivity(intent)
            }
            R.id.ivBagBtn -> {
                val intent = Intent(this, ShoppingBagActivity::class.java)
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

    private fun showAvailableFonts() {
        val customView: View =
            LayoutInflater.from(this).inflate(R.layout.popup_layout_available_fonts, null)
        val popupWindow = PopupWindow(
            customView,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        val popupBinding: PopupLayoutAvailableFontsBinding = DataBindingUtil.bind(customView)!!
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.elevation = 5.0f
        // popupWindow.softInputMode = PopupWindow.INPUT_METHOD_NEEDED
        //popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //popupWindow.showAtLocation(binding.tvFont, Gravity.CENTER, 0, 0)
        popupWindow.showAsDropDown(binding.tvFont, 0, 10, Gravity.CENTER)

        val adapterFont = FontAdapter(this@CustomizeActivity, listFonts)
        val gridLayoutManager = GridLayoutManager(this, 1)
        popupBinding.rvData.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = adapterFont
        }
        adapterFont.onItemClick(object : RVItemClickListener {
            override fun onClick(pos: String, view: View?) {
                binding.tvFont.text = listFonts[pos.toInt()].name
                val typeface: Typeface = Typeface.createFromFile(listFonts[pos.toInt()].file)
                binding.tvData.typeface = typeface
                popupWindow.dismiss()
            }
        })
    }

    private fun showAvailableTextSize() {
        val customView: View =
            LayoutInflater.from(this).inflate(R.layout.popup_layout_available_fonts, null)
        val popupWindow = PopupWindow(
            customView,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        val popupBinding: PopupLayoutAvailableFontsBinding = DataBindingUtil.bind(customView)!!
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.elevation = 5.0f
        // popupWindow.softInputMode = PopupWindow.INPUT_METHOD_NEEDED
        //popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //popupWindow.showAtLocation(binding.tvFont, Gravity.CENTER, 0, 0)
        popupWindow.showAsDropDown(binding.tvSize, 0, 10, Gravity.CENTER)

        for (i in 20..40) {
            listSize.add(i)
        }
        val adapterSize = SizeAdapter(this@CustomizeActivity, listSize)
        val gridLayoutManager = GridLayoutManager(this, 1)
        popupBinding.rvData.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = adapterSize
        }
        adapterSize.onItemClick(object : RVItemClickListener {
            override fun onClick(pos: String, view: View?) {
                binding.tvSize.text = listSize[pos.toInt()].toString()

                binding.tvData.textSize = listSize[pos.toInt()].toFloat()
                popupWindow.dismiss()
            }
        })
    }

    private fun showAvailableColors() {
        val customView: View =
            LayoutInflater.from(this).inflate(R.layout.popup_layout_available_fonts, null)
        val popupWindow = PopupWindow(
            customView,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        val popupBinding: PopupLayoutAvailableFontsBinding = DataBindingUtil.bind(customView)!!
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.elevation = 5.0f
        // popupWindow.softInputMode = PopupWindow.INPUT_METHOD_NEEDED
        //popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //popupWindow.showAtLocation(binding.tvFont, Gravity.CENTER, 0, 0)
        popupWindow.showAsDropDown(binding.tvSize, 0, 10, Gravity.CENTER)

        for (i in 20..40) {
            listSize.add(i)
        }
        val adapterSize = SizeAdapter(this@CustomizeActivity, listSize)
        val gridLayoutManager = GridLayoutManager(this, 10)
        popupBinding.rvData.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = adapterSize
        }
        adapterSize.onItemClick(object : RVItemClickListener {
            override fun onClick(pos: String, view: View?) {
                binding.tvSize.text = listSize[pos.toInt()].toString()

                binding.tvData.textSize = listSize[pos.toInt()].toFloat()
                popupWindow.dismiss()
            }
        })
    }
}