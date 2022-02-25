package com.shoparty.android.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentHomeBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.deals.TopSellingHomeModel
import com.shoparty.android.ui.main.home.*
import com.shoparty.android.ui.main.topselling.TopSellingActivity
import com.shoparty.android.ui.search.SearchActivity
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dashboard_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),RecyclerViewClickListener,View.OnClickListener {

    private lateinit var drawerLayout: DrawerLayout
    private var productsBool: Boolean=false
    private var serviceBool: Boolean=false
    private var flowersBool: Boolean=false
    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // val root =  inflater.inflate(R.layout.navigation_drawer, container, false)
      binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
      //  val root =  inflater.inflate(R.layout.fragment_home, container, false)

      // drawerLayout = root.findViewById(R.id.drawer_layout)
        //initialise()
       // setupUI()
        return binding.root
        //return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).info_tools.tv_title.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon2.visibility=View.VISIBLE

        (activity as MainActivity).info_tools.ivBagBtn.visibility=View.VISIBLE
        (activity as MainActivity).info_tools.iv_btnsearch.visibility=View.INVISIBLE

        initialise()

    }
    private fun initialise() {

        binding.tvHomeSearch.setOnClickListener(this)
        val imageList: ArrayList<String> = ArrayList()
        imageList.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
        imageList.add("https://images.ctfassets.net/hrltx12pl8hq/4plHDVeTkWuFMihxQnzBSb/aea2f06d675c3d710d095306e377382f/shutterstock_554314555_copy.jpg")
        imageList.add("https://media.istockphoto.com/photos/child-hands-formig-heart-shape-picture-id951945718?k=6&m=951945718&s=612x612&w=0&h=ih-N7RytxrTfhDyvyTQCA5q5xKoJToKSYgdsJ_mHrv0=")
        setImageInSlider(imageList, binding.imageSliderr)




        Topselling()
        HomeCategory()
        season()
        HomeTheamRecyclar()
        NewArrival()
        TopsellingSubcategories()
        OfferDiscoutItem()
        Brands()



    }

    private fun Brands() {
        val homeOffersList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Up To 10% Off"),
            HomeCategoriesModel("Up To 10% Off"),
            HomeCategoriesModel("Up To 10% Off"),
            HomeCategoriesModel("Up To 10% Off"),

            )


        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        home_bonds_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = HomeBondsAdapter(homeOffersList)
        }
    }

    private fun OfferDiscoutItem() {
         val homeOffersList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Up To 10% Off"),
            HomeCategoriesModel("Up To 10% Off"),

        )


            val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
            home_offers_recycler.apply {
                layoutManager = gridLayoutManager
                setHasFixedSize(true)
                isFocusable = false
                adapter = HomeOffersAdapter(homeOffersList)
            }

    }

    private fun TopsellingSubcategories() {
         val tsSubCategoriesList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Mask"),
            HomeCategoriesModel("Mask"),
            HomeCategoriesModel("Mask"),
            HomeCategoriesModel("Mask"),
        )

               val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
            ts_subcategories_recycler.apply {
                layoutManager = gridLayoutManager
                setHasFixedSize(true)
                isFocusable = false
                adapter = TopSellingSubcategoriesAdapter(tsSubCategoriesList)
            }

    }

    private fun NewArrival() {
         val newArrivalsList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Toys Kids"),
            HomeCategoriesModel("Toys Kids"),
            HomeCategoriesModel("Toys Kids"),
            HomeCategoriesModel("Toys Kids"),
        )


            val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
            home_new_arrivals_recycler.apply {
                layoutManager = gridLayoutManager
                setHasFixedSize(true)
                isFocusable = false
                adapter = NewArrivalsHomeAdapter(newArrivalsList)
            }


    }

    private fun HomeTheamRecyclar() {

         val themesList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Unicorn"),
            HomeCategoriesModel("Mermaid"),
            HomeCategoriesModel("Unicorn"),
            HomeCategoriesModel("Mermaid"),
        )

            val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
            home_themes_recycler.apply {
                layoutManager = gridLayoutManager
                setHasFixedSize(true)
                isFocusable = false
                adapter = HomeCategoriesAdapter(themesList)
            }



    }

    private fun season() {
         val seasonsItemList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Ballons"),

            )


            binding.seasonsRecycler.adapter = HomeSeasonsAdapter(seasonsItemList)

    }

    private fun HomeCategory() {
         val categoryList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Party Supply"),
        )

            val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
            home_categories_recycler.apply {
                layoutManager = gridLayoutManager
                setHasFixedSize(true)
                isFocusable = false
                adapter = HomeCategoriesAdapter(categoryList)
            }

    }

    private fun Topselling() {
        val topSellingItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
        )
        binding.topSellingRecycler.adapter = TopSellingHomeAdapter(topSellingItemList,this)
    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = MySliderImageAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

    override fun click(pos: String) {
        val intent = Intent(activity, TopSellingActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v?.id){
           R.id.tv_home_search->{
               val intent = Intent(activity, SearchActivity::class.java)
               startActivity(intent)
           }
        }

    }


/*
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

//themes

//new arrivals


    //ts Sub categories


    //home offers



    //home Bonds

    */
}