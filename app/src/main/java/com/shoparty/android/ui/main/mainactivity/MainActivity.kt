package com.shoparty.android.ui.main.mainactivity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityMainBinding

import com.shoparty.android.ui.fragment.*
import com.shoparty.android.ui.login.LoginActivity

import com.shoparty.android.ui.main.categories.CategoriesFragment
import com.shoparty.android.ui.main.deals.DealsFragment
import com.shoparty.android.ui.main.home.HomeCategoriesModel
import com.shoparty.android.ui.main.myaccount.MyAccountFragment
import com.shoparty.android.ui.main.wishlist.WishListFragment
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.ui.shoppingbag.ShopingBagActivity

import kotlinx.android.synthetic.main.dashboard_toolbar.view.*


class MainActivity : AppCompatActivity(),View.OnClickListener {
    private var seletedItemId: Int = 0
    private lateinit var homeFragment: HomeFragment
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        initialise()
    }

    private fun initialise() {

        binding.navigationView.bringToFront()
        binding.homeNavBtn.setOnClickListener(this)
        binding.crossNavBtn.setOnClickListener(this)
        binding.btnSigninSignout.setOnClickListener(this)
        binding.btnSigninSignout.setOnClickListener(this)
        binding.infoTools.ivBagBtn.setOnClickListener(this)
        binding.infoTools.ivBtnsearch.setOnClickListener(this)



        binding.wishlistNavBtn.setOnClickListener(this)
        binding.accountNavBtn.setOnClickListener(this)
        binding.languageNavLay.setOnClickListener(this)
        binding.rlSignout.setOnClickListener(this)

        binding.infoTools.ivDrawericon.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        homeFragment=HomeFragment()
        val categoriesFragment=CategoriesFragment()
        val dealsFragment=DealsFragment()
        val wishListFragment= WishListFragment()
        val myAccountFragment= MyAccountFragment()

        val drawerItemList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Products"),
            HomeCategoriesModel("Services"),
            HomeCategoriesModel("Flowers"),
            HomeCategoriesModel("Rentals"),
            HomeCategoriesModel("New Arrivals"),
            HomeCategoriesModel("Best Selling"))

        binding.rvDrawerHomerecyclarview.layoutManager = LinearLayoutManager(this)
        val adapter = DrawerAdapter(this,drawerItemList)
        binding.rvDrawerHomerecyclarview.adapter = adapter

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
                manageHomeSidebar()
            }
            R.id.cross_nav_btn -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            R.id.wishlist_nav_btn -> {

                manageWishlistSidebar()

            }
            R.id.account_nav_btn -> {

                manageMyaccountAccountSidebar()

            }
            R.id.language_nav_lay -> {
                manageLanguageSidebar()

            }
            R.id.btn_signin_signout -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.ivBagBtn->{
                val intent = Intent(this, ShopingBagActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_signout->{
                manageSignoutSidebar()
            }
            R.id.iv_btnsearch->{
                val intent = Intent (this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun manageSignoutSidebar() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        binding.ivSignout.setImageResource(R.drawable.drawer_icon_signout_pink);
        binding.tvSignout.setTextColor(getColor(R.color.pink))

        binding.ivLanguage.setImageResource(R.drawable.language_icon);
        binding.tvLanguage.setTextColor(getColor(R.color.black))
        binding.ivHome.setImageResource(R.drawable.ic_baseline_home_24);
        binding.tvHome.setTextColor(getColor(R.color.black))
        binding.ivMyaccount.setImageResource(R.drawable.ic_user);
        binding.tvMyaccount.setTextColor(getColor(R.color.black))

        val builder = android.app.AlertDialog.Builder(this, R.style.CustomAlertDialog)
        val inflater = layoutInflater
        val dialogLayout: View =
            inflater.inflate(R.layout.alert_dialog_signout2, null)
        val btn_cancel = dialogLayout.findViewById<Button>(R.id.btn_cancel)
        val btn_yes = dialogLayout.findViewById<Button>(R.id.btn_yes)
        builder.setView(dialogLayout)
        builder.show()

        btn_yes.setOnClickListener {

        }
        btn_cancel.setOnClickListener {

        }
    }

    private fun manageLanguageSidebar() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        binding.ivLanguage.setImageResource(R.drawable.drawer_language_pink);
        binding.tvLanguage.setTextColor(getColor(R.color.pink))

        binding.ivSignout.setImageResource(R.drawable.ic_sign_out_icon);
        binding.tvSignout.setTextColor(getColor(R.color.black))
        binding.ivHome.setImageResource(R.drawable.ic_baseline_home_24);
        binding.tvHome.setTextColor(getColor(R.color.black))
        binding.ivMyaccount.setImageResource(R.drawable.ic_user);
        binding.tvMyaccount.setTextColor(getColor(R.color.black))

        val builder = AlertDialog.Builder(this , R.style.CustomAlertDialog)
        val inflater = layoutInflater
        val dialogLayout: View =
            inflater.inflate(R.layout.alert_dialog_signout, null)
        val btn_cancel = dialogLayout.findViewById<Button>(R.id.cancel_btn)
        val btn_save = dialogLayout.findViewById<Button>(R.id.save_btn)

        btn_cancel.setOnClickListener {

        }
        builder.setView(dialogLayout)
        builder.show()
    }

    private fun manageMyaccountAccountSidebar() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        //  ic_user
        binding.ivMyaccount.setImageResource(R.drawable.drawer_myaccount_pink);
        binding.tvMyaccount.setTextColor(getColor(R.color.pink))

        binding.ivSignout.setImageResource(R.drawable.ic_sign_out_icon);
        binding.tvSignout.setTextColor(getColor(R.color.black))
        binding.ivHome.setImageResource(R.drawable.ic_baseline_home_24);
        binding.ivLanguage.setImageResource(R.drawable.language_icon);
        binding.ivWishlist.setImageResource(R.drawable.wishlist_icon);
        binding.tvHome.setTextColor(getColor(R.color.black))
        binding.tvLanguage.setTextColor(getColor(R.color.black))
        binding.tvWishlist.setTextColor(getColor(R.color.black))
        binding.bottomNavigatinView.findViewById<View>(R.id.MyAccountFragment).performClick()

    }

    private fun manageWishlistSidebar() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        // wishlist_icon

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        binding.ivWishlist.setImageResource(R.drawable.drawer_wishlist_pink);
        binding.tvWishlist.setTextColor(getColor(R.color.pink))

        binding.ivSignout.setImageResource(R.drawable.ic_sign_out_icon);
        binding.tvSignout.setTextColor(getColor(R.color.black))
        binding.ivHome.setImageResource(R.drawable.ic_baseline_home_24);
        binding.ivMyaccount.setImageResource(R.drawable.ic_user);
        binding.ivLanguage.setImageResource(R.drawable.language_icon);
        binding.tvHome.setTextColor(getColor(R.color.black))
        binding.tvMyaccount.setTextColor(getColor(R.color.black))
        binding.tvLanguage.setTextColor(getColor(R.color.black))

        binding.bottomNavigatinView.findViewById<View>(R.id.WishlistFragment).performClick()

    }

    private fun manageHomeSidebar()
    {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        binding.ivHome.setImageResource(R.drawable.ic_drawer_home_pink);
        binding.tvHome.setTextColor(getColor(R.color.pink))


        binding.ivSignout.setImageResource(R.drawable.ic_sign_out_icon);
        binding.tvSignout.setTextColor(getColor(R.color.black))
        binding.ivWishlist.setImageResource(R.drawable.wishlist_icon);
        binding.ivMyaccount.setImageResource(R.drawable.ic_user);
        binding.ivLanguage.setImageResource(R.drawable.language_icon);
        binding.tvWishlist.setTextColor(getColor(R.color.black))
        binding.tvMyaccount.setTextColor(getColor(R.color.black))
        binding.tvLanguage.setTextColor(getColor(R.color.black))

        binding.bottomNavigatinView.findViewById<View>(R.id.homeFragment).performClick()
    }


    override fun onBackPressed() {
        if (binding.bottomNavigatinView.getSelectedItemId() == R.id.homeFragment)
        {
           AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.are_sure_exit))
                .setPositiveButton(
                    getString(R.string.yes)
                ) {
                        dialog: DialogInterface?, which: Int -> finishAffinity()
                }
                .setNegativeButton(getString(R.string.no), null).show()
        }
        else
        {
            binding.bottomNavigatinView.setSelectedItemId(R.id.homeFragment)
            binding.bottomNavigatinView.findViewById<View>(R.id.homeFragment).performClick()
        }
    }
}