package com.shoparty.android.ui.activities.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityMainBinding

import com.shoparty.android.databinding.ActivityPrivacyPolicyBinding
import com.shoparty.android.ui.fragment.*

import com.shoparty.android.ui.mainactivity.categories.CategoriesFragment
import com.shoparty.android.ui.mainactivity.deals.DealsFragment
import com.shoparty.android.ui.mainactivity.myaccount.MyAccountFragment
import com.shoparty.android.ui.mainactivity.wishlist.WishListFragment

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.alert_dialog_signout.view.*
import kotlinx.android.synthetic.main.dashboard_toolbar.view.*
import kotlinx.android.synthetic.main.navigation_drawer.view.*


class MainActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        initialise()

    }

    private fun initialise() {

        binding.navigationView.bringToFront();
       // binding.navigationView.setNavigationItemSelectedListener(this)
        binding.homeNavBtn.setOnClickListener(this)
        binding.crossNavBtn.setOnClickListener(this)


        binding.wishlistNavBtn.setOnClickListener(this)
        binding.accountNavBtn.setOnClickListener(this)
        binding.languageNavLay.setOnClickListener(this)

        binding.infoTools.iv_Drawericon.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        val homeFragment=HomeFragment()
        val categoriesFragment=CategoriesFragment()
        val dealsFragment=DealsFragment()
        val wishListFragment= WishListFragment()
        val myAccountFragment= MyAccountFragment()

       /* binding.wishlistNavBtn.setOnClickListener {
            setCurrentFragment(wishListFragment)
            Toast.makeText(this,"dfdf",Toast.LENGTH_LONG).show()
            binding.bottomNavigatinView.findViewById<View>(R.id.WishlistFragment).performClick()

        }*/


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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.home_nav_btn -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                binding.ivHome.setImageResource(R.drawable.ic_drawer_home_pink);
                binding.tvHome.setTextColor(getColor(R.color.pink))

                binding.ivWishlist.setImageResource(R.drawable.wishlist_icon);
                binding.ivMyaccount.setImageResource(R.drawable.ic_user);
                binding.ivLanguage.setImageResource(R.drawable.language_icon);
                binding.tvWishlist.setTextColor(getColor(R.color.black))
                binding.tvMyaccount.setTextColor(getColor(R.color.black))
                binding.tvLanguage.setTextColor(getColor(R.color.black))
                //  ic_baseline_home_24
                binding.bottomNavigatinView.findViewById<View>(R.id.homeFragment).performClick()

            }
            R.id.cross_nav_btn -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
              //  binding.bottomNavigatinView.findViewById<View>(R.id.WishlistFragment).performClick()


            }
            R.id.wishlist_nav_btn -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
               // wishlist_icon

                binding.drawerLayout.closeDrawer(GravityCompat.START)
                binding.ivWishlist.setImageResource(R.drawable.drawer_wishlist_pink);
                binding.tvWishlist.setTextColor(getColor(R.color.pink))

                binding.ivHome.setImageResource(R.drawable.ic_baseline_home_24);
                binding.ivMyaccount.setImageResource(R.drawable.ic_user);
                binding.ivLanguage.setImageResource(R.drawable.language_icon);
                binding.tvHome.setTextColor(getColor(R.color.black))
                binding.tvMyaccount.setTextColor(getColor(R.color.black))
                binding.tvLanguage.setTextColor(getColor(R.color.black))

                binding.bottomNavigatinView.findViewById<View>(R.id.WishlistFragment).performClick()

            }
            R.id.account_nav_btn -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
              //  ic_user
                binding.ivMyaccount.setImageResource(R.drawable.drawer_myaccount_pink);
                binding.tvMyaccount.setTextColor(getColor(R.color.pink))

                binding.ivHome.setImageResource(R.drawable.ic_baseline_home_24);
                binding.ivLanguage.setImageResource(R.drawable.language_icon);
                binding.ivWishlist.setImageResource(R.drawable.wishlist_icon);
                binding.tvHome.setTextColor(getColor(R.color.black))
                binding.tvLanguage.setTextColor(getColor(R.color.black))
                binding.tvWishlist.setTextColor(getColor(R.color.black))
                binding.bottomNavigatinView.findViewById<View>(R.id.MyAccountFragment).performClick()

            }
            R.id.language_nav_lay -> {
              //  language_icon

                binding.ivLanguage.setImageResource(R.drawable.drawer_language_pink);
                binding.tvLanguage.setTextColor(getColor(R.color.pink))

                binding.ivHome.setImageResource(R.drawable.ic_baseline_home_24);
                binding.tvHome.setTextColor(getColor(R.color.black))
                binding.ivMyaccount.setImageResource(R.drawable.ic_user);
                binding.tvMyaccount.setTextColor(getColor(R.color.black))
                binding.bottomNavigatinView.findViewById<View>(R.id.MyAccountFragment).performClick()

                //  binding.drawerLayout.closeDrawer(GravityCompat.START)
                //  ic_user
              //  binding.bottomNavigatinView.findViewById<View>(R.id.MyAccountFragment).performClick()

            }


        }
    }




}