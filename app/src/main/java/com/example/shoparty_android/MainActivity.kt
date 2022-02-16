package com.example.shoparty_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shoparty_android.view.activity.AddCardActivity
import com.example.shoparty_android.view.activity.MyAccountActivity
import com.example.shoparty_android.view.activity.ShippingActivity
import com.example.shoparty_android.view.fragment.MyAccountFragment
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