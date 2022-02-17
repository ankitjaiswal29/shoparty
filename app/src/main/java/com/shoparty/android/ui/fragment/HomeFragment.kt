package com.shoparty.android.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.models.custommodel.HomeCategoriesModel
import com.shoparty.android.models.custommodel.TopSellingHomeModel
import com.shoparty.android.ui.activities.ballons.BallonsActivity
import com.shoparty.android.ui.activities.candles.CandlesActivity
import com.shoparty.android.ui.activities.colors.ColoursActivity
import com.shoparty.android.ui.activities.costumes.CostumesActivity
import com.shoparty.android.ui.activities.partysupply.PartySupplyActivity
import com.shoparty.android.ui.activities.search.SearchActivity
import com.shoparty.android.ui.activities.seasons.SeasonsActivity
import com.shoparty.android.ui.activities.shoppingbag.ShopingBagActivity
import com.shoparty.android.ui.activities.theme.ThemesActivity
import com.shoparty.android.ui.adapters.*

import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.navigation_drawer.*

class HomeFragment : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private var productsBool: Boolean=false
    private var serviceBool: Boolean=false
    private var flowersBool: Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.navigation_drawer, container, false)

        drawerLayout = root.findViewById(R.id.drawer_layout)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        below_product_layout.visibility = View.GONE
        below_service_layout.visibility = View.GONE
        below_flowers_layout.visibility = View.GONE

        products_nav_lay.setOnClickListener {
            if (productsBool == false){
                below_product_layout.visibility = View.VISIBLE
                productsBool = true
            }else{
                below_product_layout.visibility = View.GONE
                productsBool = false
            }
        }

        services_nav_lay.setOnClickListener {
            if (serviceBool == false){
                below_service_layout.visibility = View.VISIBLE
                serviceBool = true
            }else{
                below_service_layout.visibility = View.GONE
                serviceBool = false
            }
        }

        flowers_nav_lay.setOnClickListener {
            if (flowersBool == false){
                below_flowers_layout.visibility = View.VISIBLE
                flowersBool = true
            }else{
                below_flowers_layout.visibility = View.GONE
                flowersBool = false
            }
        }

        nav_drawer_btn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        cross_nav_btn.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        costume_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), CostumesActivity::class.java)
            startActivity(intent)
        }

        themes_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), ThemesActivity::class.java)
            startActivity(intent)
        }
        party_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), PartySupplyActivity::class.java)
            startActivity(intent)
        }
        ballons_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), BallonsActivity::class.java)
            startActivity(intent)
        }

        colors_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), ColoursActivity::class.java)
            startActivity(intent)
        }
        seasons_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), SeasonsActivity::class.java)
            startActivity(intent)
        }
        candles_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), CandlesActivity::class.java)
            startActivity(intent)
        }
        signup_nav_lay.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
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

        home_search_ed_txt.setOnClickListener {
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(intent)
        }

        bag_btn.setOnClickListener {
            val intent = Intent(requireActivity(), ShopingBagActivity::class.java)
            startActivity(intent)
        }

        fillTopSellingRecyclerView(topSellingItemList)
        fillCategoriesRecyclerView(categoryList)
        fillSeasonsRecyclerView(seasonsItemList)
        fillThemesRecyclerView(themesList)
        fillNewArrivalsRecyclerView(newArrivalsList)
        fillTsSubCategoriesRecyclerView(tsSubCategoriesList)
        fillHomeOffersRecyclerView(homeOffersList)
        fillHomeBondsRecyclerView(homeBondsList)
    }


    private val topSellingItemList = listOf<TopSellingHomeModel>(
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
    )

    private fun fillTopSellingRecyclerView(teachers: List<TopSellingHomeModel>) {
        top_selling_recycler.adapter = TopSellingHomeAdapter(topSellingItemList)
    }

    private val categoryList = listOf<HomeCategoriesModel>(
        HomeCategoriesModel("Ballons"),
        HomeCategoriesModel("Party Supply"),
    )

    private fun fillCategoriesRecyclerView(categories: List<HomeCategoriesModel>) {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        home_categories_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = HomeCategoriesAdapter(categoryList)
        }
    }

    private val seasonsItemList = listOf<HomeCategoriesModel>(
        HomeCategoriesModel("Ballons"),
        HomeCategoriesModel("Ballons"),
        HomeCategoriesModel("Ballons"),
        HomeCategoriesModel("Ballons"),
        HomeCategoriesModel("Ballons"),

        )

    private fun fillSeasonsRecyclerView(seasons: List<HomeCategoriesModel>) {
        seasons_recycler.adapter = HomeSeasonsAdapter(seasonsItemList)
    }


//themes
    private val themesList = listOf<HomeCategoriesModel>(
        HomeCategoriesModel("Unicorn"),
        HomeCategoriesModel("Mermaid"),
        HomeCategoriesModel("Unicorn"),
        HomeCategoriesModel("Mermaid"),
    )

    private fun fillThemesRecyclerView(themes: List<HomeCategoriesModel>) {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        home_themes_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = HomeCategoriesAdapter(themesList)
        }
    }
//new arrivals

    private val newArrivalsList = listOf<HomeCategoriesModel>(
        HomeCategoriesModel("Toys Kids"),
        HomeCategoriesModel("Toys Kids"),
        HomeCategoriesModel("Toys Kids"),
        HomeCategoriesModel("Toys Kids"),
    )

    private fun fillNewArrivalsRecyclerView(themes: List<HomeCategoriesModel>) {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        home_new_arrivals_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = NewArrivalsHomeAdapter(newArrivalsList)
        }
    }

    //ts Sub categories
    private val tsSubCategoriesList = listOf<HomeCategoriesModel>(
        HomeCategoriesModel("Mask"),
        HomeCategoriesModel("Mask"),
        HomeCategoriesModel("Mask"),
        HomeCategoriesModel("Mask"),
    )

    private fun fillTsSubCategoriesRecyclerView(themes: List<HomeCategoriesModel>) {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        ts_subcategories_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = TopSellingSubcategoriesAdapter(tsSubCategoriesList)
        }
    }

    //home offers

    private val homeOffersList = listOf<HomeCategoriesModel>(
        HomeCategoriesModel("Toys Kids"),
        HomeCategoriesModel("Toys Kids"),
        HomeCategoriesModel("Toys Kids"),
        HomeCategoriesModel("Toys Kids"),
    )

    private fun fillHomeOffersRecyclerView(themes: List<HomeCategoriesModel>) {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        home_offers_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = HomeOffersAdapter(homeOffersList)
        }
    }

    //home Bonds

    private val homeBondsList = listOf<HomeCategoriesModel>(
        HomeCategoriesModel("Toys Kids"),
        HomeCategoriesModel("Toys Kids"),
        HomeCategoriesModel("Toys Kids"),
        HomeCategoriesModel("Toys Kids"),
    )

    private fun fillHomeBondsRecyclerView(themes: List<HomeCategoriesModel>) {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        home_bonds_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = HomeBondsAdapter(homeBondsList)
        }
    }
}