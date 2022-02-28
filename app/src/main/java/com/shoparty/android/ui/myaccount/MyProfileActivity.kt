package com.shoparty.android.ui.myaccount

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R

import com.shoparty.android.databinding.ActivityMyProfileBinding
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.tv_female
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
 /*   private var btn: Button? = null
    private var imageview: ImageView? = null*/
    private val GALLERY = 1
    private val CAMERA = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_profile)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_my_profile)
        initialise()
    }

    private fun initialise() {
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
                showPictureDialog()

            }
            R.id.tvMale -> {
               // iv_male.visibility=View.VISIBLE
                //iv_female.visibility=View.GONE
                binding.tvMale.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pink_check, 0, 0, 0);
                binding.tvFemale.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                binding.tvFemale.setTextColor(Color.parseColor("#E30986"));
                binding.tvMale.setTextColor(Color.parseColor("#E30986"));
                binding.tvFemale.setTextColor(Color.parseColor("#A19989"));
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

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
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
    }
    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
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