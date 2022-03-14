package com.shoparty.android.utils

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.google.android.material.snackbar.Snackbar
import java.lang.RuntimeException

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.util.*
import android.telephony.PhoneNumberUtils

import android.content.ComponentName

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity





object Utils {

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    fun hideKeyboard(activity: Activity, view: View) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboard(activity: Activity) {
        val view = (activity.findViewById<View>(R.id.content) as ViewGroup).getChildAt(0)
        hideKeyboard(activity, view)
    }
    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showShortToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


      fun convertToCustomFormat(dateStr: String?): String {
        val utc = TimeZone.getTimeZone("UTC")
        val sourceFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val destFormat = SimpleDateFormat("dd MMM,YYYY HH:mm aa")
        sourceFormat.timeZone = utc
        val convertedDate = sourceFormat.parse(dateStr)
        return destFormat.format(convertedDate)
    }

   fun checkValidMobile(mobile:String):Boolean{
       return mobile.length<8 || mobile.length>15
    }

    fun formatElapsedTime(elapsedSeconds: Long): String? {
        throw RuntimeException("Stub!")
    }
    fun showLongToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }

        return false
    }

    fun setEditTextFocus(editText: EditText) {
        editText.clearFocus()
        editText.requestFocus()
        editText.setSelection(editText.text.length)
    }

    fun utcToLocalTime(date :String):String{
        var dateLocal = date
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val value = formatter.parse(date)
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm") //this format changeable
            dateFormatter.timeZone = TimeZone.getDefault()
            dateLocal = dateFormatter.format(value)
            Log.d("dateLocal", "utcToLocalTime: $dateLocal")
            //Log.d("ourDate", ourDate);
        } catch (e: java.lang.Exception) {
            Thread.currentThread().stackTrace
            dateLocal = "00-00-0000 00:00"
        }
        return dateLocal
    }
    fun getAge(year: Int, month: Int, day: Int): Int? {
        //calculating age from dob
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob[year, month] = day
        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }
        return age
    }

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


   /* fun openWhatsAppConversation(context: Context, number: String, message: String?) {
        var number = number
        number = number.replace(" ", "").replace("+", "")
        val sendIntent = Intent("android.intent.action.MAIN")
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net")
        context.startActivity(sendIntent)
    }


    fun openWhatsAppConversationUsingUri(
        context: Context,
        numberWithCountryCode: String,
        message: String
    ) {
        val uri: Uri =
            Uri.parse("https://api.whatsapp.com/send?phone=$numberWithCountryCode&text=$message")
        val sendIntent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(sendIntent)
    }*/






}