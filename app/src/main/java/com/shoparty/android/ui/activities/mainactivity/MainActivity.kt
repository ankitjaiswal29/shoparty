package com.shoparty.android.ui.activities.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityMainBinding

import com.shoparty.android.databinding.ActivityPrivacyPolicyBinding
import com.shoparty.android.ui.fragment.*

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        initialise()
       // val navController = findNavController(R.id.nav_fragment)
        //bottom_navigatin_view.setupWithNavController(navController)

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

    private fun initialise() {
        val homeFragment=HomeFragment()
        val categoriesFragment=CategoriesFragment()
        val dealsFragment=DealsFragment()
        val wishListFragment=WishListFragment()
        val myAccountFragment=MyAccountFragment()

        setCurrentFragment(homeFragment)

        binding.bottomNavigatinView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment->setCurrentFragment(homeFragment)
                R.id.categoriesFragment->setCurrentFragment(categoriesFragment)
                R.id.dealsFragment->setCurrentFragment(dealsFragment)
                R.id.WishlistFragment->setCurrentFragment(wishListFragment)
                R.id.MyAccountFragment ->setCurrentFragment(myAccountFragment)




            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}