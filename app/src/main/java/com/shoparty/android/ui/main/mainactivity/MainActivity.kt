package com.shoparty.android.ui.main.mainactivity


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityMainBinding
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.categories.CategoriesFragment
import com.shoparty.android.ui.main.deals.DealsFragment
import com.shoparty.android.ui.main.home.HomeCategoriesModel
import com.shoparty.android.ui.main.home.HomeFragment
import com.shoparty.android.ui.main.myaccount.MyAccountFragment
import com.shoparty.android.ui.main.myaccount.myprofileupdate.MyProfileActivity
import com.shoparty.android.ui.main.wishlist.WishListFragment
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.ui.shoppingbag.ShopingBagActivity
import com.shoparty.android.utils.PrefManager


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initialise()
    }

    private fun initialise() {
        binding.rlSignout.visibility = View.GONE
        if (PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty()) {
            binding.btnSigninSignout.visibility = View.VISIBLE
        } else {
            binding.btnSigninSignout.visibility = View.GONE
        }

        binding.navigationView.bringToFront()

        binding.homeNavBtn.setOnClickListener(this)
        binding.crossNavBtn.setOnClickListener(this)
        binding.btnSigninSignout.setOnClickListener(this)
        binding.btnSigninSignout.setOnClickListener(this)
        binding.infoTools.ivBag.setOnClickListener(this)
        binding.infoTools.ivSearch.setOnClickListener(this)
        binding.clProfile.setOnClickListener(this)
        binding.wishlistNavBtn.setOnClickListener(this)
        binding.accountNavBtn.setOnClickListener(this)
        binding.languageNavLay.setOnClickListener(this)
        binding.rlSignout.setOnClickListener(this)

        binding.infoTools.ivDrawerIcon.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        drawerListing()

        setCurrentFragment(HomeFragment())

        binding.bottomNavigatinView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> setCurrentFragment(HomeFragment())
                R.id.categoriesFragment -> setCurrentFragment(CategoriesFragment())
                R.id.dealsFragment -> setCurrentFragment(DealsFragment())
                R.id.WishlistFragment -> setCurrentFragment(WishListFragment())
                R.id.MyAccountFragment -> setCurrentFragment(MyAccountFragment())
            }
            true
        }
    }

    private fun drawerListing() {
        val drawerItemList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Products"),
            HomeCategoriesModel("Services"),
            HomeCategoriesModel("Flowers"),
            HomeCategoriesModel("Rentals"),
            HomeCategoriesModel("New Arrivals"),
            HomeCategoriesModel("Best Selling")
        )

        binding.rvDrawerHomerecyclarview.layoutManager = LinearLayoutManager(this)
        val adapter = DrawerAdapter(this, drawerItemList)
        binding.rvDrawerHomerecyclarview.adapter = adapter
    }

    fun manageUi(
        tvTitle: Boolean = false,
        ivLogo: Boolean = false,
        ivBag: Boolean = false,
        ivSearch: Boolean = false
    ) {
        binding.infoTools.tvTitle.visibility = if (tvTitle) View.VISIBLE else View.INVISIBLE
        binding.infoTools.ivLogo.visibility =
            if (ivLogo) View.VISIBLE else View.INVISIBLE
        binding.infoTools.ivBag.visibility = if (ivBag) View.VISIBLE else View.INVISIBLE
        binding.infoTools.ivSearch.visibility = if (ivSearch) View.VISIBLE else View.INVISIBLE
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    override fun onClick(v: View?) {
        when (v?.id) {
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
            R.id.ivBag -> {
                val intent = Intent(this, ShopingBagActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_signout -> {
                manageSignOutSidebar()
            }
            R.id.ivSearch -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.cl_profile -> {
                val intent = Intent(this, MyProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun manageSignOutSidebar() {
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

        val builder = android.app.AlertDialog.Builder(this, R.style.CustomAlertDialogWithMargin)
        val inflater = layoutInflater
        val dialogLayout: View =
            inflater.inflate(R.layout.alert_dialog_signout, null)
        val btn_cancel = dialogLayout.findViewById<Button>(R.id.cancel_btn)
        val btn_save = dialogLayout.findViewById<Button>(R.id.save_btn)

        builder.setView(dialogLayout)
        val builderinstance = builder.show()

        btn_cancel.setOnClickListener {
            builder.setCancelable(true)
            builderinstance.dismiss()
        }

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

    private fun manageHomeSidebar() {

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
        if (binding.bottomNavigatinView.getSelectedItemId() == R.id.homeFragment) {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.are_sure_exit))
                .setPositiveButton(
                    getString(R.string.yes)
                ) { dialog: DialogInterface?, which: Int ->
                    finishAffinity()
                }
                .setNegativeButton(getString(R.string.no), null).show()
        } else {
            binding.bottomNavigatinView.setSelectedItemId(R.id.homeFragment)
            binding.bottomNavigatinView.findViewById<View>(R.id.homeFragment).performClick()
        }
    }

}