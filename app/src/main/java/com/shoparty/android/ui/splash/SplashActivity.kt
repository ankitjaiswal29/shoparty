package com.shoparty.android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.shoparty.android.R
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.utils.Utils


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

        handleDeepLinking()
    }

    private fun handleDeepLinking() {

        val intent = intent
       // val dashIntent = Intent(this, MainActivity::class.java)
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
            Utils.showLongToast(this, "$productId--$productDetail")
        }
//            startActivity(dashIntent)
//            finish()

//            if (PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)) {
//                if (recipeId.isNotBlank()) {
//                    //divert to recipe page directly after subscription
//                    PrefManager.putBoolean(PrefManager.isDeepLinked,true)
//                    PrefManager.putString(PrefManager.RECIPE_ID,recipeId)
//                    PrefManager.putString(PrefManager.RECIPE_IMAGE,recipeImage)
//                    PrefManager.putString(PrefManager.RECIPE_NAME,recipeName)
//
//                    dashIntent.putExtra(Constants.RECIPE_ID, recipeId)
//                    dashIntent.putExtra(Constants.RECIPE_IMAGE, recipeImage)
//                    dashIntent.putExtra(Constants.RECIPE_NAME, recipeName)
//
//                    Log.e(TAG, ">>>>> Deep Link URl ::" + data.toString())
//                    startActivity(dashIntent)
//                    finish()
//                }
//            }
//        } else {
//            if (PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)) {
//                startActivity(dashIntent)
//                finish()
//            }
//        }
        }
    }