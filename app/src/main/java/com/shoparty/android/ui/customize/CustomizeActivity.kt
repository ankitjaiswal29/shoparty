package com.shoparty.android.ui.customize

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.fonts.SystemFonts
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.developers.imagezipper.ImageZipper
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.shoparty.android.R
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.database.MyDatabase
import com.shoparty.android.databinding.ActivityCustomizeBinding
import com.shoparty.android.databinding.PopupLayoutAvailableFontsBinding
import com.shoparty.android.databinding.PreviewDialogLayoutBinding
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.ui.productdetails.AddItemToBagRequestModel
import com.shoparty.android.ui.productdetails.ProducatDetailsResponse
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.ui.shoppingbag.ShoppingBagActivity
import com.shoparty.android.utils.ImagePickerActivity
import com.shoparty.android.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class CustomizeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCustomizeBinding
    private val listFonts: ArrayList<Font> = ArrayList()

    private val listSize: ArrayList<Int> = ArrayList()

    private var dialog: Dialog? = null
    private val REQUEST_IMAGE = 999
    private var imageZipperFile: File? = null

    private var mRootWidth = 0
    private var mRootHeight = 0

    private var mXDelta = 0
    var mYDelta = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_customize)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.text = getString(R.string.customize_it)

        val image = intent.getStringExtra("image")
        val modal = intent.getParcelableExtra<ProducatDetailsResponse.ProductDetails>("modal")
        // val data = intent.getParcelableExtra<ProducatDetailsResponse.ProductDetails>("productDetails")
        Log.e("data", image.toString())
        Log.e("modal", modal.toString())
       // Glide.with(this@CustomizeActivity).asBitmap().load(image).into(binding.imgBanner)

        Glide.with(this@CustomizeActivity).asBitmap()
            .load(image.toString())
            //.centerCrop()
           // .placeholder(R.mipmap.ic_launcher)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {

                    return true
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    binding.clView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            binding.clView.viewTreeObserver
                                .removeOnGlobalLayoutListener(this)
                            // }
                            mRootWidth = binding.clView.getWidth()
                            mRootHeight = binding.clView.getHeight()
                        }
                    })

                    binding.tvData.setOnTouchListener(mOnTouchListener)
                    binding.tvData.requestFocus()
                    return false
                }

            })
            .into(binding.imgBanner)

        binding.infoTool.ivBagBtn.visibility = View.VISIBLE

        binding.infoTool.ivBagBtn.setOnClickListener(this)

        binding.infoTool.ivDrawerBack.setOnClickListener {
            onBackPressed()
        }

        binding.tvFont.setOnClickListener {
            showAvailableFonts()
        }

        binding.tvSize.setOnClickListener {
            showAvailableTextSize()
        }

        binding.llColor.setOnClickListener {
            showAvailableColors()
        }
        binding.customizeApproveBtn.setOnClickListener {
            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            val bitmap=Utils.screenShot(binding.clView)
                            val file= bitmapToFile(bitmap!!,"cdc")
                            val intent = Intent()
                            setResult(Activity.RESULT_OK,
                                intent.putExtra("file",file))
                               finish()
                        }
                        if (report.isAnyPermissionPermanentlyDenied) {
                            showSettingsDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        token?.continuePermissionRequest()
                    }
                }).check()

        }
        binding.llText.setOnClickListener {
            binding.etName.requestFocus()
            Utils.showKeyboard(this@CustomizeActivity, binding.root)
        }

        binding.btnPreview.setOnClickListener {
            showPreview()
        }

        binding.llPicture.setOnClickListener {
            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            openDialogToUpdateProfilePIC()
                        }
                        if (report.isAnyPermissionPermanentlyDenied) {
                            showSettingsDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        token?.continuePermissionRequest()
                    }
                }).check()
        }

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.tvData.text = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

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

    private var mOnTouchListener = object : View.OnTouchListener {

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            val xScreenTouch =
                event.rawX.toInt() // x location relative to the screen
            val yScreenTouch =
                event.rawY.toInt() // y location relative to the screen

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = view.layoutParams as RelativeLayout.LayoutParams
                    mXDelta = xScreenTouch - lParams.leftMargin
                    mYDelta = yScreenTouch - lParams.topMargin
                }
                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = view
                        .layoutParams as RelativeLayout.LayoutParams
                    layoutParams.leftMargin =
                        Math.max(0, Math.min(mRootWidth - 20, xScreenTouch - mXDelta))
                    layoutParams.topMargin =
                        Math.max(0, Math.min(mRootHeight - 20, yScreenTouch - mYDelta))
                    view.layoutParams = layoutParams
                }
            }
            return true
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBagBtn -> {
                val intent = Intent(this, ShoppingBagActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null
        return try {
            file = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileNameToSave)
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    private fun showPreview() {

        val customView: View =
            LayoutInflater.from(this).inflate(R.layout.preview_dialog_layout, null)
        val popupWindow = PopupWindow(
            customView,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        val popupBinding: PreviewDialogLayoutBinding = DataBindingUtil.bind(customView)!!
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.elevation = 5.0f
        // popupWindow.softInputMode = PopupWindow.INPUT_METHOD_NEEDED
        //popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)

        val bitmap = Utils.screenShot(binding.clView)

        popupBinding.imgBanner.setImageBitmap(bitmap)

        popupBinding.ivClose.setOnClickListener {
            popupWindow.dismiss()
           /* lifecycleScope.launch(Dispatchers.IO) {
//                MyDatabase.getInstance(this@CustomizeActivity).getProductDao()
//                    .insertCartProduct(
//                        AddItemToBagRequestModel(product_id.toInt(),product_detail_id,product_size_id,
//                            product_color_id,quantity,price)
//                    )

                val intent =
                    Intent(this@CustomizeActivity, ShoppingBagActivity::class.java)
                startActivity(intent)
            }*/

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
        popupWindow.showAsDropDown(binding.tvFont, 0, 0, Gravity.CENTER)

        val adapterFont = FontAdapter(this@CustomizeActivity, listFonts)
        val gridLayoutManager = GridLayoutManager(this, 1)
        popupBinding.rvData.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = adapterFont
        }
        adapterFont.onItemClick(object : RVItemClickListener {
            override fun onClick(pos: String, view: View?) {
                binding.tvFont.text =
                    listFonts[pos.toInt()].name.removeSuffix(".ttf").removeSuffix(".otf")
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
        popupWindow.showAsDropDown(binding.tvSize, 0, 0, Gravity.CENTER)
        listSize.clear()
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

                binding.tvData.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP,
                    listSize[pos.toInt()].toFloat()
                )
                popupWindow.dismiss()
            }
        })
    }

    private fun showAvailableColors() {
        val customView: View =
            LayoutInflater.from(this).inflate(R.layout.popup_layout_available_fonts, null)
        val popupWindow = PopupWindow(
            customView,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
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

        val adapterColor = ColorAdapter(this@CustomizeActivity, listColor)
        val gridLayoutManager = GridLayoutManager(this, 10)
        popupBinding.rvData.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = adapterColor
        }
        adapterColor.onItemClick(object : RVItemClickListener {
            override fun onClick(pos: String, view: View?) {
                binding.viewColor.setBackgroundColor(Color.parseColor(listColor[pos.toInt()]))
                binding.tvData.setTextColor(Color.parseColor(listColor[pos.toInt()]))
                popupWindow.dismiss()
            }
        })
    }

    fun openDialogToUpdateProfilePIC() {
        dialog = Dialog(this)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_curved_bg_inset)
        dialog?.setContentView(R.layout.dialog_select)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog?.getWindow()?.getAttributes())
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog?.getWindow()?.setAttributes(lp)
        val cameraLayout: LinearLayout? = dialog?.findViewById<LinearLayout>(R.id.cameraLayout)
        val galleryLayout: LinearLayout? = dialog?.findViewById<LinearLayout>(R.id.galleryLayout)
        cameraLayout?.setOnClickListener {
            launchCameraIntent()
            dialog?.dismiss()
        }
        galleryLayout?.setOnClickListener {
            launchGalleryIntent()
            dialog?.dismiss()
        }
        dialog?.show()
    }

    fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_permission_title))
        builder.setMessage(getString(R.string.dialog_permission_message))
        builder.setPositiveButton(getString(R.string.go_to_settings)) { dialog: DialogInterface, which: Int ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton(
        ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
        builder.show()

    }

    private fun AlertDialog.Builder.setNegativeButton(function: (DialogInterface, Int) -> Unit) {

    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", this.packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    private fun launchCameraIntent() {
        val intent = Intent(this, ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity.REQUEST_IMAGE_CAPTURE
        )

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    private fun launchGalleryIntent() {
        val intent = Intent(this, ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity.REQUEST_GALLERY_IMAGE
        )

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            checkPermissionOnActivityResult(requestCode, resultCode, data)
        }
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                val uri = data!!.getParcelableExtra<Uri>("path")
                val file = File(uri!!.path)
                loadProfile(uri.toString())
                try {
                    imageZipperFile = ImageZipper(this)
                        .setQuality(50)
                        .setMaxWidth(300)
                        .setMaxHeight(300)
                        .compressToFile(file)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkPermissionOnActivityResult(requestCode: Int, resultCode: Int, data: Intent) {}

    private fun loadProfile(url: String) {
        Glide.with(this).load(url).error(R.drawable.gallery_image_icon)
            .placeholder(R.drawable.gallery_image_icon)
            .into(binding.imgBanner)
    }

    private val listColor = listOf<String>(
        "#B0171F",
        "#DC143C",
        "#FFB6C1",
        "#FFAEB9",
        "#EEA2AD",
        "#CD8C95",
        "#8B5F65",
        "#FFC0CB",
        "#FFB5C5",
        "#EEA9B8",
        "#CD919E",
        "#8B636C",
        "#DB7093",
        "#FF82AB",
        "#EE799F",
        "#CD6889",
        "#8B475D",
        "#FFF0F5",
        "#EEE0E5",
        "#CDC1C5",
        "#8B8386",
        "#FF3E96",
        "#EE3A8C",
        "#CD3278",
        "#8B2252",
        "#FF69B4",
        "#FF6EB4",
        "#EE6AA7",
        "#CD6090",
        "#8B3A62",
        "#872657",
        "#FF1493",
        "#EE1289",
        "#CD1076",
        "#8B0A50",
        "#FF34B3",
        "#EE30A7",
        "#CD2990",
        "#8B1C62",
        "#C71585",
        "#D02090",
        "#DA70D6",
        "#FF83FA",
        "#EE7AE9",
        "#CD69C9",
        "#8B4789",
        "#D8BFD8",
        "#FFE1FF",
        "#EED2EE",
        "#CDB5CD",
        "#8B7B8B",
        "#FFBBFF",
        "#EEAEEE",
        "#CD96CD",
        "#8B668B",
        "#DDA0DD",
        "#EE82EE",
        "#FF00FF",
        "#EE00EE",
        "#CD00CD",
        "#8B008B",
        "#800080",
        "#BA55D3",
        "#E066FF",
        "#D15FEE",
        "#B452CD",
        "#7A378B",
        "#9400D3",
        "#9932CC",
        "#BF3EFF",
        "#B23AEE",
        "#9A32CD",
        "#68228B",
        "#4B0082",
        "#8A2BE2",
        "#9B30FF",
        "#912CEE",
        "#7D26CD",
        "#551A8B",
        "#9370DB",
        "#AB82FF",
        "#9F79EE",
        "#8968CD",
        "#5D478B",
        "#483D8B",
        "#8470FF",
        "#7B68EE",
        "#6A5ACD",
        "#836FFF",
        "#7A67EE",
        "#6959CD",
        "#473C8B",
        "#F8F8FF",
        "#E6E6FA",
        "#0000FF",
        "#0000EE",
        "#0000CD",
        "#00008B",
        "#000080",
        "#191970",
        "#3D59AB",
        "#4169E1",
        "#4876FF",
        "#436EEE",
        "#3A5FCD",
        "#27408B",
        "#6495ED",
        "#B0C4DE",
        "#CAE1FF",
        "#BCD2EE",
        "#A2B5CD",
        "#6E7B8B",
        "#778899",
        "#708090",
        "#C6E2FF",
        "#B9D3EE",
        "#9FB6CD",
        "#6C7B8B",
        "#1E90FF",
        "#1C86EE",
        "#1874CD",
        "#104E8B",
        "#F0F8FF",
        "#4682B4",
        "#63B8FF",
        "#5CACEE",
        "#4F94CD",
        "#36648B",
        "#87CEFA",
        "#B0E2FF",
        "#A4D3EE",
        "#8DB6CD",
        "#607B8B",
        "#87CEFF",
        "#7EC0EE",
        "#6CA6CD",
        "#4A708B",
        "#87CEEB",
        "#00BFFF",
        "#00B2EE",
        "#009ACD",
        "#00688B",
        "#33A1C9",
        "#ADD8E6",
        "#BFEFFF",
        "#B2DFEE",
        "#9AC0CD",
        "#68838B",
        "#B0E0E6",
        "#98F5FF",
        "#8EE5EE",
        "#7AC5CD",
        "#53868B",
        "#00F5FF",
        "#00E5EE",
        "#00C5CD",
        "#00868B",
        "#5F9EA0",
        "#00CED1",
        "#F0FFFF",
        "#E0EEEE",
        "#C1CDCD",
        "#838B8B",
        "#E0FFFF",
        "#D1EEEE",
        "#B4CDCD",
        "#7A8B8B",
        "#BBFFFF",
        "#AEEEEE",
        "#96CDCD",
        "#668B8B",
        "#2F4F4F",
        "#97FFFF",
        "#8DEEEE",
        "#79CDCD",
        "#528B8B",
        "#00FFFF",
        "#00EEEE",
        "#00CDCD",
        "#008B8B",
        "#008080",
        "#48D1CC",
        "#20B2AA",
        "#03A89E",
        "#40E0D0",
        "#808A87",
        "#00C78C",
        "#7FFFD4",
        "#76EEC6",
        "#66CDAA",
        "#458B74",
        "#00FA9A",
        "#F5FFFA",
        "#00FF7F",
        "#00EE76",
        "#00CD66",
        "#008B45",
        "#3CB371",
        "#54FF9F",
        "#4EEE94",
        "#43CD80",
        "#2E8B57",
        "#00C957",
        "#BDFCC9",
        "#3D9140",
        "#F0FFF0",
        "#E0EEE0",
        "#C1CDC1",
        "#838B83",
        "#8FBC8F",
        "#C1FFC1",
        "#B4EEB4",
        "#9BCD9B",
        "#698B69",
        "#98FB98",
        "#9AFF9A",
        "#90EE90",
        "#7CCD7C",
        "#548B54",
        "#32CD32",
        "#228B22",
        "#00FF00",
        "#00EE00",
        "#00CD00",
        "#008B00",
        "#008000",
        "#006400",
        "#308014",
        "#7CFC00",
        "#7FFF00",
        "#76EE00",
        "#66CD00",
        "#458B00",
        "#ADFF2F",
        "#CAFF70",
        "#BCEE68",
        "#A2CD5A",
        "#6E8B3D",
        "#556B2F",
        "#6B8E23",
        "#C0FF3E",
        "#B3EE3A",
        "#9ACD32",
        "#698B22",
        "#FFFFF0",
        "#EEEEE0",
        "#CDCDC1",
        "#8B8B83",
        "#F5F5DC",
        "#FFFFE0",
        "#EEEED1",
        "#CDCDB4",
        "#8B8B7A",
        "#FAFAD2",
        "#FFFF00",
        "#EEEE00",
        "#CDCD00",
        "#8B8B00",
        "#808069",
        "#808000",
        "#BDB76B",
        "#FFF68F",
        "#EEE685",
        "#CDC673",
        "#8B864E",
        "#F0E68C",
        "#EEE8AA",
        "#FFFACD",
        "#EEE9BF",
        "#CDC9A5",
        "#8B8970",
        "#FFEC8B",
        "#EEDC82",
        "#CDBE70",
        "#8B814C",
        "#E3CF57",
        "#FFD700",
        "#EEC900",
        "#CDAD00",
        "#8B7500",
        "#FFF8DC",
        "#EEE8CD",
        "#CDC8B1",
        "#8B8878",
        "#DAA520",
        "#FFC125",
        "#EEB422",
        "#CD9B1D",
        "#8B6914",
        "#B8860B",
        "#FFB90F",
        "#EEAD0E",
        "#CD950C",
        "#8B6508",
        "#FFA500",
        "#EE9A00",
        "#CD8500",
        "#8B5A00",
        "#FFFAF0",
        "#FDF5E6",
        "#F5DEB3",
        "#FFE7BA",
        "#EED8AE",
        "#CDBA96",
        "#8B7E66",
        "#FFE4B5",
        "#FFEFD5",
        "#FFEBCD",
        "#FFDEAD",
        "#EECFA1",
        "#CDB38B",
        "#8B795E",
        "#FCE6C9",
        "#D2B48C",
        "#9C661F",
        "#FF9912",
        "#FAEBD7",
        "#FFEFDB",
        "#EEDFCC",
        "#CDC0B0",
        "#8B8378",
        "#DEB887",
        "#FFD39B",
        "#EEC591",
        "#CDAA7D",
        "#8B7355",
        "#FFE4C4",
        "#EED5B7",
        "#CDB79E",
        "#8B7D6B",
        "#E3A869",
        "#ED9121",
        "#FF8C00",
        "#FF7F00",
        "#EE7600",
        "#CD6600",
        "#8B4500",
        "#FF8000",
        "#FFA54F",
        "#EE9A49",
        "#CD853F",
        "#8B5A2B",
        "#FAF0E6",
        "#FFDAB9",
        "#EECBAD",
        "#CDAF95",
        "#8B7765",
        "#FFF5EE",
        "#EEE5DE",
        "#CDC5BF",
        "#8B8682",
        "#F4A460",
        "#C76114",
        "#D2691E",
        "#FF7F24",
        "#EE7621",
        "#CD661D",
        "#8B4513",
        "#292421",
        "#FF7D40",
        "#FF6103",
        "#8A360F",
        "#A0522D",
        "#FF8247",
        "#EE7942",
        "#CD6839",
        "#8B4726",
        "#FFA07A",
        "#EE9572",
        "#CD8162",
        "#8B5742",
        "#FF7F50",
        "#FF4500",
        "#EE4000",
        "#CD3700",
        "#8B2500",
        "#5E2612",
        "#E9967A",
        "#FF8C69",
        "#EE8262",
        "#CD7054",
        "#8B4C39",
        "#FF7256",
        "#EE6A50",
        "#CD5B45",
        "#8B3E2F",
        "#8A3324",
        "#FF6347",
        "#EE5C42",
        "#CD4F39",
        "#8B3626",
        "#FA8072",
        "#FFE4E1",
        "#EED5D2",
        "#CDB7B5",
        "#8B7D7B",
        "#FFFAFA",
        "#EEE9E9",
        "#CDC9C9",
        "#8B8989",
        "#BC8F8F",
        "#FFC1C1",
        "#EEB4B4",
        "#CD9B9B",
        "#8B6969",
        "#F08080",
        "#CD5C5C",
        "#FF6A6A",
        "#EE6363",
        "#8B3A3A",
        "#CD5555",
        "#A52A2A",
        "#FF4040",
        "#EE3B3B",
        "#CD3333",
        "#8B2323",
        "#B22222",
        "#FF3030",
        "#EE2C2C",
        "#CD2626",
        "#8B1A1A",
        "#FF0000",
        "#EE0000",
        "#CD0000",
        "#8B0000",
        "#800000",
        "#8E388E",
        "#7171C6",
        "#7D9EC0",
        "#388E8E",
        "#71C671",
        "#8E8E38",
        "#C5C1AA",
        "#C67171",
        "#555555",
        "#1E1E1E",
        "#282828",
        "#515151",
        "#5B5B5B",
        "#848484",
        "#8E8E8E",
        "#AAAAAA",
        "#B7B7B7",
        "#C1C1C1",
        "#EAEAEA",
        "#F4F4F4",
        "#FFFFFF",
        "#F5F5F5",
        "#DCDCDC",
        "#D3D3D3",
        "#C0C0C0",
        "#A9A9A9",
        "#808080",
        "#696969",
        "#000000",
        "#FCFCFC",
        "#FAFAFA",
        "#F7F7F7",
        "#F5F5F5",
        "#F2F2F2",
        "#F0F0F0",
        "#EDEDED",
        "#EBEBEB",
        "#E8E8E8",
        "#E5E5E5",
        "#E3E3E3",
        "#E0E0E0",
        "#DEDEDE",
        "#DBDBDB",
        "#D9D9D9",
        "#D6D6D6",
        "#D4D4D4",
        "#D1D1D1",
        "#CFCFCF",
        "#CCCCCC",
        "#C9C9C9",
        "#C7C7C7",
        "#C4C4C4",
        "#C2C2C2",
        "#BFBFBF",
        "#BDBDBD",
        "#BABABA",
        "#B8B8B8",
        "#B5B5B5",
        "#B3B3B3",
        "#B0B0B0",
        "#ADADAD",
        "#ABABAB",
        "#A8A8A8",
        "#A6A6A6",
        "#A3A3A3",
        "#A1A1A1",
        "#9E9E9E",
        "#9C9C9C",
        "#999999",
        "#969696",
        "#949494",
        "#919191",
        "#8F8F8F",
        "#8C8C8C",
        "#8A8A8A",
        "#878787",
        "#858585",
        "#828282",
        "#7F7F7F",
        "#7D7D7D",
        "#7A7A7A",
        "#787878",
        "#757575",
        "#737373",
        "#707070",
        "#6E6E6E",
        "#6B6B6B",
        "#696969",
        "#666666",
        "#636363",
        "#616161",
        "#5E5E5E",
        "#5C5C5C",
        "#595959",
        "#575757",
        "#545454",
        "#525252",
        "#4F4F4F",
        "#4D4D4D",
        "#4A4A4A",
        "#474747",
        "#454545",
        "#424242",
        "#404040",
        "#3D3D3D",
        "#3B3B3B",
        "#383838",
        "#363636",
        "#333333",
        "#303030",
        "#2E2E2E",
        "#2B2B2B",
        "#292929",
        "#262626",
        "#242424",
        "#212121",
        "#1F1F1F",
        "#1C1C1C",
        "#1A1A1A",
        "#171717",
        "#141414",
        "#121212",
        "#0F0F0F",
        "#0D0D0D",
        "#0A0A0A",
        "#080808",
        "#050505",
        "#030303"
    )

}