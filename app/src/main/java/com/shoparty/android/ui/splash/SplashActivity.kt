package com.shoparty.android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.shoparty.android.R
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.utils.PrefManager
import java.util.*


class SplashActivity : AppCompatActivity() {
    lateinit var locale: Locale
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            setLocale("ar")
        }else {
            setLocale("en")
        }

        handleDeepLinking()
    }

    private fun setLocale(localeName: String) {
        // if (localeName != currentLanguage) {
        locale = Locale(localeName)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, dm)
    }

    private fun handleDeepLinking() {

        val intent = intent
        if (intent != null && intent.data != null) {
            val data = intent.data
            val splittedData = data.toString().split("&")
            val productId = try {
                splittedData[0].split("=")[1]
            } catch (e: Exception) {
                ""
            }

            val productDetail = try {
                splittedData[1].split("=")[1]
            } catch (e: Exception) {
                ""
            }

            val productSize = try {
                splittedData[2].split("=")[1]
            } catch (e: Exception) {
                ""
            }

            val productColor = try {
                splittedData[3].split("=")[1]
            } catch (e: Exception) {
                ""
            }
            val productName = try {
                splittedData[4].split("=")[1]
            } catch (e: Exception) {
                ""
            }
            PrefManager.write(PrefManager.IDPRODUCT1,productId)
            PrefManager.write(PrefManager.PRODUCATNAME1,productName)
            PrefManager.write(PrefManager.PRODUCATDETAILSID1,productDetail)
            PrefManager.write(PrefManager.PRODUCTSIZEID1,productSize)
            PrefManager.write(PrefManager.PRODUCTCOLORID1,productColor)
            PrefManager.write(PrefManager.isFromLink,true)
        }
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }


    }