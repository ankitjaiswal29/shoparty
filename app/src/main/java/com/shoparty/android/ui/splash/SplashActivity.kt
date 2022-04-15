package com.shoparty.android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig
import com.shoparty.android.R
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.Utils


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handleDeepLinking()
    }

    private fun handleDeepLinking() {

        val intent = intent
       // val dashIntent = Intent(this, ProductDetailsActivity::class.java)
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
//            startActivity(dashIntent)
        }
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }
    }