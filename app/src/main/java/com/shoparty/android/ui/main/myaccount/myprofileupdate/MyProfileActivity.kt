package com.shoparty.android.ui.main.myaccount.myprofileupdate

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri


import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.developers.imagezipper.ImageZipper
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


import com.shoparty.android.R

import com.shoparty.android.databinding.ActivityMyProfileBinding
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.myaccount.MyAccountViewModel
import com.shoparty.android.ui.register.RegisterViewModel
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.ImagePickerActivity
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MyProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMyProfileBinding
    var cal = Calendar.getInstance()
    private lateinit var viewModel: MyAccountViewModel
    private var selecteddate = ""
    private var selectedgender = ""
    var dialog: Dialog? = null
    private val REQUEST_IMAGE = 999
    private var imageZipperFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_my_profile)
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(application)
        )[MyAccountViewModel::class.java]

        initialise()
        setObserver()
    }

    private fun initialise()
    {
        setPrefrenceData()  //set data
        binding.btnSave.setOnClickListener(this)
        binding.tvDateBirth.setOnClickListener(this)
        binding.tvMale.setOnClickListener(this)
        binding.tvFemale.setOnClickListener(this)
        binding.ivEditProfile.setOnClickListener(this)
        binding.infoTool.back.visibility=View.VISIBLE
        binding.infoTool.tvTitle.setText(getString(R.string.my_account))
        binding.infoTool.back.setOnClickListener(this)
    }


    private fun setObserver() {
        viewModel.profileupdate.observe(this, { response ->
            when (response)
            {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    PrefManager.write(PrefManager.NAME, response.data?.name!!)
                    PrefManager.write(PrefManager.IMAGE,response.data?.image!!)
                    PrefManager.write(PrefManager.MOBILE, response.data?.mobile!!)
                    PrefManager.write(PrefManager.EMAIL, response.data?.email!!)
                    PrefManager.write(PrefManager.DOB, binding.tvDateBirth.text.toString().trim())
                    PrefManager.write(PrefManager.GENDER, response.data?.gender!!)
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
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

    private fun setPrefrenceData()
    {
        Glide.with(this).load(PrefManager.read(PrefManager.IMAGE,"")).error(R.drawable.person_img).into(binding.ivProfilePic)
        binding.txtMobile.text = PrefManager.read(PrefManager.MOBILE,"").toString()
        binding.tvName.text = PrefManager.read(PrefManager.NAME,"")
        binding.etFirstname.setText(PrefManager.read(PrefManager.NAME,""))
        binding.etMobile.setText(PrefManager.read(PrefManager.MOBILE,""))
        binding.etEmail.setText(PrefManager.read(PrefManager.EMAIL,""))
        binding.tvDateBirth.text = PrefManager.read(PrefManager.DOB,"")

        binding.etFirstname.setSelection(binding.etFirstname.length())
        binding.etMobile.isEnabled = false
        binding.etEmail.setSelection(binding.etEmail.length())
        if(PrefManager.read(PrefManager.GENDER,"") == Constants.MALE)
        {
            maleGenderSet()
        }
        else
        {
            femaleGenderSet()
        }
    }




    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSave -> {
                if(validation())
                {
                val builder = MultipartBody.Builder()
                        builder.setType(MultipartBody.FORM)
                        if(imageZipperFile != null)
                        {
                            builder.addFormDataPart(
                                "image",
                                imageZipperFile?.name,
                                RequestBody.create("image/*".toMediaTypeOrNull(), imageZipperFile!!))
                        }
                        builder.addFormDataPart("name", binding.etFirstname.text.toString())
                        builder.addFormDataPart("email", binding.etEmail.text.toString())
                        builder.addFormDataPart("mobile",binding.etMobile.text.toString())
                        builder.addFormDataPart("dob",binding.tvDateBirth.text.toString())
                        builder.addFormDataPart("gender",  selectedgender)
                        val body = builder.build()

                        viewModel.postupdateProfile(body)   //api call
                }
            }
            R.id.back -> {
                onBackPressed()

            }
            R.id.tvDateBirth -> {
                DatePic()

            }
            R.id.iv_EditProfile -> {
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
                maleGenderSet()
            }
            R.id.tv_female -> {
                femaleGenderSet()
            }
        }
    }

    private fun maleGenderSet()
    {
        binding.tvMale.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pink_check, 0, 0, 0);
        binding.tvFemale.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        binding.tvFemale.setTextColor(Color.parseColor("#E30986"));
        binding.tvMale.setTextColor(Color.parseColor("#E30986"));
        binding.tvFemale.setTextColor(Color.parseColor("#A19989"));
        selectedgender=binding.tvMale.text.toString()
    }

    private fun femaleGenderSet()
    {
        binding.tvMale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        binding.tvFemale.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pink_check, 0, 0, 0);
        binding.tvFemale.setTextColor(Color.parseColor("#E30986"));
        binding.tvMale.setTextColor(Color.parseColor("#A19989"));
        selectedgender=binding.tvFemale.text.toString()
    }

    private fun DatePic()
    {
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

    private fun loadProfile(url: String)
    {
        Glide.with(this).load(url).error(R.drawable.gallery_image_icon).placeholder(R.drawable.gallery_image_icon)
            .into(binding.ivProfilePic)
        binding.ivProfilePic.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent))
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

    private fun validation():Boolean
    {
        if (binding.etFirstname.text.toString().isNullOrBlank())
        {
            Utils.showShortToast(this,getString(R.string.enterfullname))
            return false
        }
        if (binding.etEmail.text.toString().trim().isNullOrBlank()) {
            Utils.showShortToast(this,getString(R.string.enteremailid))
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString().trim()).matches())
        {
            Utils.showShortToast(this,getString(R.string.entervalidmail))
            return false
        }
        if (binding.etMobile.text.toString().isNullOrBlank()) {
            Utils.showShortToast(this,getString(R.string.entermobileno))
            return false
        }
        if(Utils.checkValidMobile(binding.etMobile.text.toString()!!)){
            Utils.showShortToast(this,getString(R.string.entervalidnumber))
            return false
        }
        if(binding.tvDateBirth.text.toString().isNullOrBlank()){
            Utils.showShortToast(this,getString(R.string.pleaseselectdob))
            return false
        }
        return true
    }
}



private fun AlertDialog.Builder.setNegativeButton(function: (DialogInterface, Int) -> Unit) {

}