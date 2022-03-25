package com.shoparty.android.ui.main.mainactivity


import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityMainBinding
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.categories.CategoriesFragment
import com.shoparty.android.ui.main.categories.CategoryRequestModel
import com.shoparty.android.ui.main.deals.DealsFragment
import com.shoparty.android.ui.main.drawer.drawer_main_category.DrawerParentAdapter
import com.shoparty.android.ui.main.drawer.drawer_main_category.DrawerResponse
import com.shoparty.android.ui.main.home.HomeFragment
import com.shoparty.android.ui.main.myaccount.MyAccountFragment
import com.shoparty.android.ui.main.myaccount.MyAccountViewModel
import com.shoparty.android.ui.main.myaccount.myprofileupdate.MyProfileActivity
import com.shoparty.android.ui.main.wishlist.WishListFragment
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.ui.shoppingbag.ShopingBagActivity
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModal
    private lateinit var drawerParentAdapter: DrawerParentAdapter
    private val listDrawer: ArrayList<DrawerResponse.Category> = ArrayList()
    private lateinit var myaccountviewModel: MyAccountViewModel
    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel =
            ViewModelProvider(this, ViewModalFactory(application)).get(MainViewModal::class.java)
        myaccountviewModel = ViewModelProvider(this, ViewModalFactory(application))[MyAccountViewModel::class.java]
        initialise()
        setObserver()

        val request = CategoryRequestModel("1")
        viewModel.getCategory(request)




    }

    private fun initialise()
    {
        if (PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty())
        {
            binding.btnSigninSignout.visibility = View.VISIBLE
            binding.clProfile.visibility = View.GONE
            binding.rlSignout.visibility = View.GONE
        }
        else
        {
            binding.rlSignout.visibility = View.VISIBLE
            binding.btnSigninSignout.visibility = View.GONE
            binding.tvName.setText(PrefManager.read(PrefManager.NAME, ""))
            binding.tvPhoneno.setText(PrefManager.read(PrefManager.MOBILE, ""))
            Glide.with(this).load(PrefManager.read(PrefManager.IMAGE, ""))
                .error(R.drawable.person_img)
                .into(binding.ivProfilePic)
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
                R.id.WishlistFragment ->
                    if(PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty())
                    {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        setCurrentFragment(WishListFragment())
                    }
                R.id.MyAccountFragment ->
                    if(PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty())
                    {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        setCurrentFragment(MyAccountFragment())
                    }
            }
            true
        }
    }


    private fun setObserver() {
        viewModel.drawer.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    listDrawer.clear()
                    listDrawer.addAll(response?.data!! as ArrayList<DrawerResponse.Category>)
                    drawerParentAdapter.notifyDataSetChanged()
                }

                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        myaccountviewModel.logout.observe(this, { response ->
            when (response)
            {
                is Resource.Success -> {
                   ProgressDialog.hideProgressBar()

                    PrefManager.clearAllPref()
                    dialog!!.dismiss()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                   ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                   ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }

    private fun drawerListing() {
        drawerParentAdapter = DrawerParentAdapter(this, listDrawer)
        val gridLayoutManager = GridLayoutManager(this, 1)
        binding.rvCategories.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = drawerParentAdapter
        }

    }

    fun manageUi(
        tvTitle: Boolean = false,
        ivLogo: Boolean = false,
        ivBag: Boolean = false,
        ivSearch: Boolean = false)
    {
        binding.infoTools.tvTitle.visibility = if(tvTitle) View.VISIBLE else View.INVISIBLE
        binding.infoTools.ivLogo.visibility = if (ivLogo) View.VISIBLE else View.INVISIBLE
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
                if(PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty())
                {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    manageWishlistSidebar()
                }
            }
            R.id.account_nav_btn -> {
                if(PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty())
                {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    manageMyaccountAccountSidebar()
                }
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
        opendialog()
    }

    private fun opendialog()
    {
        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog!!.setContentView(R.layout.alert_dialog_signout2)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog!!.getWindow()?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog!!.window?.attributes = lp
        dialog!!.setCanceledOnTouchOutside(true)
        val btn_cancel = dialog!!.findViewById<Button>(R.id.btn_cancel)
        val btn_yes = dialog!!.findViewById<Button>(R.id.btn_yes)
        btn_yes.setOnClickListener {
            myaccountviewModel.postLogout()  //api call
        }
        btn_cancel.setOnClickListener {
            dialog!!.dismiss()
        }
        dialog!!.show()
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
        binding.ivWishlist.setImageResource(R.drawable.wishlist_icon_black);
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
        binding.ivWishlist.setImageResource(R.drawable.wishlist_icon_black);
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