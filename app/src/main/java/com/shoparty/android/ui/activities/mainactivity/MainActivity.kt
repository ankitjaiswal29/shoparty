package com.shoparty.android.ui.activities.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shoparty.android.R

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_fragment)
        bottom_navigatin_view.setupWithNavController(navController)

       /* Handler().postDelayed({
            val intent = Intent(this, AddCardActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)*/
       /* Handler().postDelayed({
            val intent = Intent(this, ShippingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)*/


        /*Handler().postDelayed({
            val intent = Intent(this, ShippingActivity::class.java)
            startActivity(intent)
           // finish()
          //  val fragmentManager: FragmentManager = supportFragmentManager
            //val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            //fragmentTransaction.replace(R.id.idmyfragment, MyAccountFragment()).commit()
        }, 3000)*/




    }
}