package com.shoparty.android.ui.main.myaccount

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.PermissionRequest
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.shoparty.android.R

import com.shoparty.android.databinding.ActivityMyProfileBinding
import com.shoparty.android.utils.ImagePickerActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MyProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMyProfileBinding
    var cal = Calendar.getInstance()
    private var selecteddate = ""
    private var selectedgender = ""
    private val GALLERY = 1
    private val CAMERA = 2
    var dialog: Dialog? = null
    private val REQUEST_IMAGE = 999
    var imageZipperFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_my_profile)
        initialise()
    }

    private fun initialise() {


        selectedgender=binding.tvMale.text.toString()
        binding.btnSave.setOnClickListener(this)
        binding.tvDateBirth.setOnClickListener(this)
        binding.tvMale.setOnClickListener(this)
        binding.tvFemale.setOnClickListener(this)
        binding.ivEditProfile.setOnClickListener(this)
        binding.infoTool.back.visibility=View.VISIBLE
        binding.infoTool.tvTitle.setText(getString(R.string.my_account))
        binding.infoTool.back.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSave -> {
                /*val intent = Intent(this, MyAccountActivity::class.java)
                startActivity(intent)*/
                finish()

            }
            R.id.back -> {
                onBackPressed()

            }
            R.id.tvDateBirth -> {
                DatePic()

            }
            R.id.iv_EditProfile -> {
                //showPictureDialog()

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
            R.id.tvMale -> {
               // iv_male.visibility=View.VISIBLE
                //iv_female.visibility=View.GONE
                binding.tvMale.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pink_check, 0, 0, 0);
                binding.tvFemale.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                binding.tvFemale.setTextColor(Color.parseColor("#E30986"));
                binding.tvMale.setTextColor(Color.parseColor("#E30986"));
                binding.tvFemale.setTextColor(Color.parseColor("#A19989"));
                selectedgender=binding.tvMale.text.toString()
               // selectedgender=tv_male.text.toString()

            }
            R.id.tv_female -> {
              //  iv_male.visibility=View.GONE
                //iv_female.visibility=View.VISIBLE
               // tv_female.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pink_check, 0, 0, 0);
                //tv_male.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding.tvMale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                binding.tvFemale.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pink_check, 0, 0, 0);

                binding.tvFemale.setTextColor(Color.parseColor("#E30986"));
                binding.tvMale.setTextColor(Color.parseColor("#A19989"));
                selectedgender=binding.tvFemale.text.toString()
                //selectedgender=tv_female.text.toString()
            }

        }
    }

    private fun DatePic() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }


            DatePickerDialog(
                this, R.style.DialogTheme,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()


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
            //PrefManager.getString(R.string.Profile.toString())
        Toast.makeText(this,"dfd",Toast.LENGTH_LONG).show()
        ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
        builder.show()
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
        //  Log.d("TAG", "Image cache path: $url")
        Glide.with(this).load(url).error(R.drawable.gallery_image_icon).placeholder(R.drawable.gallery_image_icon)
            .into(binding.ivProfilePic)
        binding.ivProfilePic.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent))
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }
    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)

        startActivityForResult(intent, CAMERA)
    }

   /* public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        *//* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*//*
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                 //   Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
                    binding.ivProfilePic!!.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                  //  Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            binding.ivProfilePic!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
         //   Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }*/
    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
        )
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }


    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvDateBirth!!.text = sdf.format(cal.time)
        selecteddate=sdf.format(cal.time)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}